package interface_adapter.add_chat;

/**
 * The state for the Add Chat View Model.
 */
public class AddChatState {
    private String chatName = "";
    private String ID = "";
    private String chatNameError = "";
    private boolean success = false;

    public AddChatState(AddChatState copy) {
        chatName = copy.chatName;
        ID = copy.ID;
        chatNameError = copy.chatNameError;
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

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}