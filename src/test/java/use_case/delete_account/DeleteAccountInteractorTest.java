package use_case.delete_account;
import data_access.SendBirdUserDataAccessObject;
import data_access.InMemoryUserDataAccessObject;
import entity.CommonUserFactory;
import entity.User;
import entity.UserFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DeleteAccountInteractorTest {

    private SendBirdUserDataAccessObject dataAccess;
    private UserFactory userFactory;
    private User user;

    @BeforeEach
    void setUp() {
        dataAccess = new SendBirdUserDataAccessObject();
        userFactory = new CommonUserFactory();
        user = userFactory.create("User", "Password1");
        dataAccess.save(user);
        dataAccess.setCurrentUser(user);
        dataAccess.setCurrentUsername(user.getName());
    }

    @Test
    void deleteAccountSuccessTest() {
        DeleteAccountInputData inputData = new DeleteAccountInputData(user.getName());
        DeleteAccountOutputBoundary successPresenter = new DeleteAccountOutputBoundary() {
            @Override
            public void prepareSuccessDeleteAccountView(DeleteAccountOutputData outputData) {
                assertTrue(outputData.isSuccess());
            }
            @Override
            public void prepareFailDeleteAccountView(String errorMessage, DeleteAccountOutputData outputData) {
                fail("Should not call prepareFailDeleteAccountView on successful deletion.");
            }
        };
        DeleteAccountInteractor interactor = new DeleteAccountInteractor(dataAccess, successPresenter);
        interactor.execute(inputData);
        assertFalse(dataAccess.existsByName(user.getName()));
    }

    @AfterEach
    void tearDown() {
        dataAccess = null;
        user = null;
        userFactory = null;
    }
}