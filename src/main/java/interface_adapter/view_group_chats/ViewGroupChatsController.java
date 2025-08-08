package interface_adapter.view_group_chats;

import interface_adapter.ViewManagerModel;
import use_case.join_chat.JoinChatInputBoundary;
import use_case.join_chat.JoinChatInputData;
import use_case.leave_chat.LeaveChatInputBoundary;
import use_case.leave_chat.LeaveChatInputData;
import use_case.load_group_chats.LoadGroupChatsInputBoundary;
import use_case.update_chat.UpdateChatInputBoundary;
import use_case.update_chat.UpdateChatInputData;

/**
 * The controller for the View Chats View.
 */
public class ViewGroupChatsController {
    private final JoinChatInputBoundary joinChatUseCaseInteractor;
    private final LeaveChatInputBoundary leaveChatUseCaseInteractor;
    private final LoadGroupChatsInputBoundary loadGroupChatsUseCaseInteractor;
    private final ViewManagerModel viewManagerModel;

    public ViewGroupChatsController(JoinChatInputBoundary joinChatUseCaseInteractor,
                                    LeaveChatInputBoundary leaveChatUseCaseInteractor,
                                    LoadGroupChatsInputBoundary loadGroupChatsUseCaseInteractor,
                                    ViewManagerModel viewManagerModel) {
        this.joinChatUseCaseInteractor = joinChatUseCaseInteractor;
        this.leaveChatUseCaseInteractor = leaveChatUseCaseInteractor;
        this.loadGroupChatsUseCaseInteractor = loadGroupChatsUseCaseInteractor;
        this.viewManagerModel = viewManagerModel;
    }

    public void switchToViewChats() {
        viewManagerModel.setState("view chats");
        viewManagerModel.firePropertyChanged();
    }

    /**
     * Executes the Join Chat Use Case.
     * @param channelUrl the URL of the channel to be joined.
     */
    public void joinChat(String channelUrl) {
        JoinChatInputData joinChatInputData = new JoinChatInputData(channelUrl);
        joinChatUseCaseInteractor.execute(joinChatInputData);
    }

    /**
     * Executes the Leave Chat Use Case.
     * @param channelUrl the URL of the channel to be joined.
     */
    public void leaveChat(String channelUrl) {
        LeaveChatInputData leaveChatInputData = new LeaveChatInputData(channelUrl);
        leaveChatUseCaseInteractor.execute(leaveChatInputData);
    }

    /**
     * Executes the View Chat Use Case.
     * @param channelUrl the URL of the channel to be viewed.
     */
    public void switchToViewChat(String channelUrl) {
        // Todo
    }

    /**
     * Executes the Load Group Chats Use Case.
     */
    public void loadGroupChats() {
        loadGroupChatsUseCaseInteractor.execute();
    }
}
