package use_case.add_friend;

import entity.User;
import entity.UserFactory;

/**
 * The AddFriend Interactor.
 */
public class AddFriendInteractor implements AddFriendInputBoundary {
    private final AddFriendUserDataAccessInterface userDataAccessObject;
    private final AddFriendOutputBoundary presenter;
    // private final UserFactory userFactory;

    public AddFriendInteractor(AddFriendUserDataAccessInterface userDataAccessInterface,
                               AddFriendOutputBoundary addFriendOutputBoundary) {
                               // UserFactory userFactory) {
        this.userDataAccessObject = userDataAccessInterface;
        this.presenter = addFriendOutputBoundary;
        // this.userFactory = userFactory;

    }

    @Override
    public void execute(AddFriendInputData addFriendInputData) {
        final String currentUserID = addFriendInputData.getCurrentUserID();
        final String friendUsername = addFriendInputData.getFriendUsername();
        final String friendID = addFriendInputData.getFriendID();

        boolean success  = false;

        // getting both users, first one not needed?
        User currentUser = userDataAccessObject.getUser(currentUserID);
        if (currentUser == null) {
            presenter.prepareFailView("You are not found");
            return;
        }

        User friend = userDataAccessObject.getFriendUser(friendUsername, friendID);
        if (friend == null) {
            presenter.prepareFailView("User not found");
            return;
        }

        // adding self is prohibited
        if (currentUserID.equals(friendID)) {
            presenter.prepareFailView("You cannot add yourself");
            return;
        }

        // Username or ID cannot be empty
        if (friendUsername == null || friendUsername.trim().isEmpty()) {
            presenter.prepareFailView("Username cannot be empty");
            return;
        }

        if (friendID == null || friendID.trim().isEmpty()) {
            presenter.prepareFailView("ID cannot be empty");
            return;
        }

        // Check if already friends
        if (userDataAccessObject.alreadyFriend(currentUser, friend)) {
            presenter.prepareFailView("User is already added");
            return;
        }
        else {
            AddFriendOutputData outputData = new AddFriendOutputData(currentUserID, friendUsername, friendID);
            presenter.prepareSuccessView(outputData);
        }

    }

}
