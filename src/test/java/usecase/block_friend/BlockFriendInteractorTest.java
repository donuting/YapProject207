package usecase.block_friend;

import dataaccess.SendBirdUserDataAccessObject;
import entity.CommonUserFactory;
import entity.GroupChat;
import entity.Message;
import entity.User;
import entity.UserFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BlockFriendInteractorTest {

    private SendBirdUserDataAccessObject dataAccess;
    private UserFactory userFactory;
    private User user;
    private User friend;

    @BeforeEach
    void setUp() {
        dataAccess = new SendBirdUserDataAccessObject();
        userFactory = new CommonUserFactory();
        user = userFactory.create("User", "Password1");
        friend = userFactory.create("Friend", "Password2");
        dataAccess.save(user);
        dataAccess.save(friend);
        dataAccess.setCurrentUser(user);
        dataAccess.setCurrentUsername(user.getName());
    }

    @Test
    void blockFriendSuccessTest() {

        List<String> memberIds = new ArrayList<>();
        memberIds.add(user.getName());
        memberIds.add(friend.getName());
        String chatName = "TestChat";
        List<Message> messageHistory = new ArrayList<>();
        String channelUrl = "test-channel-url";
        GroupChat personalChat = new GroupChat(memberIds, chatName, messageHistory, channelUrl);

        dataAccess.addFriend(user.getName(), friend.getName(), personalChat);
        BlockFriendInputData inputData = new BlockFriendInputData(friend.getID());
        BlockFriendOutputBoundary successPresenter = new BlockFriendOutputBoundary() {
            @Override
            public void blockFriendPrepareSuccessView(BlockFriendOutputData outputData) {
                assertEquals(user.getName(), outputData.getCurrentUsername());
                assertEquals(friend.getName(), outputData.getBlockedUsername());
                assertTrue(outputData.isSuccess());
            }

            @Override
            public void blockFriendPrepareFailView(String errorMessage, BlockFriendOutputData outputData) {
                fail("Block friend use case should succeed");
            }
        };
        BlockFriendInteractor interactor = new BlockFriendInteractor(dataAccess, successPresenter);
        interactor.execute(inputData);
    }

    @Test
    void blockFriendFailFriendDoesNotExistTest() {
        BlockFriendInputData inputData = new BlockFriendInputData("NonExistentUser");
        BlockFriendOutputBoundary failPresenter = new BlockFriendOutputBoundary() {
            @Override
            public void blockFriendPrepareSuccessView(BlockFriendOutputData outputData) {
                fail("Block friend use case should fail when blocked user does not exist");
            }

            @Override
            public void blockFriendPrepareFailView(String errorMessage, BlockFriendOutputData outputData) {
                assertFalse(outputData.isSuccess());
                assertEquals("Failed to block friend.", errorMessage);
            }
        };
        BlockFriendInteractor interactor = new BlockFriendInteractor(dataAccess, failPresenter);
        interactor.execute(inputData);
    }

    @AfterEach
    void tearDown() {
        dataAccess.deleteUserById(friend.getID(), friend.getName());
        dataAccess.deleteUserById(user.getID(), user.getName());
        dataAccess = null;
        user = null;
        friend = null;
        userFactory = null;
    }
}
