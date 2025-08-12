package usecase.change_password;

import entity.UserFactory;

/**
 * The Change Password Interactor.
 */
public class ChangePasswordInteractor implements ChangePasswordInputBoundary {
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

        if (password == null || password.length() < 8) {
            userPresenter.prepareFailChangePasswordView("Password is empty or has less than 8 characters.");
        }

        boolean hasDigit = false;
        boolean hasUpper = false;
        boolean hasLower = false;

        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (Character.isDigit(c)) {
                hasDigit = true;
            }
            else if (Character.isUpperCase(c)) {
                hasUpper = true;
            }
            else if (Character.isLowerCase(c)) {
                hasLower = true;
            }
            if (hasDigit && hasUpper && hasLower) {
                // End early if all conditions met
                break;
            }
        }

        if (!hasDigit || !hasUpper || !hasLower) {
            userPresenter.prepareFailChangePasswordView("Password must contain at least one digit," +
                    " one uppercase letter, and one lowercase letter.");
        }

        userDataAccessObject.changePassword(username, password);

        final ChangePasswordOutputData changePasswordOutputData = new ChangePasswordOutputData(username,
                                                                                  false, password);
        userPresenter.prepareSuccessChangePasswordView(changePasswordOutputData);
    }
}
