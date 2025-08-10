package use_case.update_chat;

/**
 * The input boundary for the Update Chat use case.
 */
public interface UpdateChatInputBoundary {
    /**
     * Execute the Update Chat use case.
     */
    void execute(UpdateChatInputData updateChatInputData);

    /**
     * Switches the view to return to the list of chats
     */
    void leaveChatView();
}
