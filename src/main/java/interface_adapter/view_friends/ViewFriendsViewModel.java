package interface_adapter.view_friends;

import interface_adapter.ViewModel;

/**
 * The View Model for the View Friends View.
 */
public class ViewFriendsViewModel extends ViewModel<ViewFriendsState> {

    public ViewFriendsViewModel() {
        super("view friends");
        setState(new ViewFriendsState());
    }
}
