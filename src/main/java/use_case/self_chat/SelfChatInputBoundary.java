package use_case.self_chat;

public interface SelfChatInputBoundary {

    /**
     * Executes the self chat use case.
     * @param selfChatInputData the input data
     */
    void execute(SelfChatInputData selfChatInputData);

    /**
     * Clears all messages in the self chat.
     */
    void clearMessages();

    /**
     * Loads existing messages from storage.
     */
    void loadMessages();
}