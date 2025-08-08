package use_case.delete_account;

import data_access.InMemoryUserDataAccessObject;
import entity.CommonUserFactory;
import entity.User;
import entity.UserFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DeleteAccountInteractorTest {

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
    void deleteAccountSuccessTest() {
        DeleteAccountInputData inputData = new DeleteAccountInputData(user.getName());
        DeleteAccountOutputBoundary successPresenter = new DeleteAccountOutputBoundary() {
            @Override
            public void present(DeleteAccountOutputData outputData) {
                assertTrue(outputData.isSuccess());
            }
        };
        DeleteAccountInteractor interactor = new DeleteAccountInteractor(dataAccess, successPresenter);
        interactor.execute(inputData);
        assertFalse(dataAccess.existsByName(user.getName()));
    }

    @Test
    void deleteAccountFailTest() {
        DeleteAccountInputData inputData = new DeleteAccountInputData("NonExistentUser");
        DeleteAccountOutputBoundary failPresenter = new DeleteAccountOutputBoundary() {
            @Override
            public void present(DeleteAccountOutputData outputData) {
                assertFalse(outputData.isSuccess());
            }
        };
        DeleteAccountInteractor interactor = new DeleteAccountInteractor(dataAccess, failPresenter);
        interactor.execute(inputData);
    }

    @AfterEach
    void tearDown() {
        dataAccess = null;
        user = null;
        userFactory = null;
    }
}