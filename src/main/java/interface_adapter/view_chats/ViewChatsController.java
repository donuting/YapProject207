package interface_adapter.view_chats;

import interface_adapter.ViewManagerModel;

/**
 * The controller for the View Chats View.
 */
public class ViewChatsController {
    private final ViewManagerModel viewManagerModel;

    public ViewChatsController(ViewManagerModel viewManagerModel) {
        this.viewManagerModel = viewManagerModel;
    }

    /**
     * Executes the switch to self chat use case.
     */
    public void switchToSelfChat() {
        // Navigate to self chat screen
        viewManagerModel.setState("self chat");
        viewManagerModel.firePropertyChanged();
    }
    /**
     * Executes the switch to add chat use case.
     */
    public void switchToAddChat() {
        // Navigate to add chat screen
        viewManagerModel.setState("add chat");
        viewManagerModel.firePropertyChanged();
    }

    /**
     * Executes the switch to back to menu use case.
     */
    public void switchToBackToMenu() {
        // Navigate to menu screen
        viewManagerModel.setState("main menu");
        viewManagerModel.firePropertyChanged();
    }
}
