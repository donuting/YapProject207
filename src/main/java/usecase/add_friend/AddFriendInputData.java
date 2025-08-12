package usecase.add_friend;

/**
 * The Input Data for the Add Friend Use Case.
 */
public class AddFriendInputData {

    private final String friendUsername;
    private final String friendID;

    public AddFriendInputData(String friendUsername, String friendID) {
        this.friendUsername = friendUsername;
        this.friendID = friendID;
    }

    /**
     * Gets the inputted username.
     *
     * @return the inputted username.
     */
    public String getFriendUsername() {
        return friendUsername;
    }

    /**
     * Gets the inputted user ID.
     *
     * @return the inputted user ID.
     */
    public String getFriendID() {
        return friendID;
    }
}
