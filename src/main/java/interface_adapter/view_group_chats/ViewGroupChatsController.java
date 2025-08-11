package interface_adapter.view_group_chats;

import interface_adapter.ViewManagerModel;
import interface_adapter.chat.ChatState;
import interface_adapter.chat.ChatViewModel;
import use_case.join_chat.JoinChatInputBoundary;
import use_case.join_chat.JoinChatInputData;
import use_case.leave_chat.LeaveChatInputBoundary;
import use_case.leave_chat.LeaveChatInputData;
import use_case.load_group_chats.LoadGroupChatsInputBoundary;

/**
 * The controller for the View Chats View.
 */
public class ViewGroupChatsController {
    private final JoinChatInputBoundary joinChatUseCaseInteractor;
    private final LeaveChatInputBoundary leaveChatUseCaseInteractor;
    private final LoadGroupChatsInputBoundary loadGroupChatsUseCaseInteractor;
    private final ChatViewModel chatViewModel;
    private final ViewManagerModel viewManagerModel;

    public ViewGroupChatsController(JoinChatInputBoundary joinChatUseCaseInteractor,
                                    LeaveChatInputBoundary leaveChatUseCaseInteractor,
                                    LoadGroupChatsInputBoundary loadGroupChatsUseCaseInteractor,
                                    ChatViewModel chatViewModel,
                                    ViewManagerModel viewManagerModel) {
        this.joinChatUseCaseInteractor = joinChatUseCaseInteractor;
        this.leaveChatUseCaseInteractor = leaveChatUseCaseInteractor;
        this.loadGroupChatsUseCaseInteractor = loadGroupChatsUseCaseInteractor;
        this.chatViewModel = chatViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    /**
     * Switches to the view chats view.
     */
    public void switchToViewChats() {
        viewManagerModel.setState("view chats");
        viewManagerModel.firePropertyChanged();
    }

    /**
     * Executes the Join Chat Use Case.
     * @param channelUrl the URL of the channel to be joined.
     */
    public void joinChat(String channelUrl) {
        JoinChatInputData joinChatInputData = new JoinChatInputData(channelUrl, null);
        joinChatUseCaseInteractor.execute(joinChatInputData);
    }

    /**
     * Executes the Leave Chat Use Case.
     * @param channelUrl the URL of the channel to be left.
     */
    public void leaveChat(String channelUrl) {
        LeaveChatInputData leaveChatInputData = new LeaveChatInputData(channelUrl);
        leaveChatUseCaseInteractor.execute(leaveChatInputData);
    }

    /**
     * Executes the View Chat Use Case.
     * @param channelUrl the URL of the channel to be viewed.
     * @param chatName the name of the chat to be viewed.
     */
    public void switchToViewChat(String channelUrl, String chatName) {
        // Update the active chat's channel URL and name
        ChatState chatState = chatViewModel.getState();
        chatState.setCurrentChannelUrl(channelUrl);
        chatState.setChatName(chatName);
        chatState.setGroupChat(true);

        // Updates the active chat's messages
        chatState.setNeedsUpdate(true);
        chatViewModel.setState(chatState);
        chatViewModel.firePropertyChanged();

        // Navigate to the chat view
        viewManagerModel.setState("chat");
        viewManagerModel.firePropertyChanged();
    }

    /**
     * Executes the Load Group Chats Use Case.
     */
    public void loadGroupChats() {
        loadGroupChatsUseCaseInteractor.execute();
    }
}
