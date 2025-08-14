package usecase.update_chat;

import java.util.List;

import entity.Message;

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

    /**
     * Returns whether the use case succeeded.
     * @return true if successful
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Gets the list of messages outputted by the interactor.
     * @return the list of messages
     */
    public List<Message> getMessages() {
        return messages;
    }

    /**
     * Gets the list of usernames outputted by the interactor.
     * @return the list of usernames
     */
    public List<String> getUsernames() {
        return usernames;
    }

    /**
     * Gets the current user ID outputted by the interactor.
     * @return the current user ID
     */
    public String getCurrentUserId() {
        return currentUserId;
    }
}
