package use_case.remove_friend;

/**
 * Output data for the Remove Friend use case.
 */
public class RemoveFriendOutputData {
    private final String currentUsername;
    private final String removedUsername;
    private final String removedId;
    private final boolean success;

    public RemoveFriendOutputData(String currentUsername, String removedUsername, String removedId, boolean success) {
        this.currentUsername = currentUsername;
        this.removedUsername = removedUsername;
        this.removedId = removedId;
        this.success = success;
    }

    public String getRemovedUsername() {
        return removedUsername;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getCurrentUsername() {
        return currentUsername;
    }

    public String getRemovedId() {
        return removedId;
    }
}
