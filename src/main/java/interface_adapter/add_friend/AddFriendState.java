package interface_adapter.add_friend;

/**
 * The state for the AddFriend View Model.
 */
public class AddFriendState {


    private String currentUsername = "";
    private String errorMessage;
    private String successMessage;
    private String friendName;
    private String friendUserID;

    public AddFriendState () {

    }

    public  AddFriendState(AddFriendState copy) {
        this.currentUsername = copy.currentUsername;
        this.errorMessage = copy.errorMessage;
        this.successMessage = copy.successMessage;
        this.friendName = copy.friendName;
        this.friendUserID = copy.friendUserID;

    }

    public String getCurrentUsername() {
        return currentUsername;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public String getFriendUsername() {
        return friendName;
    }

    public void setFriendUsername(String friendName) {
        this.friendName = friendName;
    }

    public void setCurrentUsername(String currentUsername) {
        this.currentUsername = currentUsername;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }

    public String getFriendUserID() {
        return friendUserID;
    }

    public void setFriendUserID(String friendUserID) {
        this.friendUserID = friendUserID;
    }
}
