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
        User currentUser = userDataAccessObject.get(currentUsername);
        User friendUser = userDataAccessObject.get(friendUsername);


        // check that nothing is empty
        if (currentUsername == null || friendUsername == null || friendID == null) {
            presenter.prepareFailView("Please fill out all fields"
                    //, new AddFriendOutputData(friendUsername, true, null)
            );
            return;
        }

        if (currentUsername.trim().isEmpty() || friendUsername.trim().isEmpty() || friendID.trim().isEmpty()) {
            presenter.prepareFailView("Please fill out all fields"
                    //, new AddFriendOutputData(currentUsername, true, null)
            );
            return;
        }

        // check if current user spelled own username correctly, not sure if this worked (maybe DAO issue)
        // says currentUser is null when tested
        if (!currentUsername.equals(currentUser.getName())) {
            presenter.prepareFailView("Your account name is incorrect"
                    // , new AddFriendOutputData(friendUsername, true, null)
            );
            return;
        }

        if (!userDataAccessObject.existsByName(friendUsername)) {
            presenter.prepareFailView("User " + friendUsername + " does not exist"
                    // , new AddFriendOutputData(friendUsername, true, null)
            );
            return;
        }

        // check if already friends
        if (userDataAccessObject.alreadyFriend(currentUsername, friendUsername)) {
            presenter.prepareFailView("You are already friends with " + friendUsername
                    // , new AddFriendOutputData(friendUsername, true, null)
            );
            return;
        }

        // checking that user is not adding self using IDS, leave for near the end
        if (friendID.equals(currentUser.getID())) {
            presenter.prepareFailView("You cannot add yourself as a friend"
                    //, new AddFriendOutputData(friendUsername, true, null)
            );
            return;
        }

        // successful
        else {
            // adds from current user side
            currentUser.addFriend(friendID);
            // create the chat for this user, also do I need to add these to the database???

            // adds from friend's side
            friendUser.addFriend(currentUser.getID());
            // create the chat for friend
            AddFriendOutputData outputData = new AddFriendOutputData(friendUsername,
                    true, friendUsername + " has been added!");
            presenter.prepareSuccessView(outputData);



        }
    }

    @Override
    public void switchToMainMenuView() {
        presenter.switchToMainMenuView();
    }
}
