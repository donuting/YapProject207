package interface_adapter.view_chats;

import interface_adapter.ViewManagerModel;
import interface_adapter.add_chat.AddChatViewModel;
import interface_adapter.self_chat.SelfChatViewModel;

/**
 * The controller for the View Chats View.
 */
public class ViewChatsController {
    private final ViewManagerModel viewManagerModel;
    private final AddChatViewModel addChatViewModel;
    private final ViewChatsViewModel viewChatsViewModel;
    private final SelfChatViewModel selfChatViewModel;

    public ViewChatsController(ViewManagerModel viewManagerModel, AddChatViewModel addChatViewModel, ViewChatsViewModel viewChatsViewModel, SelfChatViewModel selfChatViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.addChatViewModel = addChatViewModel;
        this.viewChatsViewModel = viewChatsViewModel;
        this.selfChatViewModel = selfChatViewModel;
    }

    /**
     * Executes the open self chat use case.
     */
    public void openSelfChat() {
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
}