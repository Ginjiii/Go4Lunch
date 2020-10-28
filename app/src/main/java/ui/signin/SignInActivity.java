package ui.signin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.widget.Toast;

import androidx.databinding.library.baseAdapters.BR;


import com.example.go4lunch.R;
import com.example.go4lunch.databinding.ActivitySignInBinding;

import ui.MainActivity;
import ui.base.BaseActivity;

public class SignInActivity extends BaseActivity<ActivitySignInBinding, SignInViewModel> implements SignInNavigator {

    private ActivitySignInBinding mActivitySignInBinding;

    public static Intent newIntent(Context context) {
        return new Intent(context, SignInActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_sign_in;
    }

    @Override
    public void handleError(Throwable throwable) {
        // handle error
    }

    @Override
    public void login() {
        String email = mActivitySignInBinding.toString();
        String password = mActivitySignInBinding.toString();
        if (mViewModel.isEmailAndPasswordValid(email, password)) {
            hideKeyboard();
            mViewModel.login(email, password);
        } else {
            Toast.makeText(this, getString(R.string.invalid_email_password), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void openMainActivity() {
        Intent intent = MainActivity.newIntent(SignInActivity.this);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivitySignInBinding = getViewDataBinding();
        mViewModel.setNavigator(this);
    }
}