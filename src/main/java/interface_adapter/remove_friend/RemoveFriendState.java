package interface_adapter.remove_friend;

/**
 * The state for the AddFriend View Model.
 */
public class RemoveFriendState {
    private String removedUsername;
    private String removeFriendError;

    public String getRemoveFriendError() {
        return removeFriendError;
    }

    public void setRemoveFriendError(String removeFriendError) {
        this.removeFriendError = removeFriendError;
    }

    public String getRemovedUsername() {
        return removedUsername;
    }

    public void setRemovedUsername(String removedUsername) {
        this.removedUsername = removedUsername;
    }
}
