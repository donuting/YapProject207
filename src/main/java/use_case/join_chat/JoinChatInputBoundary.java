package use_case.join_chat;

/**
 * The input boundary for the Join Chat use case.
 */
public interface JoinChatInputBoundary {
    /**
     * Execute the Join Chat use case.
     * @param inputData The input data for this use case.
     */
    void execute(JoinChatInputData inputData);

    /**
     * Switches the view to return to the list of chats
     */
    void switchToChatsView();
}
