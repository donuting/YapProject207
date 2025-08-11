package interface_adapter.add_friend;

import interface_adapter.ViewManagerModel;
import use_case.add_friend.AddFriendInputBoundary;
import use_case.add_friend.AddFriendInputData;

/**
 * The Controller for the Add Friend Use Case.
 */
public class AddFriendController {

    private final AddFriendInputBoundary addFriendUseCaseInteractor;

    public AddFriendController(//ViewManagerModel viewManagerModel,
                               AddFriendInputBoundary addFriendUseCaseInteractor) {
        this.addFriendUseCaseInteractor = addFriendUseCaseInteractor;
    }

    /**
     * Switches back to Main Menu.
     */
    public void switchToMainMenu() {
        addFriendUseCaseInteractor.switchToMainMenuView();
    }

    /**
     * Executes the AddFriend Use Case
     * @param friendUsername the username of the person you wish to add
     * @param friendID the ID of the person you wish to add
     */
    public void execute(String friendUsername, String friendID) {
        final AddFriendInputData addFriendInputData = new AddFriendInputData(friendUsername, friendID);

        addFriendUseCaseInteractor.execute(addFriendInputData);

    }
}
