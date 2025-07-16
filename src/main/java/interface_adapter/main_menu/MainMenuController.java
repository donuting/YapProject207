package interface_adapter.main_menu;

import interface_adapter.ViewManagerModel;
import interface_adapter.logout.LogoutController;

/**
 * The controller for the Main Menu View.
 */
public class MainMenuController {
    private final ViewManagerModel viewManagerModel;
    private final LogoutController logoutController;

    public MainMenuController(ViewManagerModel viewManagerModel, LogoutController logoutController) {
        this.viewManagerModel = viewManagerModel;
        this.logoutController = logoutController;
    }

    /**
     * Executes the switch to View Chats use case.
     */
    public void switchToViewChats() {
        // Navigate to view chats screen
        viewManagerModel.setState("view chats");
        viewManagerModel.firePropertyChanged();
    }

    /**
     * Executes the switch to Profile Settings use case.
     */
    public void switchToProfileSettings() {
        // Navigate to profile settings screen
        viewManagerModel.setState("profile settings");
        viewManagerModel.firePropertyChanged();
    }

    /**
     * Executes the switch to Add Friends use case.
     */
    public void switchToAddFriends() {
        // Navigate to add friends screen
        viewManagerModel.setState("add friends");
        viewManagerModel.firePropertyChanged();
    }

}