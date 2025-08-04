package use_case.add_friend;

import data_access.InMemoryUserDataAccessObject;
import entity.User;
import entity.UserFactory;

/**
 * The AddFriend Interactor.
 */
public class AddFriendInteractor implements AddFriendInputBoundary {
    private final AddFriendUserDataAccessInterface userDataAccessObject;
    private final AddFriendOutputBoundary presenter;


    public AddFriendInteractor(AddFriendUserDataAccessInterface userDataAccessInterface,
                               AddFriendOutputBoundary addFriendOutputBoundary) {

        this.userDataAccessObject = userDataAccessInterface;
        this.presenter = addFriendOutputBoundary;

    }

    @Override
    public void execute(AddFriendInputData addFriendInputData) {
        final String currentUsername = addFriendInputData.getCurrentUsername();
        final String friendUsername = addFriendInputData.getFriendUsername();
        final String friendID = addFriendInputData.getFriendID();


        // check that nothing is empty
        if (currentUsername == null || friendUsername == null || friendID == null) {
            presenter.prepareFailView("Please fill out all fields",
                    new AddFriendOutputData(friendUsername, true, null));
            return;
        }

        if (currentUsername.trim().isEmpty() || friendUsername.trim().isEmpty() || friendID.trim().isEmpty()) {
            presenter.prepareFailView("Please fill out all fields", new AddFriendOutputData(currentUsername,
                    true, null));
            return;
        }

        if (friendID.equals(userDataAccessObject.get(currentUsername).getID())) {
            presenter.prepareFailView("You cannot add yourself as a friend",
                    new AddFriendOutputData(friendUsername, true, null));
            return;
        }

        try {
            //check if user exists
            if (!userDataAccessObject.existsByName(friendUsername)) {
                presenter.prepareFailView("User " + friendUsername + " does not exist",
                        new AddFriendOutputData(friendUsername, true, null));
                return;
            }

            // check if already friends
            if (userDataAccessObject.alreadyFriend(currentUsername, friendUsername)) {
                presenter.prepareFailView("You are already friends with " + friendUsername,
                        new AddFriendOutputData(friendUsername, true, null));
                return;
            }

            // attempt to add friend
            boolean success = userDataAccessObject.addFriend(currentUsername, friendUsername);

            if (success) {
                AddFriendOutputData outputData = new AddFriendOutputData(friendUsername, false,
                        "Successfully added " + friendUsername);
                presenter.prepareSuccessView(outputData);
            } else {
                presenter.prepareFailView("Failed to add friend", new AddFriendOutputData(friendUsername,
                        true, null));
            }
        } catch (Exception e) {
            presenter.prepareFailView("Error occurred", new AddFriendOutputData(friendUsername,
                    true, null));
        }
    }
}
