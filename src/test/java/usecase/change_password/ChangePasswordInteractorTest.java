package usecase.change_password;

import dataaccess.InMemoryUserDataAccessObject;
import entity.CommonUserFactory;
import entity.User;
import entity.UserFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ChangePasswordInteractorTest {

    private InMemoryUserDataAccessObject dataAccess;
    private UserFactory userFactory;
    private User user;

    @BeforeEach
    void setUp() {
        dataAccess = new InMemoryUserDataAccessObject();
        userFactory = new CommonUserFactory();
        user = userFactory.create("User", "Password1");
        dataAccess.save(user);

    }

    @Test
    void changePasswordSuccessTest() {
        ChangePasswordInputData inputData = new ChangePasswordInputData("Password2", "User");
        ChangePasswordOutputBoundary sucessPresenter = new ChangePasswordOutputBoundary() {
            @Override
            public void prepareSuccessChangePasswordView(ChangePasswordOutputData outputData) {
                assertEquals("User", outputData.getUsername());
                assertEquals("Password2", outputData.getPassword());
            }

            @Override
            public void prepareFailChangePasswordView(String errorMessage) {
                fail("Change password use case failed");
            }
        };

        ChangePasswordInputBoundary changePasswordInteractor =
                new ChangePasswordInteractor(dataAccess,sucessPresenter, userFactory);
        changePasswordInteractor.execute(inputData);
    }

    @Test
    //The password does not have a capital letter
    void changePasswordFailTest1() {
        ChangePasswordInputData inputData = new ChangePasswordInputData("password2", "User");
        ChangePasswordOutputBoundary failPresenter = new ChangePasswordOutputBoundary() {
            @Override
            public void prepareSuccessChangePasswordView(ChangePasswordOutputData outputData) {
               fail("Change password use case does not check the existence of a uppercase letter");
            }

            @Override
            public void prepareFailChangePasswordView(String errorMessage) {
                assertEquals("Password must contain at least one digit, one uppercase letter, and one lowercase letter.",
                        errorMessage);
            }
        };

        ChangePasswordInputBoundary changePasswordInteractor =
                new ChangePasswordInteractor(dataAccess,failPresenter, userFactory);
        changePasswordInteractor.execute(inputData);
    }

    @Test
    //The password does not have a digit
    void changePasswordFailTest2() {
        ChangePasswordInputData inputData = new ChangePasswordInputData("Password", "User");
        ChangePasswordOutputBoundary failPresenter = new ChangePasswordOutputBoundary() {
            @Override
            public void prepareSuccessChangePasswordView(ChangePasswordOutputData outputData) {
                fail("Change password use case does not check the existence of a digit");
            }

            @Override
            public void prepareFailChangePasswordView(String errorMessage) {
                assertEquals("Password must contain at least one digit, one uppercase letter, and one lowercase letter.",
                        errorMessage);
            }
        };

        ChangePasswordInputBoundary changePasswordInteractor =
                new ChangePasswordInteractor(dataAccess,failPresenter, userFactory);
        changePasswordInteractor.execute(inputData);
        assertEquals("Password1", user.getPassword());
    }

    @Test
    //The password does not have a lowercase letter
    void changePasswordFailTest3() {
        ChangePasswordInputData inputData = new ChangePasswordInputData("PASSWORD2", "User");
        ChangePasswordOutputBoundary failPresenter = new ChangePasswordOutputBoundary() {
            @Override
            public void prepareSuccessChangePasswordView(ChangePasswordOutputData outputData) {
                fail("Change password use case does not check the existence of a lowercase letter");
            }

            @Override
            public void prepareFailChangePasswordView(String errorMessage) {
                assertEquals("Password must contain at least one digit, one uppercase letter, and one lowercase letter.",
                        errorMessage);
            }
        };

        ChangePasswordInputBoundary changePasswordInteractor =
                new ChangePasswordInteractor(dataAccess,failPresenter, userFactory);
        changePasswordInteractor.execute(inputData);
        assertEquals("Password1", user.getPassword());
    }

    @Test
    // password is empty
    void changePasswordFailTest4() {
        ChangePasswordInputData inputData = new ChangePasswordInputData("", "User");
        ChangePasswordOutputBoundary failPresenter = new ChangePasswordOutputBoundary() {
            @Override
            public void prepareSuccessChangePasswordView(ChangePasswordOutputData outputData) {
                fail("Change password use case does not check the empty case");
            }

            @Override
            public void prepareFailChangePasswordView(String errorMessage) {
                assertEquals("Password is empty or has less than 8 characters.",
                        errorMessage);
            }
        };

        ChangePasswordInputBoundary changePasswordInteractor =
                new ChangePasswordInteractor(dataAccess,failPresenter, userFactory);
        changePasswordInteractor.execute(inputData);
        assertEquals("Password1", user.getPassword());
    }

    @AfterEach
    void tearDown() {
        dataAccess = null;
        user = null;
        userFactory = null;
    }
}
