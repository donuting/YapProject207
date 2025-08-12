package interface_adapter.self_chat;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonObject;

/**
 * The state for the Self Chat View Model.
 */
public class SelfChatState {
    private String username = "";
    // A map from the message ID to the message data (body text, message ID, timestamp)
    private Map<Integer, JsonObject> messageData = new HashMap<>();
    private String errorMessage = "";

    public SelfChatState(SelfChatState copy) {
        this.username = copy.username;
        this.messageData = copy.messageData;
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

    public Map<Integer, JsonObject> getMessages() {
        return new HashMap<>(messageData);
    }

    /**
     * Description.
     * @param data message data
     */
    public void addMessageData(Map<Integer, JsonObject> data) {
        this.messageData.putAll(data);
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Description.
     */
    public void clearMessages() {
        this.messageData.clear();
    }

    public int getMessageCount() {
        return messageData.size();
    }
}
