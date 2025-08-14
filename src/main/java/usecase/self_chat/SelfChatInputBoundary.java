package usecase.self_chat;

/**
 * The Input Boundary for actions which are related to self chat.
 */
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
     * Loads existing messages in the self chat.
     */
    void loadMessages();

    /**
     * Saves a birthday entry to the self chat.
     * @param name the name of the person
     * @param date the birthday in YYYYMMDD format
     */
    void saveBirthday(String name, String date);
}
