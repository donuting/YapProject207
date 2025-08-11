package interface_adapter.chat;

import entity.GroupChat;
import entity.Message;
import java.util.List;
import java.util.ArrayList;

/**
 * The State information representing the logged-in user.
 */
public class ChatState {
    private String currentChannelUrl = "";
    private String chatName = "";
    private List<Message> messages = new ArrayList<>();
    private List<Message> messagesSentByUser = new ArrayList<>();
    private List<String> usernames = new ArrayList<>();
    private String currentMessage = "";
    private String error = null;
    private boolean needsUpdate = false;
    private boolean needsClearChat = false;
    private boolean isGroupChat = false;

    public ChatState(ChatState copy) {
        this.currentChannelUrl = copy.currentChannelUrl;
        this.chatName = copy.chatName;
        this.messages = new ArrayList<>(copy.messages);
        this.messagesSentByUser = new ArrayList<>(copy.messagesSentByUser);
        this.usernames = new ArrayList<>(copy.usernames);
        this.currentMessage = copy.currentMessage;
        this.error = copy.error;
        this.needsUpdate = copy.needsUpdate;
        this.needsClearChat = copy.needsClearChat;
        this.isGroupChat = copy.isGroupChat;
    }

    // Because of the previous copy constructor, the default constructor must be explicit.
    public ChatState() {
    }

    public String getCurrentChannelUrl() {
        return currentChannelUrl;
    }

    public void setCurrentChannelUrl(String currentChannelUrl) {
        this.currentChannelUrl = currentChannelUrl;
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

    public List<Message> getMessagesSentByUser() {
        return messagesSentByUser;
    }

    public void setMessagesSentByUser(List<Message> messagesSentByUser) {
        this.messagesSentByUser = messagesSentByUser;
    }

    public List<String> getUsernames() {
        return usernames;
    }

    public void setUsernames(List<String> usernames) {
        this.usernames = usernames;
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

    public boolean getNeedsUpdate() {
        return needsUpdate;
    }

    public void setNeedsUpdate(boolean needsUpdate) {
        this.needsUpdate = needsUpdate;
    }

    public boolean getNeedsClearChat() {
        return needsClearChat;
    }

    public void setNeedsClearChat(boolean needsClearChat) {
        this.needsClearChat = needsClearChat;
    }

    public boolean getIsGroupChat() {
        return isGroupChat;
    }

    public void setGroupChat(boolean groupChat) {
        this.isGroupChat = groupChat;
    }
}