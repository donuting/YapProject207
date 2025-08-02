package use_case.remove_friend;

/**
 * Input data for the Remove Friend use case.
 */
public class RemoveFriendInputData {
    private final String removedUsername;

    public RemoveFriendInputData(String removedUsername) {
        this.removedUsername = removedUsername;
    }

    public String getRemovedUsername() {
        return removedUsername;
    }
}
