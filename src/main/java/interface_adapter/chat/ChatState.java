package interface_adapter.chat;

import entity.GroupChat;
import entity.Message;
import java.util.List;
import java.util.ArrayList;

/**
 * The State information representing the logged-in user.
 */
public class ChatState {
    private GroupChat currentChat = null;
    private String chatName = "";
    private List<Message> messages = new ArrayList<>();
    private String currentMessage = "";
    private String error = null;

    public ChatState(ChatState copy) {
        this.currentChat = copy.currentChat;
        this.chatName = copy.chatName;
        this.messages = new ArrayList<>(copy.messages);
        this.currentMessage = copy.currentMessage;
        this.error = copy.error;
    }

    // Because of the previous copy constructor, the default constructor must be explicit.
    public ChatState() {
    }

    public GroupChat getCurrentChat() {
        return currentChat;
    }

    public void setCurrentChat(GroupChat currentChat) {
        this.currentChat = currentChat;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public String getCurrentMessage() {
        return currentMessage;
    }

    public void setCurrentMessage(String currentMessage) {
        this.currentMessage = currentMessage;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}