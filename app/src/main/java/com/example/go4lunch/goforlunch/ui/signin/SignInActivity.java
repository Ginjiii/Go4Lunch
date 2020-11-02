package com.example.go4lunch.goforlunch.ui.signin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.go4lunch.R;
import com.example.go4lunch.goforlunch.ui.MainActivity;
import com.firebase.ui.auth.IdpResponse;

import static com.example.go4lunch.goforlunch.ui.signin.SignInViewModel.RC_SIGN_IN;


public class SignInActivity extends AppCompatActivity {

    private com.example.go4lunch.databinding.ActivitySignInBinding binding;
    private SignInViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        viewModel = new ViewModelProvider(this).get(SignInViewModel.class);
    }

    private void initView() {
        binding = com.example.go4lunch.databinding.ActivitySignInBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.facebookLoginButton.setOnClickListener(v -> viewModel.startSignInActivityFacebook(SignInActivity.this));

        binding.gmailLoginButton.setOnClickListener(v -> viewModel.startSignInActivityGoogle(SignInActivity.this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("tagii", "onActivityResult requestCode : "+requestCode);
        Log.i("tagii", "onActivityResult resultCode : "+resultCode);
        IdpResponse response = IdpResponse.fromResultIntent(data);
        Log.i("tagii", "response : "+response.getEmail());

        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                Intent loginIntent = new Intent(this, MainActivity.class);
                startActivity(loginIntent);
            } else {
                Toast.makeText(this, R.string.error_unknown_error, Toast.LENGTH_SHORT).show();
            }
        }
    }
}