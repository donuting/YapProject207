package usecase.add_friend;

import dataaccess.SendBirdUserDataAccessObject;
import entity.CommonUser;
import entity.CommonUserFactory;
import entity.GroupChat;
import entity.GroupChatFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AddFriendInteractorTest {

    private CommonUser user;
    private CommonUser friend;
    private GroupChat chat;
    private SendBirdUserDataAccessObject dataAccess = mock(SendBirdUserDataAccessObject.class);

    @BeforeEach
    void setUp() {
        CommonUserFactory factory = new CommonUserFactory();
        user = factory.create("User", "Password1");
        friend = factory.create("Friend", "Password2");
        List<String> memberID = new ArrayList<>();
        memberID.add(user.getID());
        memberID.add(friend.getID());
        chat = new GroupChat(memberID, user.getName()+", "+friend.getName(), new ArrayList<>());
        when(dataAccess.getCurrentUser()).thenReturn(user);
        when(dataAccess.getCurrentUsername()).thenReturn(user.getName());

    }

    @Test
    // Success Case
    void AddFriendSuccessTest() {
        when(dataAccess.get(friend.getName())).thenReturn(friend);
        when(dataAccess.existsByName(friend.getName())).thenReturn(true);
        when(dataAccess.create(chat.getMemberIds(), chat.getChatName(), new GroupChatFactory())).thenReturn(chat);
        AddFriendInputData inputData = new AddFriendInputData(friend.getName(), friend.getID());
        AddFriendOutputBoundary successPresenter = new AddFriendOutputBoundary() {
            @Override
            public void prepareSuccessView(AddFriendOutputData outputData) {
                assertEquals(friend.getName(), outputData.getFriendUsername());
                assertTrue(outputData.isSuccess());
                assertEquals(friend.getName() + " has been added!", outputData.getSuccessMessage());

            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Add Friend use case does not add friend");

            }

            @Override
            public void switchToMainMenuView() {
                fail("WTF, this is not supposed to happen");
            }
        };
        AddFriendInputBoundary addFriendInteractor = new AddFriendInteractor(dataAccess, successPresenter);
        addFriendInteractor.execute(inputData);

    }

    @Test
    //Friend is not a user
    void AddFriendFailureTest1() {
        when(dataAccess.get(friend.getName())).thenReturn(null);
        when(dataAccess.existsByName(friend.getName())).thenReturn(false);
        when(dataAccess.create(chat.getMemberIds(), chat.getChatName(), new GroupChatFactory())).thenReturn(chat);
        AddFriendInputData inputData = new AddFriendInputData(friend.getName(), friend.getID());
        AddFriendOutputBoundary failPresenter = new AddFriendOutputBoundary() {
            @Override
            public void prepareSuccessView(AddFriendOutputData outputData) {
                fail("Does not check if friend exists in the database");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("User does not exist", errorMessage);

            }

            @Override
            public void switchToMainMenuView() {
                fail("WTF, this is not supposed to happen");
            }
        };
        AddFriendInputBoundary addFriendInteractor = new AddFriendInteractor(dataAccess, failPresenter);
        addFriendInteractor.execute(inputData);
    }

    @Test
    //friend is blocked
    void AddFriendFailureTest2() {
        user.addFriend(friend.getID());
        user.blockUser(friend.getID());
        when(dataAccess.get(friend.getName())).thenReturn(friend);
        when(dataAccess.existsByName(friend.getName())).thenReturn(true);
        when(dataAccess.create(chat.getMemberIds(), chat.getChatName(), new GroupChatFactory())).thenReturn(chat);
        dataAccess.save(user);
        dataAccess.save(friend);
        AddFriendInputData inputData = new AddFriendInputData(friend.getName(), friend.getID());
        AddFriendOutputBoundary successPresenter = new AddFriendOutputBoundary() {
            @Override
            public void prepareSuccessView(AddFriendOutputData outputData) {
                fail("Interactor does not check if potential friend is blocked");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals(friend.getName() + " is blocked", errorMessage);

            }

            @Override
            public void switchToMainMenuView() {
                fail("WTF, this is not supposed to happen");
            }
        };
        AddFriendInputBoundary addFriendInteractor = new AddFriendInteractor(dataAccess, successPresenter);
        addFriendInteractor.execute(inputData);
    }

    @Test
    //friend is already a friend
    void AddFriendFailureTest3() {
        user.addFriend(friend.getID());
        when(dataAccess.get(friend.getName())).thenReturn(friend);
        when(dataAccess.existsByName(friend.getName())).thenReturn(true);
        when(dataAccess.create(chat.getMemberIds(), chat.getChatName(), new GroupChatFactory())).thenReturn(chat);
        dataAccess.save(user);
        dataAccess.save(friend);
        AddFriendInputData inputData = new AddFriendInputData(friend.getName(), friend.getID());
        AddFriendOutputBoundary successPresenter = new AddFriendOutputBoundary() {
            @Override
            public void prepareSuccessView(AddFriendOutputData outputData) {
                fail("Interactor does not check if users are already friends");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("You are already friends with " + friend.getName(), errorMessage);

            }

            @Override
            public void switchToMainMenuView() {
                fail("WTF, this is not supposed to happen");
            }
        };
        AddFriendInputBoundary addFriendInteractor = new AddFriendInteractor(dataAccess, successPresenter);
        addFriendInteractor.execute(inputData);
    }

    @Test
    //Trying to add yourself as a friend
    void AddFriendFailureTest4() {
        when(dataAccess.get(user.getName())).thenReturn(user);
        when(dataAccess.existsByName(user.getName())).thenReturn(true);
        when(dataAccess.create(chat.getMemberIds(), chat.getChatName(), new GroupChatFactory())).thenReturn(chat);
        dataAccess.save(user);
        dataAccess.save(friend);
        AddFriendInputData inputData = new AddFriendInputData(user.getName(), user.getID());
        AddFriendOutputBoundary successPresenter = new AddFriendOutputBoundary() {
            @Override
            public void prepareSuccessView(AddFriendOutputData outputData) {
                fail("Interactor does not check if users are already friends ");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("You cannot add yourself as a friend (friend ID must be different from yours)", errorMessage);

            }

            @Override
            public void switchToMainMenuView() {
                fail("WTF, this is not supposed to happen");
            }
        };
        AddFriendInputBoundary addFriendInteractor = new AddFriendInteractor(dataAccess, successPresenter);
        addFriendInteractor.execute(inputData);

    }

    @Test
    // Your user name is incorrect
    void AddFriendFailureTest6() {
        when(dataAccess.get(user.getName())).thenReturn(user);
        when(dataAccess.existsByName(user.getName())).thenReturn(true);
        when(dataAccess.create(chat.getMemberIds(), chat.getChatName(), new GroupChatFactory())).thenReturn(chat);
        dataAccess.save(user);
        dataAccess.save(friend);
        AddFriendInputData inputData = new AddFriendInputData( user.getName(), friend.getID());
        AddFriendOutputBoundary sucessPresenter = new AddFriendOutputBoundary() {
            @Override
            public void prepareSuccessView(AddFriendOutputData outputData) {
                fail("Interactor does not make sure the data entered is correct");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("Incorrect info added", errorMessage);

            }

            @Override
            public void switchToMainMenuView() {
                fail("WTF, this is not supposed to happen");
            }
        };
        AddFriendInputBoundary addFriendInteractor = new AddFriendInteractor(dataAccess, sucessPresenter);
        addFriendInteractor.execute(inputData);

    }



    @AfterEach
    void tearDown() {
        user = null;
        friend = null;
        dataAccess = null;
    }
}
