package interfaceadapter.view_group_chats;

import dataaccess.SendBirdUserDataAccessObject;
import interfaceadapter.ViewManagerModel;
import interfaceadapter.chat.ChatState;
import interfaceadapter.chat.ChatViewModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import usecase.join_chat.JoinChatInputBoundary;
import usecase.join_chat.JoinChatInputData;
import usecase.join_chat.JoinChatInteractor;
import usecase.leave_chat.LeaveChatInputBoundary;
import usecase.leave_chat.LeaveChatInputData;
import usecase.leave_chat.LeaveChatInteractor;
import usecase.load_group_chats.LoadGroupChatsInputBoundary;
import usecase.load_group_chats.LoadGroupChatsInteractor;


class ViewGroupChatsControllerTest {
    final SendBirdUserDataAccessObject userDataAccessObject = new SendBirdUserDataAccessObject();
    final ViewGroupChatsPresenter viewGroupChatsPresenter = new ViewGroupChatsPresenter(new ViewGroupChatsViewModel());
    final ChatViewModel chatViewModel = new ChatViewModel();
    final ViewManagerModel viewManagerModel = new ViewManagerModel();
    final JoinChatInputBoundary joinChatInputBoundary =
            new JoinChatInteractor(userDataAccessObject, viewGroupChatsPresenter);
    final LeaveChatInputBoundary leaveChatInputBoundary =
            new LeaveChatInteractor(userDataAccessObject, viewGroupChatsPresenter);
    final LoadGroupChatsInputBoundary loadGroupChatsInputBoundary =
            new LoadGroupChatsInteractor(userDataAccessObject, viewGroupChatsPresenter);

    @Test
    void switchToViewChatsTest() {
        final ViewManagerModel viewManagerModelTest = new ViewManagerModel() {
            @Override
            public void firePropertyChanged() {
                Assertions.assertTrue(true);
            }
        };

        final ViewGroupChatsController viewGroupChatsController =
                new ViewGroupChatsController(joinChatInputBoundary, leaveChatInputBoundary,
                        loadGroupChatsInputBoundary, chatViewModel, viewManagerModelTest);

        viewGroupChatsController.switchToViewChats();
    }

    @Test
    void joinChatTest() {
        final JoinChatInputBoundary joinChatInteractorTest = new JoinChatInteractor(userDataAccessObject, viewGroupChatsPresenter) {
            @Override
            public void execute(JoinChatInputData joinChatInputData) {
                Assertions.assertEquals("channelUrl", joinChatInputData.getChannelUrl());
            }
        };

        String channelUrl = "channelUrl";
        final ViewGroupChatsController viewGroupChatsController =
                new ViewGroupChatsController(joinChatInteractorTest, leaveChatInputBoundary,
                        loadGroupChatsInputBoundary, chatViewModel, viewManagerModel);

        viewGroupChatsController.joinChat(channelUrl);
    }

    @Test
    void leaveChatTest() {
        final LeaveChatInputBoundary leaveChatInteractorTest = new LeaveChatInteractor(userDataAccessObject, viewGroupChatsPresenter) {
            @Override
            public void execute(LeaveChatInputData leaveChatInputData) {
                Assertions.assertEquals("channelUrl", leaveChatInputData.getChannelUrl());
            }
        };

        String channelUrl = "channelUrl";
        final ViewGroupChatsController viewGroupChatsController =
                new ViewGroupChatsController(joinChatInputBoundary, leaveChatInteractorTest,
                        loadGroupChatsInputBoundary, chatViewModel, viewManagerModel);

        viewGroupChatsController.leaveChat(channelUrl);
    }

    @Test
    void switchToViewChatTest() {
        final ViewManagerModel viewManagerModelTest = new ViewManagerModel() {
            @Override
            public void firePropertyChanged() {
                Assertions.assertTrue(true);
            }
        };
        final ChatViewModel chatViewModelTest = new ChatViewModel() {
            @Override
            public void firePropertyChanged() {
                ChatState state = getState();
                Assertions.assertEquals("channelUrl", state.getCurrentChannelUrl());
                Assertions.assertEquals("chatName", state.getChatName());
                Assertions.assertTrue(state.getIsGroupChat());
                Assertions.assertTrue(state.getNeedsUpdate());
                Assertions.assertTrue(state.getNeedsUpdate());
            }
        };
        String channelUrl = "channelUrl";
        String chatName = "chatName";
        final ViewGroupChatsController viewGroupChatsController =
                new ViewGroupChatsController(joinChatInputBoundary, leaveChatInputBoundary,
                        loadGroupChatsInputBoundary, chatViewModelTest, viewManagerModelTest);

        viewGroupChatsController.switchToViewChat(channelUrl, chatName);
    }
}
