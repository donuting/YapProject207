package use_case.add_friend;

/**
 * The Input Data for the Add Friend Use Case.
 */
public class AddFriendInputData {

    private final String currentUserID;
    private final String friendUsername;
    private final String friendID;

    public AddFriendInputData(String currentUserID, String friendUsername, String friendID) {
        this.currentUserID = currentUserID;
        this.friendUsername = friendUsername;
        this.friendID = friendID;
    }

    // only did ID for easier check later on
    public String getCurrentUserID() {
        return currentUserID;
    }

    public String getFriendUsername() {
        return friendUsername;
    }

    public String getFriendID() {
        return friendID;
    }
}
