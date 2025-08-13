package interfaceadapter.view_chats;

/**
 * The state for the View chats View Model.
 */
public class ViewChatsState {
    private String username = "";
    private String error = "";

    public ViewChatsState(ViewChatsState copy) {
        username = copy.username;
        error = copy.error;
    }

    // Because of the previous copy constructor, the default constructor must be explicit.
    public ViewChatsState() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
