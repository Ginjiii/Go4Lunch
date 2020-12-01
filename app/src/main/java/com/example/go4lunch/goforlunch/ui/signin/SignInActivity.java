package com.example.go4lunch.goforlunch.ui.signin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.example.go4lunch.goforlunch.ui.MainActivity;
import com.go4lunch.R;
import com.go4lunch.databinding.ActivitySignInBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.example.go4lunch.goforlunch.ui.signin.SignInViewModel.RC_SIGN_IN;


public class SignInActivity extends AppCompatActivity {

    private ActivitySignInBinding binding;
    private SignInViewModel viewModel;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        viewModel = new ViewModelProvider(this).get(SignInViewModel.class);
    }
    private void initView() {
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.facebookLoginButton.setOnClickListener(v -> viewModel.startSignInActivityFacebook(SignInActivity.this));

        binding.gmailLoginButton.setOnClickListener(v -> viewModel.startSignInActivityGoogle(SignInActivity.this));

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            finish();
        }
        startActivity(new Intent(SignInActivity.this, MainActivity.class));
    }

    @Override
    public void onStart(){
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            // Name, email address, and profile picture Url
            String username = currentUser.getDisplayName();
            String email = currentUser.getEmail();
            Uri urlPicture = currentUser.getPhotoUrl();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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