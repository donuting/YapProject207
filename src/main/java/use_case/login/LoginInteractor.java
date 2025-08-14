package use_case.login;

import entity.User;

/**
 * The Login Interactor.
 */
public class LoginInteractor implements LoginInputBoundary {
    private final LoginUserDataAccessInterface userDataAccessObject;
    private final LoginOutputBoundary loginPresenter;

    public LoginInteractor(LoginUserDataAccessInterface userDataAccessInterface,
                           LoginOutputBoundary loginOutputBoundary) {
        this.userDataAccessObject = userDataAccessInterface;
        this.loginPresenter = loginOutputBoundary;
    }

    @Override
    public void execute(LoginInputData loginInputData) {
        final String username = loginInputData.getUsername();
        final String password = loginInputData.getPassword();
        if (!userDataAccessObject.existsByName(username)) {
            loginPresenter.prepareFailView(username + ": Account does not exist.");
        }
        else {
            final User user = userDataAccessObject.get(username);
            final String pwd = user.getPassword();
            if (!password.equals(pwd)) {
                loginPresenter.prepareFailView("Incorrect password for \"" + username + "\".");
            }
            else {
                userDataAccessObject.setCurrentUser(user);
                userDataAccessObject.setCurrentSelfChat(user.getSelfChat());
                final LoginOutputData loginOutputData = new LoginOutputData(user.getName(), false, user.getPassword(),
                        user.getID(), user.getBio(), user.getDOB());
                loginPresenter.prepareSuccessView(loginOutputData);
            }
        }
    }

    /**
     * Execute the Switch To Login use case.
     */
    @Override
    public void switchToSignup() {
        loginPresenter.switchToSignup();
    }
}
