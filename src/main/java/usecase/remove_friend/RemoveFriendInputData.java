package usecase.remove_friend;

/**
 * Input data for the Remove Friend use case.
 */
public class RemoveFriendInputData {
    private final String removedId;

    public RemoveFriendInputData(String removedUsername) {
        this.removedId = removedUsername;
    }

    /**
     * Gets the inputted ID of the user being removed.
     *
     * @return the inputted ID of the user being removed.
     */
    public String getRemovedId() {
        return removedId;
    }
}
