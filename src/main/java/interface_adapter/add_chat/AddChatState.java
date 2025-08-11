package interface_adapter.add_chat;

/**
 * The state for the Add Chat View Model.
 */
public class AddChatState {
    private String chatName = "";
    private String username = "";  // NEW: username to add to chat
    private String ID = "";
    private String chatNameError = "";
    private String usernameError = "";  // NEW: error for username validation
    private boolean success = false;

    public AddChatState(AddChatState copy) {
        chatName = copy.chatName;
        username = copy.username;  // NEW
        ID = copy.ID;
        chatNameError = copy.chatNameError;
        usernameError = copy.usernameError;  // NEW
        success = copy.success;
    }

    // Because of the previous copy constructor, the default constructor must be explicit.
    public AddChatState() {
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    // NEW: Username getter/setter
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getChatNameError() {
        return chatNameError;
    }

    public void setChatNameError(String chatNameError) {
        this.chatNameError = chatNameError;
    }

    // NEW: Username error getter/setter
    public String getUsernameError() {
        return usernameError;
    }

    public void setUsernameError(String usernameError) {
        this.usernameError = usernameError;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}