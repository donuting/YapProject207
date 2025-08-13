package usecase.self_chat;

import java.util.List;

import entity.Message;

/**
 * Output Data for the Self Chat Use Case.
 */
public class SelfChatOutputData {
    private final String username;
    private final List<Message> messages;
    private final boolean success;
    private final String errorMessage;

    public SelfChatOutputData(String username, List<Message> messages, boolean success, String errorMessage) {
        this.username = username;
        this.messages = messages;
        this.success = success;
        this.errorMessage = errorMessage;
    }

    /**
     * Gets the username outputted by the interactor.
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the messages outputted by the interactor.
     * @return the messages
     */
    public List<Message> getMessages() {
        return messages;
    }

    /**
     * Returns whether the use case succeeded.
     * @return true if successful
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Gets the error message outputted by the interactor.
     * @return the error message
     */
    public String getErrorMessage() {
        return errorMessage;
    }
}

