package use_case.add_friend;

import data_access.InMemoryUserDataAccessObject;
import data_access.SendBirdUserDataAccessObject;
import entity.CommonUser;
import entity.CommonUserFactory;
import entity.GroupChat;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AddFriendInteractorTest {

    private CommonUser user;
    private CommonUser friend;
    private InMemoryUserDataAccessObject dataAccess;

    @BeforeEach
    void setUp() {
        CommonUserFactory factory = new CommonUserFactory();
        user = factory.create("User", "Password1");
        friend = factory.create("Friend", "Password2");
        dataAccess = new InMemoryUserDataAccessObject();
    }

    @Test
    // Success Case
    void AddFriendSuccessTest() {
        dataAccess.save(user);
        dataAccess.save(friend);
        AddFriendInputData inputData = new AddFriendInputData(user.getName(), friend.getName(), friend.getID());
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
        dataAccess.save(user);
        AddFriendInputData inputData = new AddFriendInputData(user.getName(), friend.getName(), friend.getID());
        AddFriendOutputBoundary failPresenter = new AddFriendOutputBoundary() {
            @Override
            public void prepareSuccessView(AddFriendOutputData outputData) {
                fail("Does not check if friend exists in the database");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("User " + friend.getName() + " does not exist", errorMessage);

            }

            @Override
            public void switchToMainMenuView() {
                fail("WTF, this is not supposed to happen");
            }
        };
        AddFriendInputBoundary addFriendInteractor = new AddFriendInteractor(dataAccess, failPresenter);
        addFriendInteractor.execute(inputData);
        assert user.getFriendIDs().isEmpty();
        assert friend.getFriendIDs().isEmpty();
        assert user.getGroupChats().isEmpty();
        assert friend.getGroupChats().isEmpty();
    }

    @Test
    //friend is blocked
    void AddFriendFailureTest2() {
        CommonUser commonUser = (CommonUser) user;
        CommonUser commonFriend = (CommonUser) friend;
        commonUser.addFriend(commonFriend.getID());
        commonUser.blockUser(commonFriend.getID());
        dataAccess.save(commonUser);
        dataAccess.save(commonFriend);
        AddFriendInputData inputData = new AddFriendInputData(commonUser.getName(), commonFriend.getName(), commonFriend.getID());
        AddFriendOutputBoundary successPresenter = new AddFriendOutputBoundary() {
            @Override
            public void prepareSuccessView(AddFriendOutputData outputData) {
                fail("Interactor does not check if potential friend is blocked");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals(commonFriend.getName() + " is blocked", errorMessage);

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
        CommonUser commonUser = (CommonUser) user;
        CommonUser commonFriend = (CommonUser) friend;
        commonUser.addFriend(commonFriend.getID());
        dataAccess.save(commonUser);
        dataAccess.save(commonFriend);
        AddFriendInputData inputData = new AddFriendInputData(commonUser.getName(), commonFriend.getName(), commonFriend.getID());
        AddFriendOutputBoundary successPresenter = new AddFriendOutputBoundary() {
            @Override
            public void prepareSuccessView(AddFriendOutputData outputData) {
                fail("Interactor does not check if users are already friends");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("You are already friends with " + commonFriend.getName(), errorMessage);

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
        dataAccess.save(user);
        dataAccess.save(friend);
        AddFriendInputData inputData = new AddFriendInputData(user.getName(), user.getName(), user.getID());
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
    // Friend's username and ID do not match
    void AddFriendFailureTest5() {
        CommonUser friend2 = new CommonUser("Friends2", "Password1", "-1",
                "Bio", "20250823", new ArrayList<String>(),
                new ArrayList<String>(), new ArrayList<GroupChat>(),
                new ArrayList<GroupChat>());
        dataAccess.save(user);
        dataAccess.save(friend);
        dataAccess.save(friend2);
        AddFriendInputData inputData = new AddFriendInputData(user.getName(), friend2.getName(), friend.getID());
        AddFriendOutputBoundary successPresenter = new AddFriendOutputBoundary() {
            @Override
            public void prepareSuccessView(AddFriendOutputData outputData) {
                fail("Interactor does not check if the friend.username and friend.UID match");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("User " + friend2.getName() + " does not exist", errorMessage);

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
    // Your username is incorrect
    void AddFriendFailureTest6() {
        dataAccess.save(user);
        dataAccess.save(friend);
        AddFriendInputData inputData = new AddFriendInputData("Incorrect Name", friend.getName(), friend.getID());
        AddFriendOutputBoundary successPresenter = new AddFriendOutputBoundary() {
            @Override
            public void prepareSuccessView(AddFriendOutputData outputData) {
                fail("Interactor does not make sure the data entered is correct");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("Your account name is incorrect", errorMessage);

            }

            @Override
            public void switchToMainMenuView() {
                fail("WTF, this is not supposed to happen");
            }
        };
        AddFriendInputBoundary addFriendInteractor = new AddFriendInteractor(dataAccess, successPresenter);
        addFriendInteractor.execute(inputData);

    }



    @AfterEach
    void tearDown() {
        user = null;
        friend = null;
        dataAccess = null;
    }
}
