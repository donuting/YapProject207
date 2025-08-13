package use_case.remove_friend;

import data_access.InMemoryUserDataAccessObject;
import data_access.SendBirdUserDataAccessObject;
import entity.CommonUser;
import entity.CommonUserFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RemoveFriendInteractorTest {

    private CommonUser user;
    private CommonUser friend;
    private SendBirdUserDataAccessObject dataAccess;

    @BeforeEach
    void setUp() {
        CommonUserFactory factory = new CommonUserFactory();
        user = factory.create("User", "Password1");
        friend = factory.create("Friend", "Password2");
        dataAccess = new SendBirdUserDataAccessObject();
    }

    @Test
    void removeFriendInteractorSuccessTest(){
        user.addFriend(friend.getID());
        friend.addFriend(user.getID());
        dataAccess.save(friend);
        dataAccess.save(user);
        dataAccess.setCurrentUser(user);
        dataAccess.setCurrentUsername(user.getName());
        RemoveFriendInputData inputData = new RemoveFriendInputData(friend.getName());
        RemoveFriendOutputBoundary presenter = new RemoveFriendOutputBoundary() {
            @Override
            public void removeFriendPrepareSuccessView(RemoveFriendOutputData outputData) {
                assertEquals(user.getName(), outputData.getCurrentUsername());
                assertEquals(friend.getName(), outputData.getRemovedUsername());
                assertTrue(outputData.isSuccess());
                assertTrue(user.getFriendIDs().isEmpty());
                assertTrue(friend.getFriendIDs().isEmpty());
            }

            @Override
            public void removeFriendPrepareFailView(String errorMessage) {
                fail("The interactor fails success case");
            }
        };

        RemoveFriendInteractor interactor = new RemoveFriendInteractor(dataAccess, presenter);
        interactor.execute(inputData);
    }

    @AfterEach
    void tearDown() {
        if(dataAccess.existsByName(friend.getName())) {
            dataAccess.deleteUserById(friend.getID(), friend.getName());
        }
        dataAccess.deleteUserById(user.getID(), user.getName());
        dataAccess = null;
        friend = null;
        user = null;
    }
}
