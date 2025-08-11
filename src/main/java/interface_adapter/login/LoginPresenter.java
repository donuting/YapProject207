package interface_adapter.login;

import interface_adapter.ViewManagerModel;
import interface_adapter.change_password.LoggedInState;
import interface_adapter.change_password.LoggedInViewModel;
import use_case.login.LoginOutputBoundary;
import interface_adapter.main_menu.MainMenuState;
import interface_adapter.main_menu.MainMenuViewModel;
import use_case.login.LoginOutputData;
import use_case.login.LoginOutputBoundary;

/**
 * The Presenter for the Login Use Case.
 */
public class LoginPresenter implements LoginOutputBoundary {

    private final LoginViewModel loginViewModel;
    private final MainMenuViewModel mainMenuViewModel;
    private final ViewManagerModel viewManagerModel;

    public LoginPresenter(ViewManagerModel viewManagerModel,
                          MainMenuViewModel mainMenuViewModel,
                          LoginViewModel loginViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.mainMenuViewModel = mainMenuViewModel;
        this.loginViewModel = loginViewModel;
    }

    @Override
    public void prepareSuccessView(LoginOutputData response) {
        // On success, switch to the main menu view
        final MainMenuState mainMenuState = mainMenuViewModel.getState();
        mainMenuState.setUsername(response.getUsername());
        mainMenuState.setPassword(response.getPassword());
        mainMenuState.setUID(response.getUID());
        this.mainMenuViewModel.setState(mainMenuState);
        this.mainMenuViewModel.firePropertyChanged();

        this.viewManagerModel.setState(mainMenuViewModel.getState().getUsername().isEmpty() ?
                "main menu" : "main menu");
        this.viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        final LoginState loginState = loginViewModel.getState();
        loginState.setLoginError(error);
        loginViewModel.firePropertyChanged();
    }

    @Override
    public void switchToSignup() {
        viewManagerModel.setState("sign up");
        viewManagerModel.firePropertyChanged();
    }
}