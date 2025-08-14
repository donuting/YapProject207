package interfaceadapter.view_friends;

import dataaccess.SendBirdUserDataAccessObject;
import interfaceadapter.ViewManagerModel;
import interfaceadapter.chat.ChatViewModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import usecase.block_friend.BlockFriendInputBoundary;
import usecase.block_friend.BlockFriendInputData;
import usecase.block_friend.BlockFriendInteractor;
import usecase.load_friends.LoadFriendsInputBoundary;
import usecase.load_friends.LoadFriendsInteractor;
import usecase.remove_friend.RemoveFriendInputBoundary;
import usecase.remove_friend.RemoveFriendInputData;
import usecase.remove_friend.RemoveFriendInteractor;

class ViewFriendsControllerTest {
    final SendBirdUserDataAccessObject userDataAccessObject = new SendBirdUserDataAccessObject();
    final ViewFriendsPresenter viewFriendsPresenter = new ViewFriendsPresenter(new ViewFriendsViewModel());
    final ChatViewModel chatViewModel = new ChatViewModel();
    final ViewManagerModel viewManagerModel = new ViewManagerModel();
    final RemoveFriendInputBoundary removeFriendInputBoundary =
            new RemoveFriendInteractor(userDataAccessObject, viewFriendsPresenter);
    final BlockFriendInputBoundary blockFriendInputBoundary =
            new BlockFriendInteractor(userDataAccessObject, viewFriendsPresenter);
    final LoadFriendsInputBoundary loadFriendsInputBoundary =
            new LoadFriendsInteractor(userDataAccessObject, viewFriendsPresenter);


    @Test
    void switchToMainMenuTest() {
        final ViewManagerModel viewManagerModelTest = new ViewManagerModel() {
            @Override
            public void firePropertyChanged() {
                Assertions.assertEquals("main menu", getState());
            }
        };

        final ViewFriendsController viewFriendsController =
                new ViewFriendsController(removeFriendInputBoundary, blockFriendInputBoundary,
                        loadFriendsInputBoundary, chatViewModel, viewManagerModelTest);

        viewFriendsController.switchToMainMenu();
    }

    @Test
    void switchToAddFriendTest() {
        final ViewManagerModel viewManagerModelTest = new ViewManagerModel() {
            @Override
            public void firePropertyChanged() {
                Assertions.assertEquals("add friend", getState());
            }
        };

        final ViewFriendsController viewFriendsController =
                new ViewFriendsController(removeFriendInputBoundary, blockFriendInputBoundary,
                        loadFriendsInputBoundary, chatViewModel, viewManagerModelTest);

        viewFriendsController.switchToAddFriend();
    }

    @Test
    void switchToViewChatTest() {
        final ViewManagerModel viewManagerModelTest = new ViewManagerModel() {
            @Override
            public void firePropertyChanged() {
                Assertions.assertEquals("chat", getState());
            }
        };
        final ChatViewModel chatViewModelTest = new ChatViewModel() {
            @Override
            public void firePropertyChanged() {
                Assertions.assertEquals("channelUrl", getState().getCurrentChannelUrl());
                Assertions.assertEquals("chatName", getState().getChatName());
                Assertions.assertFalse(getState().getIsGroupChat());
                Assertions.assertTrue(getState().getNeedsUpdate());
            }
        };
        String channelUrl = "channelUrl";
        String chatName = "chatName";
        final ViewFriendsController viewFriendsController =
                new ViewFriendsController(removeFriendInputBoundary, blockFriendInputBoundary,
                        loadFriendsInputBoundary, chatViewModelTest, viewManagerModelTest);

        viewFriendsController.switchToViewChat(channelUrl, chatName);
    }

    @Test
    void blockTest() {
        final BlockFriendInputBoundary blockFriendInteractorTest = new BlockFriendInteractor(userDataAccessObject, viewFriendsPresenter) {
            @Override
            public void execute(BlockFriendInputData blockFriendInputData) {
                Assertions.assertEquals("blockedId", blockFriendInputData.getBlockedId());
            }
        };

        String blockedId = "blockedId";
        final ViewFriendsController viewFriendsController =
                new ViewFriendsController(removeFriendInputBoundary, blockFriendInteractorTest,
                        loadFriendsInputBoundary, chatViewModel, viewManagerModel);

        viewFriendsController.block(blockedId);
    }

    @Test
    void removeFriendTest() {
        final RemoveFriendInputBoundary removeFriendInteractorTest = new RemoveFriendInteractor(userDataAccessObject, viewFriendsPresenter) {
            @Override
            public void execute(RemoveFriendInputData removeFriendInputData) {
                Assertions.assertEquals("removedId", removeFriendInputData.getRemovedId());
            }
        };
        final

        String removedId = "removedId";
        final ViewFriendsController viewFriendsController =
                new ViewFriendsController(removeFriendInteractorTest, blockFriendInputBoundary,
                        loadFriendsInputBoundary, chatViewModel, viewManagerModel);

        viewFriendsController.removeFriend(removedId);
    }


}
