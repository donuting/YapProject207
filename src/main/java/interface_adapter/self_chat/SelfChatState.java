package interface_adapter.self_chat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * The state for the Self Chat View Model.
 */
public class SelfChatState {
    private String username = "";
    private List<String> messages = new ArrayList<>();
    private List<LocalDateTime> timestamps = new ArrayList<>();
    private String errorMessage = "";

    public SelfChatState(SelfChatState copy) {
        this.username = copy.username;
        this.messages = new ArrayList<>(copy.messages);
        this.timestamps = new ArrayList<>(copy.timestamps);
        this.errorMessage = copy.errorMessage;
    }

    public SelfChatState() {
        // Default constructor
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getMessages() {
        return new ArrayList<>(messages);
    }

    public void setMessages(List<String> messages) {
        this.messages = new ArrayList<>(messages);
    }

    public void addMessage(String message, LocalDateTime timestamp) {
        this.messages.add(message);
        this.timestamps.add(timestamp);
    }

    public List<LocalDateTime> getTimestamps() {
        return new ArrayList<>(timestamps);
    }

    public void setTimestamps(List<LocalDateTime> timestamps) {
        this.timestamps = new ArrayList<>(timestamps);
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void clearMessages() {
        this.messages.clear();
        this.timestamps.clear();
    }

    public int getMessageCount() {
        return messages.size();
    }
}