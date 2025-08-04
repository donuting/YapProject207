package use_case.update_chat;

import entity.Message;

import java.util.List;

public class UpdateChatOutputData {
    private final List<Message> newMessages;
    private final List<Message> removedMessages;
    private final List<String> newUsers;
    private final List<String> removedUsers;
    private final boolean success;

    public UpdateChatOutputData(List<Message> newMessages, List<Message> removedMessages, List<String> newUsers, List<String> removedUsers, boolean success) {
        this.newMessages = newMessages;
        this.removedMessages = removedMessages;
        this.newUsers = newUsers;
        this.removedUsers = removedUsers;
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public List<String> getNewUsers() {
        return newUsers;
    }

    public List<Message> getNewMessages() {
        return newMessages;
    }

    public List<String> getRemovedUsers() {
        return removedUsers;
    }

    public List<Message> getRemovedMessages() {
        return removedMessages;
    }
}
