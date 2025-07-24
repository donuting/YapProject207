package interface_adapter.main_menu;

import interface_adapter.ViewManagerModel;
import interface_adapter.add_friend.AddFriendController;
import interface_adapter.add_friend.AddFriendViewModel;
import interface_adapter.logout.LogoutController;
import interface_adapter.view_chats.ViewChatsViewModel;

/**
 * The controller for the Main Menu View.
 */
public class MainMenuController {
    private final ViewManagerModel viewManagerModel;
    private final LogoutController logoutController;
    private final ViewChatsViewModel viewChatsViewModel;
    private final MainMenuViewModel mainMenuViewModel;
    private final AddFriendViewModel addFriendViewModel;

    public MainMenuController(ViewManagerModel viewManagerModel, LogoutController logoutController,
                              ViewChatsViewModel viewChatsViewModel, MainMenuViewModel mainMenuViewModel,
                              AddFriendViewModel addFriendViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.logoutController = logoutController;
        this.viewChatsViewModel = viewChatsViewModel;
        this.mainMenuViewModel = mainMenuViewModel;
        this.addFriendViewModel = addFriendViewModel;
    }

    /**
     * Executes the switch to View Chats use case.
     */
    public void switchToViewChats() {
        // Pass current username to view chats view
        final String currentUsername = mainMenuViewModel.getState().getUsername();
        viewChatsViewModel.getState().setUsername(currentUsername);
        viewChatsViewModel.firePropertyChanged();

        // Navigate to view chats screen
        viewManagerModel.setState("view chats");
        viewManagerModel.firePropertyChanged();
    }

    /**
     * Executes the switch to Profile Settings use case.
     */
    public void switchToProfileSettings() {
        // Navigate to profile settings screen
    }

    /**
     * Executes the switch to Add Friends use case.
     */
    public void switchToAddFriends() {
        // Navigate to add friends screen
        viewManagerModel.setState("add friend");
        viewManagerModel.firePropertyChanged();
    }

    /**
     * Executes the switch to View Chats use case.
     */
    public void switchToLogout() {
        // Navigate to sign up screen
        viewManagerModel.setState("sign up");
        viewManagerModel.firePropertyChanged();
    }

}