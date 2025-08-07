package use_case.change_password;

import entity.User;
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
        userDataAccessObject.changePassword(username, password);

        final ChangePasswordOutputData changePasswordOutputData = new ChangePasswordOutputData(username,
                                                                                  false, password);
        userPresenter.prepareSuccessChangePasswordView(changePasswordOutputData);
    }
}
