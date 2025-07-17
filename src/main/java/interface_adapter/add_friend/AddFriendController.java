package interface_adapter.add_friend;

import interface_adapter.ViewManagerModel;

/**
 * The Controller for the Add Friend View.
 */
public class AddFriendController {
    private ViewManagerModel viewManagerModel;

    public AddFriendController(ViewManagerModel viewManagerModel) {
        this.viewManagerModel = viewManagerModel;
    }

    /**
     * Switches back to MainMenu.
     */
    public void switchToMainMenu() {
        viewManagerModel.setState("main menu");
        viewManagerModel.firePropertyChanged();
    }

    /**
     * Executes the AddFriend Use Case
     */
    public void addFriend(String friendName, String friendID) {
        // TODO: logic to be implemented
    }
}
