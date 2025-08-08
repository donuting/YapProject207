package interface_adapter.view_chats;

import interface_adapter.ViewManagerModel;
import interface_adapter.add_chat.AddChatViewModel;
import interface_adapter.self_chat.SelfChatViewModel;
import interface_adapter.view_group_chats.ViewGroupChatsState;
import interface_adapter.view_group_chats.ViewGroupChatsViewModel;

/**
 * The controller for the View Chats View.
 */
public class ViewChatsController {
    private final ViewManagerModel viewManagerModel;
    private final AddChatViewModel addChatViewModel;
    private final ViewChatsViewModel viewChatsViewModel;
    private final SelfChatViewModel selfChatViewModel;
    private final ViewGroupChatsViewModel viewGroupChatsViewModel;

    public ViewChatsController(ViewManagerModel viewManagerModel, AddChatViewModel addChatViewModel, ViewChatsViewModel viewChatsViewModel, SelfChatViewModel selfChatViewModel, ViewGroupChatsViewModel viewGroupChatsViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.addChatViewModel = addChatViewModel;
        this.viewChatsViewModel = viewChatsViewModel;
        this.selfChatViewModel = selfChatViewModel;
        this.viewGroupChatsViewModel = viewGroupChatsViewModel;
    }

    /**
     * Executes the open self chat use case.
     */
    public void openSelfChat() {
        // Update the self chat
        selfChatViewModel.firePropertyChanged();

        // Navigate to self chat screen
        viewManagerModel.setState("self chat");
        viewManagerModel.firePropertyChanged();
    }

    /**
     * Executes the open add chat use case.
     */
    public void openAddChat() {
        // Pass current username to add chat view
        final String ID = viewChatsViewModel.getState().getUsername();
        addChatViewModel.getState().setID(ID);
        addChatViewModel.firePropertyChanged();

        // Navigate to add chat screen
        viewManagerModel.setState("add chat");
        viewManagerModel.firePropertyChanged();
    }

    /**
     * Executes the back to menu use case.
     */
    public void backToMenu() {
        // Navigate back to main menu
        viewManagerModel.setState("main menu");
        viewManagerModel.firePropertyChanged();
    }

    public void openGroupChats() {
        // Update the username
        ViewGroupChatsState viewGroupChatsState = viewGroupChatsViewModel.getState();
        viewGroupChatsState.setUsername(viewChatsViewModel.getState().getUsername());

        // Updates the list of group chats
        viewGroupChatsState.setNeedsGroupChatInfo(true);
        viewGroupChatsViewModel.setState(viewGroupChatsState);
        viewGroupChatsViewModel.firePropertyChanged();

        // Navigate to the view group chats screen
        viewManagerModel.setState("view group chats");
        viewManagerModel.firePropertyChanged();
    }
}