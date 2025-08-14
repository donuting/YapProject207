package usecase.update_chat;

import entity.Message;

import java.util.List;

public class UpdateChatOutputData {
    private final String currentUserId;
    private final List<Message> messages;
    private final List<String> usernames;
    private final boolean success;

    public UpdateChatOutputData(String currentUserId, List<Message> messages, List<String> usernames, boolean success) {
        this.currentUserId = currentUserId;
        this.messages = messages;
        this.usernames = usernames;
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public List<String> getUsernames() {
        return usernames;
    }

    public String getCurrentUserId() {
        return currentUserId;
    }
}
