package ui.signin;

public interface SignInNavigator {

    void handleError(Throwable throwable);

    void login();

    void openMainActivity();
}