package interface_adapter.remove_friend;

import interface_adapter.ViewModel;
import interface_adapter.change_password.LoggedInState;

/**
 * The View Model for the Logged In View.
 */
public class RemoveFriendViewModel extends ViewModel<RemoveFriendState> {

    public RemoveFriendViewModel() {
        super("remove friend");
        setState(new RemoveFriendState());
    }
}
