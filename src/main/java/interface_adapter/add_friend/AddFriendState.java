package interface_adapter.add_friend;

/**
 * The state for the AddFriend View Model.
 */
public class AddFriendState {
    private String username = "";
    private String addFriendError;
    private String userID;

    public String getUsername() {
        return username;
    }

    public String getAddFriendError() {
        return addFriendError;
    }

    public String getUserID() {
        return userID;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAddFriendError(String addFriendError) {
        this.addFriendError = addFriendError;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
