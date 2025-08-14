package interfaceadapter.view_friends;

import interfaceadapter.ViewModel;

/**
 * The View Model for the View Friends View.
 */
public class ViewFriendsViewModel extends ViewModel<ViewFriendsState> {

    public ViewFriendsViewModel() {
        super("view friends");
        setState(new ViewFriendsState());
    }
}
