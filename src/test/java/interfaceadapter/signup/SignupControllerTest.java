package interfaceadapter.signup;

import dataaccess.SendBirdUserDataAccessObject;
import entity.CommonUserFactory;
import entity.UserFactory;
import interfaceadapter.ViewManagerModel;
import interfaceadapter.login.LoginViewModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import usecase.signup.SignupInputBoundary;
import usecase.signup.SignupInputData;
import usecase.signup.SignupInteractor;
import usecase.signup.SignupOutputBoundary;

 class SignupControllerTest {
    final SendBirdUserDataAccessObject userDataAccessObject = new SendBirdUserDataAccessObject();
    final ViewManagerModel viewManagerModel = new ViewManagerModel();
    final SignupViewModel signupViewModel = new SignupViewModel();
    final LoginViewModel loginViewModel = new LoginViewModel();
    final SignupOutputBoundary signupOutputBoundary = new SignupPresenter(viewManagerModel, signupViewModel, loginViewModel);
    final UserFactory userFactory = new CommonUserFactory();

    @Test
    void switchToLoginViewTest() {
        final SignupInputBoundary userSignupInteractor = new SignupInteractor(
                userDataAccessObject, signupOutputBoundary, userFactory) {
            @Override
            public void switchToLoginView() {
                Assertions.assertTrue(true);
            }
        };

        final SignupController controller = new SignupController(userSignupInteractor);
        controller.switchToLoginView();
    }

    @Test
    void signupTest() {
        final SignupInputBoundary userSignupInteractor = new SignupInteractor(
                userDataAccessObject, signupOutputBoundary, userFactory) {
            @Override
            public void execute(SignupInputData signupInputData) {
                Assertions.assertEquals("username", signupInputData.getUsername());
                Assertions.assertEquals("password1", signupInputData.getPassword());
                Assertions.assertEquals("password2", signupInputData.getRepeatPassword());
            }
        };
        final SignupController controller = new SignupController(userSignupInteractor);
        String username = "username";
        String password = "password1";
        String repeatPassword = "password2";
        controller.execute(username, password, repeatPassword);
    }
}

