package usecase.add_friend;

/**
 * Output Data for the Add Friend Use Case.
 */
public class AddFriendOutputData {
    private final String friendUsername;
    private final boolean isSuccess;
    private final String successMessage;

    public AddFriendOutputData(String friendUsername,
                               boolean isSuccess,
                               String successMessage) {
        this.friendUsername = friendUsername;
        this.successMessage = successMessage;
        this.isSuccess = isSuccess;
    }

    /**
     * Gets the friend's username outputted by the interactor.
     * @return the friend's username.
     */
    public String getFriendUsername() {
        return friendUsername;
    }

    /**
     * Gets the success message outputted by the interactor.
     * @return the success message.
     */
    public String getSuccessMessage() {
        return successMessage;
    }

    /**
     * Returns whether the use case succeeded.
     * @return true if successful
     */
    public boolean isSuccess() {
        return isSuccess;
    }
}
