package interface_adapter.main_menu;

import interface_adapter.ViewManagerModel;
import interface_adapter.logout.LogoutController;
import interface_adapter.profile_and_settings.PandSviewModel;
import interface_adapter.view_chats.ViewChatsViewModel;
import interface_adapter.view_friends.ViewFriendsState;
import interface_adapter.view_friends.ViewFriendsViewModel;

/**
 * The controller for the Main Menu View.
 */
public class MainMenuController {
    private final ViewManagerModel viewManagerModel;
    private final LogoutController logoutController;
    private final ViewChatsViewModel viewChatsViewModel;
    private final MainMenuViewModel mainMenuViewModel;
    private final ViewFriendsViewModel viewFriendsViewModel;
    private final PandSviewModel pandsViewModel;

    public MainMenuController(ViewManagerModel viewManagerModel, LogoutController logoutController,
                              ViewChatsViewModel viewChatsViewModel, MainMenuViewModel mainMenuViewModel,
                              ViewFriendsViewModel viewFriendsViewModel, PandSviewModel pandsViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.logoutController = logoutController;
        this.viewChatsViewModel = viewChatsViewModel;
        this.mainMenuViewModel = mainMenuViewModel;
        this.viewFriendsViewModel = viewFriendsViewModel;
        this.pandsViewModel = pandsViewModel;
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
        final String currentUsername = mainMenuViewModel.getState().getUsername();
        final String password = mainMenuViewModel.getState().getPassword();
        final String uiD = mainMenuViewModel.getState().getUiD();
        final String Bio = mainMenuViewModel.getState().getBio();
        final String DOB = mainMenuViewModel.getState().getDOB();
        pandsViewModel.getState().setUsername(currentUsername);
        pandsViewModel.getState().setChangePasswordText(password);
        pandsViewModel.getState().setUserId(uiD);
        pandsViewModel.getState().setAddBioText(Bio);
        pandsViewModel.getState().setAddDobText(DOB);
        pandsViewModel.firePropertyChanged("Profile And Settings");
        // Navigate to profile settings screen

        viewManagerModel.setState("Profile And Settings");
        viewManagerModel.firePropertyChanged();
    }

    /**
     * Executes the switch to Add Friends use case.
     */
    public void switchToViewFriends() {
        // Update friends in state object
        final ViewFriendsState viewFriendsState = viewFriendsViewModel.getState();
        viewFriendsState.setNeedsFriendInfo(true);
        viewFriendsViewModel.setState(viewFriendsState);
        viewFriendsViewModel.firePropertyChanged();

        // Navigate to add friends screen
        viewManagerModel.setState("view friends");
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
