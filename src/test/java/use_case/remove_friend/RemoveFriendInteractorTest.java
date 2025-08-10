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
        RemoveFriendInputData inputData = new RemoveFriendInputData(friend.getName());
        RemoveFriendOutputBoundary presenter = new RemoveFriendOutputBoundary() {
            @Override
            public void prepareSuccessView(RemoveFriendOutputData outputData) {
                assertEquals(user.getName(), outputData.getCurrentUsername());
                assertEquals(friend.getName(), outputData.getRemovedUsername());
                assertTrue(outputData.isSuccess());
                assertTrue(user.getFriendIDs().isEmpty());
                assertTrue(friend.getFriendIDs().isEmpty());
            }

            @Override
            public void prepareFailView(String errorMessage, RemoveFriendOutputData outputData) {
                fail("The interactor fails success case");
            }
        };

        RemoveFriendInteractor interactor = new RemoveFriendInteractor(dataAccess, presenter);
        interactor.execute(inputData);
    }

    @Test
    // The user is not a friend
    void removeFriendInteractorFailTest1(){
        dataAccess.save(friend);
        dataAccess.save(user);
        RemoveFriendInputData inputData = new RemoveFriendInputData(friend.getName());
        RemoveFriendOutputBoundary presenter = new RemoveFriendOutputBoundary() {
            @Override
            public void prepareSuccessView(RemoveFriendOutputData outputData) {
                fail("The interactor does not check if the two users are friends");
            }

            @Override
            public void prepareFailView(String errorMessage, RemoveFriendOutputData outputData) {
                assertEquals(user.getName(), outputData.getCurrentUsername());
                assertEquals(friend.getName(), outputData.getRemovedUsername());
                assertFalse(outputData.isSuccess());
                assertTrue(user.getFriendIDs().isEmpty());
                assertTrue(friend.getFriendIDs().isEmpty());
            }
        };

        RemoveFriendInteractor interactor = new RemoveFriendInteractor(dataAccess, presenter);
        interactor.execute(inputData);
    }

    @Test
    // friend is blocked
    void removeFriendInteractorFailTest2(){
        user.addFriend(friend.getID());
        friend.addFriend(user.getID());
        user.blockUser(friend.getID());
        dataAccess.save(friend);
        dataAccess.save(user);
        RemoveFriendInputData inputData = new RemoveFriendInputData(friend.getName());
        RemoveFriendOutputBoundary presenter = new RemoveFriendOutputBoundary() {
            @Override
            public void prepareSuccessView(RemoveFriendOutputData outputData) {
                fail("Interactor does not check is the user is blocked");
            }

            @Override
            public void prepareFailView(String errorMessage, RemoveFriendOutputData outputData) {
                assertEquals("The user is blocked", errorMessage);
                assertEquals(user.getName(), outputData.getCurrentUsername());
                assertEquals(friend.getName(), outputData.getRemovedUsername());
                assertFalse(outputData.isSuccess());
                assertTrue(user.getFriendIDs().isEmpty());
                assertFalse(friend.getFriendIDs().isEmpty());

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
