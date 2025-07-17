package interface_adapter.add_friend;

import interface_adapter.ViewModel;

/**
 * The ViewModel for the AddFriend View.
 */
public class AddFriendViewModel extends ViewModel<AddFriendState> {

    public AddFriendViewModel() {
        super("add friend");
        setState(new AddFriendState());
    }

}
