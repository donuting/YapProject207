package interface_adapter.join_chat;

import use_case.join_chat.JoinChatInputBoundary;
import use_case.join_chat.JoinChatInputData;

/**
 * The Controller for the Join Chat Use Case.
 */
public class JoinChatController {
    private final JoinChatInputBoundary joinChatUseCaseInteractor;

    public JoinChatController(JoinChatInputBoundary joinChatUseCaseInteractor) {
        this.joinChatUseCaseInteractor = joinChatUseCaseInteractor;
    }

    /**
     * Executes the Signup Use Case.
     * @param channelUrl the URL of the channel to be joined.
     */
    public void execute(String channelUrl) {
        JoinChatInputData joinChatInputData = new JoinChatInputData(channelUrl);
        joinChatUseCaseInteractor.execute(joinChatInputData);
    }

    /**
     * Executes the "switch to View Chats View" Use Case.
     */
    public void switchToChatsView() {
        joinChatUseCaseInteractor.switchToChatsView();
    }
}
