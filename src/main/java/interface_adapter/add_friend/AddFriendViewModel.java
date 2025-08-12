package interface_adapter.add_friend;

import interface_adapter.ViewModel;

/**
 * The ViewModel for the AddFriend View.
 */
public class AddFriendViewModel extends ViewModel<AddFriendState> {

    public static final String TITLE = "Adding Friend View";
    public static final String YOUR_USERNAME = "Your username";
    public static final String FRIEND_USERNAME = "Friend's username";
    public static final String FRIEND_ID = "Friend ID";
    public static final String ADD_BUTTON = "Add Friend";
    public static final String CANCEL_BUTTON = "Cancel";

    public AddFriendViewModel() {
        super("add friend");
        setState(new AddFriendState());
    }

    /**
     * Description.
     * @param errorMessage error message
     */
    public void setErrorMessage(String errorMessage) {
        final AddFriendState currentState = getState();
        final AddFriendState newState = new AddFriendState(currentState);
        newState.setErrorMessage(errorMessage);
        setState(newState);

    }
}
