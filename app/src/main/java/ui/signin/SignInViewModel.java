package ui.signin;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.go4lunch.R;
import com.example.go4lunch.databinding.ActivitySignInBinding;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;

import java.util.Collections;
import java.util.Objects;

import ui.MainActivity;

import static androidx.core.content.ContextCompat.startActivity;

public class SignInViewModel extends SignInActivity {

    private static final int RC_SIGN_IN = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
}

    public SignInViewModel(Application application) {
        super(application);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.handleResponseAfterSignIn(requestCode, resultCode, data);
    }


    // Method that handles response after sign in Activity close
    private void handleResponseAfterSignIn(int requestCode, int resultCode, Intent data) {
        IdpResponse response = IdpResponse.fromResultIntent(data);

        if (requestCode == RC_SIGN_IN) {

            if (resultCode == RESULT_OK) {

                Toast.makeText(this, R.string.connection_succeed, Toast.LENGTH_SHORT).show();
                Intent loginIntent = new Intent(this, MainActivity.class);
                startActivity(loginIntent);

            } else { //error

                if (response == null) {
                    Toast.makeText(this, R.string.error_authentication_canceled, Toast.LENGTH_SHORT).show();

                } else if (Objects.requireNonNull(response.getError()).getErrorCode() == ErrorCodes.NO_NETWORK) {
                    Toast.makeText(this, R.string.error_no_internet, Toast.LENGTH_SHORT).show();

                } else if (response.getError().getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    Toast.makeText(this, R.string.error_unknown_error, Toast.LENGTH_SHORT).show();
                }
    }
            binding.adaptedFacebookLoginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startSignInActivityFacebook();
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

                binding.loginGoogleButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startSignInActivityGoogle();
                    }

                    // Create and launch sign-in activity Google
                    private void startSignInActivityGoogle() {
                        startActivityForResult(
                                AuthUI.getInstance()
                                        .createSignInIntentBuilder()
                                        .setAvailableProviders(Collections.singletonList(new
                                                AuthUI.IdpConfig.GoogleBuilder().build()))
                                        .setIsSmartLockEnabled(false, true)
                                        .build(),
                                RC_SIGN_IN);
                    }
                }