package interface_adapter.view_group_chats;

import interface_adapter.ViewModel;

/**
 * The View Model for the View Group Chats View.
 */
public class ViewGroupChatsViewModel extends ViewModel<ViewGroupChatsState> {

    public ViewGroupChatsViewModel() {
        super("view group chats");
        setState(new ViewGroupChatsState());
    }
}
