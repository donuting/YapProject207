package use_case.update_chat;

/**
 * The input boundary for the Update Chat use case.
 */
public interface UpdateChatInputBoundary {
    /**
     * Execute the Update Chat use case.
     * @param inputData The input data for this use case.
     */
    void execute(UpdateChatInputData inputData);

    /**
     * Switches the view so that the chat is shown.
     */
    void switchToChatView();

    /**
     * Switches the view to return to the list of chats
     */
    void leaveChatView();
}
