package use_case.block_friends;

import data_access.InMemoryUserDataAccessObject;
import data_access.SendBirdUserDataAccessObject;
import entity.CommonUser;
import entity.CommonUserFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.block_friend.BlockFriendInputData;
import use_case.block_friend.BlockFriendInteractor;
import use_case.block_friend.BlockFriendOutputBoundary;
import use_case.block_friend.BlockFriendOutputData;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BlockFriendsInteractorTest {

    private CommonUser user;
    private CommonUser friend;
    private SendBirdUserDataAccessObject dataAccess;

    @BeforeEach
    void setUp() {
        CommonUserFactory factory = new CommonUserFactory();
        user = factory.create("User", "Password1");
        friend = factory.create("Friend", "Password2");
        dataAccess = new SendBirdUserDataAccessObject();
        dataAccess.setCurrentUser(user);
    }

    @Test
    // success test for Block Friend
    void blockFriendsInteractorSuccessTest() {
        user.addFriend(friend.getID());
        friend.addFriend(user.getID());
        dataAccess.save(user);
        dataAccess.save(friend);
        BlockFriendInputData inputData = new BlockFriendInputData(friend.getName());
        BlockFriendOutputBoundary presenter = new BlockFriendOutputBoundary() {
            @Override
            public void prepareSuccessView(BlockFriendOutputData outputData) {
                assertEquals(user.getName(), outputData.getCurrentUsername());
                assertEquals(friend.getName(), outputData.getBlockedUsername());
                assertTrue(outputData.isSuccess());
                List<String> blocked = user.getBlockedUserIDs();
                List<String> friends = user.getFriendIDs();
                assertTrue(blocked.contains(friend.getID()));
                assertFalse(friends.contains(friend.getID()));
                friends = friend.getFriendIDs();
                assertTrue(friends.contains(user.getID()));
            }

            @Override
            public void prepareFailView(String errorMessage, BlockFriendOutputData outputData) {
                fail("Interactor fails success case");
            }
        };
        BlockFriendInteractor interactor = new BlockFriendInteractor(dataAccess, presenter);
        interactor.execute(inputData);
    }

    @Test
    // blocked person is not a friend
    void blockFriendsInteractorFailTest1() {
        dataAccess.save(user);
        dataAccess.save(friend);
        BlockFriendInputData inputData = new BlockFriendInputData(friend.getName());
        BlockFriendOutputBoundary presenter = new BlockFriendOutputBoundary() {
            @Override
            public void prepareSuccessView(BlockFriendOutputData outputData) {
                fail("Interactor does not check if users are friend");
            }

            @Override
            public void prepareFailView(String errorMessage, BlockFriendOutputData outputData) {
                assertEquals("Failed to block friend.", errorMessage);
                assertEquals(user.getName(), outputData.getCurrentUsername());
                assertEquals(friend.getName(), outputData.getBlockedUsername());
                assertFalse(outputData.isSuccess());
                List<String> blocked = user.getBlockedUserIDs();
                List<String> friends = user.getFriendIDs();
                assertFalse(blocked.contains(friend.getID()));
                assertFalse(friends.contains(friend.getID()));
                friends = friend.getFriendIDs();
                assertFalse(friends.contains(user.getID()));
            }
        };
        BlockFriendInteractor interactor = new BlockFriendInteractor(dataAccess, presenter);
        interactor.execute(inputData);
    }

    @Test
    // blocked user does not exist
    void blockFriendsInteractorFailTest2() {
        dataAccess.save(user);
        BlockFriendInputData inputData = new BlockFriendInputData(friend.getName());
        BlockFriendOutputBoundary presenter = new BlockFriendOutputBoundary() {
            @Override
            public void prepareSuccessView(BlockFriendOutputData outputData) {
                fail("Interactor does not check if user exists");
            }

            @Override
            public void prepareFailView(String errorMessage, BlockFriendOutputData outputData) {
                assertEquals("The user you are trying to block does not exist", errorMessage);
                assertEquals(user.getName(), outputData.getCurrentUsername());
                assertEquals(friend.getName(), outputData.getBlockedUsername());
                assertFalse(outputData.isSuccess());
            }
        };
        BlockFriendInteractor interactor = new BlockFriendInteractor(dataAccess, presenter);
        interactor.execute(inputData);
    }

    @Test
    // Trying to block yourself
    void blockFriendsInteractorFailTest3() {
        dataAccess.save(user);
        BlockFriendInputData inputData = new BlockFriendInputData(user.getName());
        BlockFriendOutputBoundary presenter = new BlockFriendOutputBoundary() {
            @Override
            public void prepareSuccessView(BlockFriendOutputData outputData) {
                fail("Interactor does not check if users is blocking themselves");
            }

            @Override
            public void prepareFailView(String errorMessage, BlockFriendOutputData outputData) {
                assertEquals("You cannot block yourself.", errorMessage);
                assertEquals(user.getName(), outputData.getCurrentUsername());
                assertEquals(friend.getName(), outputData.getBlockedUsername());
                assertFalse(outputData.isSuccess());
                List<String> blocked = user.getBlockedUserIDs();
                List<String> friends = user.getFriendIDs();
                assertFalse(blocked.contains(friend.getID()));
                assertFalse(friends.contains(friend.getID()));
                friends = friend.getFriendIDs();
                assertFalse(friends.contains(user.getID()));
            }
        };
        BlockFriendInteractor interactor = new BlockFriendInteractor(dataAccess, presenter);
        interactor.execute(inputData);
    }

    @Test
    // Trying to block someone who is already blocked
    void blockFriendsInteractorFailTest4() {
        user.addFriend(friend.getID());
        friend.addFriend(user.getID());
        user.blockUser(friend.getID());
        dataAccess.save(user);
        dataAccess.save(friend);
        BlockFriendInputData inputData = new BlockFriendInputData(friend.getName());
        BlockFriendOutputBoundary presenter = new BlockFriendOutputBoundary() {
            @Override
            public void prepareSuccessView(BlockFriendOutputData outputData) {
                fail("Interactor does not check if user is already blocked");
            }

            @Override
            public void prepareFailView(String errorMessage, BlockFriendOutputData outputData) {
                assertEquals("Cannot block a blocked user.", errorMessage);
                assertEquals(user.getName(), outputData.getCurrentUsername());
                assertEquals(friend.getName(), outputData.getBlockedUsername());
                assertFalse(outputData.isSuccess());
                List<String> blocked = user.getBlockedUserIDs();
                List<String> friends = user.getFriendIDs();
                assertFalse(blocked.contains(friend.getID()));
                assertFalse(friends.contains(friend.getID()));
                friends = friend.getFriendIDs();
                assertFalse(friends.contains(user.getID()));
            }
        };
        BlockFriendInteractor interactor = new BlockFriendInteractor(dataAccess, presenter);
        interactor.execute(inputData);
    }




    @AfterEach
    void tearDown() {
        dataAccess.deleteUserById(user.getID(), user.getName());
        dataAccess.deleteUserById(friend.getID(), friend.getName());
        user = null;
        friend = null;
        dataAccess = null;
    }
}
