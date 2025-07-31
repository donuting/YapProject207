package interface_adapter.add_friend;

import interface_adapter.ViewManagerModel;
import use_case.add_friend.AddFriendInputBoundary;
import use_case.add_friend.AddFriendInputData;

/**
 * The Controller for the Add Friend Use Case.
 */
public class AddFriendController {

    private final AddFriendInputBoundary addFriendUseCaseInteractor;
    private ViewManagerModel viewManagerModel;

    public AddFriendController(ViewManagerModel viewManagerModel, AddFriendInputBoundary addFriendUseCaseInteractor) {
        this.viewManagerModel = viewManagerModel;
        this.addFriendUseCaseInteractor = addFriendUseCaseInteractor;
        // change method of doing this to only use interactor (fix view)
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
     * @param friendName the username of the person you wish to add
     * @param friendID the ID of the person you wish to add
     */
    public void execute(String currentUserID, String friendName, String friendID) {
        final AddFriendInputData addFriendInputData = new AddFriendInputData(currentUserID, friendName, friendID);

        addFriendUseCaseInteractor.execute(addFriendInputData);

    }
}
