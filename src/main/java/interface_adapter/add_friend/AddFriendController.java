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

    public AddFriendController(ViewManagerModel viewManagerModel,
                               AddFriendInputBoundary addFriendUseCaseInteractor) {
        this.viewManagerModel = viewManagerModel;
        this.addFriendUseCaseInteractor = addFriendUseCaseInteractor;
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
     * @param currentUsername username of person that is adding the other person
     * @param friendUsername the username of the person you wish to add
     * @param friendID the ID of the person you wish to add
     */
    public void execute(String currentUsername, String friendUsername, String friendID) {
        final AddFriendInputData addFriendInputData = new AddFriendInputData(currentUsername, friendUsername, friendID);

        addFriendUseCaseInteractor.execute(addFriendInputData);

    }
}
