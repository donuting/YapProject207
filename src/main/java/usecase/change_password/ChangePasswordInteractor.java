package usecase.change_password;

import entity.UserFactory;

/**
 * The Change Password Interactor.
 */
public class ChangePasswordInteractor implements ChangePasswordInputBoundary {
    public static final int MIN_PASSWORD_LENGTH = 8;
    private final ChangePasswordUserDataAccessInterface userDataAccessObject;
    private final ChangePasswordOutputBoundary userPresenter;
    private final UserFactory userFactory;

    public ChangePasswordInteractor(ChangePasswordUserDataAccessInterface changePasswordDataAccessInterface,
                                    ChangePasswordOutputBoundary changePasswordOutputBoundary,
                                    UserFactory userFactory) {
        this.userDataAccessObject = changePasswordDataAccessInterface;
        this.userPresenter = changePasswordOutputBoundary;
        this.userFactory = userFactory;
    }

    @Override
    public void execute(ChangePasswordInputData changePasswordInputData) {
        String username = changePasswordInputData.getUsername();
        String password = changePasswordInputData.getPassword();

        if (password == null || password.length() < MIN_PASSWORD_LENGTH) {
            userPresenter.prepareFailChangePasswordView("Password is empty or has less than 8 characters.");
        }
        else {

            if (!password.matches("(.*[a-z].*)(.*[A-Z].*)(.*\\d.*)")) {
                userPresenter.prepareFailChangePasswordView("Password must contain at least one digit,"
                        + " one uppercase letter, and one lowercase letter.");
            }

            userDataAccessObject.changePassword(username, password);

            final ChangePasswordOutputData changePasswordOutputData = new ChangePasswordOutputData(username,
                    false, password);
            userPresenter.prepareSuccessChangePasswordView(changePasswordOutputData);
        }
    }
}
