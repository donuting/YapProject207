package use_case.remove_friend;

import data_access.InMemoryUserDataAccessObject;
import data_access.SendBirdUserDataAccessObject;
import entity.CommonUser;
import entity.CommonUserFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RemoveFriendInteractorTest {

    private CommonUser user;
    private CommonUser friend;
    private SendBirdUserDataAccessObject dataAccess;

    @BeforeEach
    void setUp() {
        CommonUserFactory factory = new CommonUserFactory();
        user = factory.create("User", "Password1");
        friend = factory.create("Friend", "Password2");
        dataAccess = mock(SendBirdUserDataAccessObject.class);
    }

    @Test
    void removeFriendInteractorSuccessTest(){
        user.addFriend(friend.getID());
        friend.addFriend(user.getID());
        when(dataAccess.get(friend.getName())).thenReturn(friend);
        when(dataAccess.get(user.getName())).thenReturn(user);
        when(dataAccess.getCurrentUser()).thenReturn(user);
        when(dataAccess.getCurrentUsername()).thenReturn(user.getName());
        when(dataAccess.getUsernameFromId(friend.getID())).thenReturn(friend.getName());
        when(dataAccess.existsByName(friend.getName())).thenReturn(true);
        when(dataAccess.removeFriend(user, friend.getName(), friend.getID())).thenReturn(true);
        RemoveFriendInputData inputData = new RemoveFriendInputData(friend.getName());
        RemoveFriendOutputBoundary presenter = new RemoveFriendOutputBoundary() {
            @Override
            public void removeFriendPrepareSuccessView(RemoveFriendOutputData outputData) {
                assertEquals(user.getName(), outputData.getCurrentUsername());
                assertEquals(friend.getName(), outputData.getRemovedUsername());
                assertTrue(outputData.isSuccess());
            }

            @Override
            public void removeFriendPrepareFailView(String errorMessage) {
                fail("The interactor fails success case");
            }
        };

        RemoveFriendInteractor interactor = new RemoveFriendInteractor(dataAccess, presenter);
        interactor.execute(inputData);
    }

    @Test
    void removeFriendInteractorSuccessTest2(){
        user.addFriend(friend.getID());
        friend.addFriend(user.getID());
        when(dataAccess.get(friend.getName())).thenReturn(friend);
        when(dataAccess.get(user.getName())).thenReturn(user);
        when(dataAccess.getCurrentUser()).thenReturn(user);
        when(dataAccess.getCurrentUsername()).thenReturn(user.getName());
        when(dataAccess.getUsernameFromId(friend.getID())).thenReturn(friend.getName());
        when(dataAccess.existsByName(friend.getName())).thenReturn(false);
        when(dataAccess.removeFriend(user, friend.getName(), friend.getID())).thenReturn(true);
        RemoveFriendInputData inputData = new RemoveFriendInputData(friend.getName());
        RemoveFriendOutputBoundary presenter = new RemoveFriendOutputBoundary() {
            @Override
            public void removeFriendPrepareSuccessView(RemoveFriendOutputData outputData) {
                fail("The interactor succeeds fail case");
            }

            @Override
            public void removeFriendPrepareFailView(String errorMessage) {
                assertEquals("User does not exist", errorMessage);
            }
        };

        RemoveFriendInteractor interactor = new RemoveFriendInteractor(dataAccess, presenter);
        interactor.execute(inputData);
    }

    @Test
    void removeFriendInteractorSuccessTest3(){
        when(dataAccess.get(friend.getName())).thenReturn(friend);
        when(dataAccess.get(user.getName())).thenReturn(user);
        when(dataAccess.getCurrentUser()).thenReturn(user);
        when(dataAccess.getCurrentUsername()).thenReturn(user.getName());
        when(dataAccess.getUsernameFromId(friend.getID())).thenReturn(friend.getName());
        when(dataAccess.existsByName(friend.getName())).thenReturn(true);
        when(dataAccess.removeFriend(user, friend.getName(), friend.getID())).thenReturn(true);
        RemoveFriendInputData inputData = new RemoveFriendInputData(friend.getName());
        RemoveFriendOutputBoundary presenter = new RemoveFriendOutputBoundary() {
            @Override
            public void removeFriendPrepareSuccessView(RemoveFriendOutputData outputData) {
                fail("The interactor succeeds fail case");
            }

            @Override
            public void removeFriendPrepareFailView(String errorMessage) {
                assertEquals("You cannot remove someone who is not your friend", errorMessage);
            }
        };

        RemoveFriendInteractor interactor = new RemoveFriendInteractor(dataAccess, presenter);
        interactor.execute(inputData);
    }

    @Test
    void removeFriendInteractorSuccessTest4(){
        user.addFriend(friend.getID());
        friend.addFriend(user.getID());
        when(dataAccess.get(friend.getName())).thenReturn(friend);
        when(dataAccess.get(user.getName())).thenReturn(user);
        when(dataAccess.getCurrentUser()).thenReturn(user);
        when(dataAccess.getCurrentUsername()).thenReturn(user.getName());
        when(dataAccess.getUsernameFromId(friend.getID())).thenReturn(friend.getName());
        when(dataAccess.existsByName(friend.getName())).thenReturn(true);
        when(dataAccess.removeFriend(user, friend.getName(), friend.getID())).thenReturn(false);
        RemoveFriendInputData inputData = new RemoveFriendInputData(friend.getName());
        RemoveFriendOutputBoundary presenter = new RemoveFriendOutputBoundary() {
            @Override
            public void removeFriendPrepareSuccessView(RemoveFriendOutputData outputData) {
                fail("The interactor succeeds fail case");
            }

            @Override
            public void removeFriendPrepareFailView(String errorMessage) {
                assertEquals("Failed to remove friend", errorMessage);
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
