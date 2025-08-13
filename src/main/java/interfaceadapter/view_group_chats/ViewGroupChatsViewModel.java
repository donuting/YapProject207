package interfaceadapter.view_group_chats;

import interfaceadapter.ViewModel;

/**
 * The View Model for the View Group Chats View.
 */
public class ViewGroupChatsViewModel extends ViewModel<ViewGroupChatsState> {

    public ViewGroupChatsViewModel() {
        super("view group chats");
        setState(new ViewGroupChatsState());
    }
}
