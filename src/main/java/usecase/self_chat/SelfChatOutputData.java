package usecase.self_chat;

import entity.Message;

import java.util.List;

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

    public String getUsername() {
        return username;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}

