package usecase.remove_friend;

/**
 * Input data for the Remove Friend use case.
 */
public class RemoveFriendInputData {
    private final String removedId;

    public RemoveFriendInputData(String removedUsername) {
        this.removedId = removedUsername;
    }

    public String getRemovedId() {
        return removedId;
    }
}
