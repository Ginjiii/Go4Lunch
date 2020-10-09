package ui.signin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.go4lunch.databinding.ActivitySignInBinding;
import com.firebase.ui.auth.AuthUI;

import java.util.Collections;

public class FacebookSignIn extends SignInActivity {

    private @org.jetbrains.annotations.NotNull ActivitySignInBinding binding;
    private static final int RC_SIGN_IN = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.adaptedFacebookLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSignInActivityFacebook();
            }
        });
    }

    //
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    // Create and launch sign-in activity Facebook
    private void startSignInActivityFacebook() {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(Collections.singletonList(new
                                AuthUI.IdpConfig.FacebookBuilder().build()))
                        .setIsSmartLockEnabled(false, true)
                        .build(),
                RC_SIGN_IN);
    }
}
