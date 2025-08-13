package usecase.remove_friend;

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

    /**
     * Gets the removed user's username outputted by the interactor.
     * @return the removed user's username
     */
    public String getRemovedUsername() {
        return removedUsername;
    }

    /**
     * Returns whether the use case succeeded.
     * @return true if successful
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Gets the current user's username outputted by the interactor.
     * @return the current user's username
     */
    public String getCurrentUsername() {
        return currentUsername;
    }

    /**
     * Gets the removed user's ID outputted by the interactor.
     * @return the removed user's ID
     */
    public String getRemovedId() {
        return removedId;
    }
}
