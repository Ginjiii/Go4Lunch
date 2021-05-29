package com.example.go4lunch.goforlunch.ui.signin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.go4lunch.goforlunch.factory.Go4LunchFactory;
import com.example.go4lunch.goforlunch.injections.Injection;
import com.example.go4lunch.goforlunch.ui.MainActivity;
import com.go4lunch.R;
import com.go4lunch.databinding.ActivitySignInBinding;

import static com.example.go4lunch.goforlunch.ui.signin.SignInViewModel.RC_SIGN_IN;

public class SignInActivity extends AppCompatActivity {

    private ActivitySignInBinding binding;
    private SignInViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();

        intListener();

        viewModel = obtainViewModel();

        checkSessionUser();
    }

    private void initView() {
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }

    private void intListener() {
        binding.facebookLoginButton.setOnClickListener(v -> viewModel.startSignInActivityFacebook(SignInActivity.this));
        binding.gmailLoginButton.setOnClickListener(v -> viewModel.startSignInActivityGoogle(SignInActivity.this));
        binding.twitterLoginButton.setOnClickListener(v -> viewModel.startSignInActivityTwitter(SignInActivity.this));
    }

    private SignInViewModel obtainViewModel() {
        Go4LunchFactory viewModelFactory = Injection.provideViewModelFactory();
        return new ViewModelProvider(this, viewModelFactory).get(SignInViewModel.class);
    }

    private void checkSessionUser() {
        viewModel.updateCurrentUser();
        if (viewModel.isCurrentUserLogged()) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                viewModel.updateCurrentUser();
                Intent loginIntent = new Intent(this, MainActivity.class);
                startActivity(loginIntent);
            } else {
                Toast.makeText(this, R.string.error_unknown_error, Toast.LENGTH_SHORT).show();
            }
        }
    }
}