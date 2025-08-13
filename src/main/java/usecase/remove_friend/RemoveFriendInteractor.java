package usecase.remove_friend;

import entity.User;

public class RemoveFriendInteractor implements RemoveFriendInputBoundary {
    private final RemoveFriendDataAccessInterface removeFriendDataAccessInterface;
    private final RemoveFriendOutputBoundary presenter;

    public RemoveFriendInteractor(RemoveFriendDataAccessInterface removeFriendDataAccessInterface,
                                  RemoveFriendOutputBoundary presenter) {
        this.removeFriendDataAccessInterface = removeFriendDataAccessInterface;
        this.presenter = presenter;
    }

    /**
     * Execute the Remove Friend use case.
     * @param inputData The input data for this use case.
     */
    @Override
    public void execute(RemoveFriendInputData inputData) {
        User currentUser = removeFriendDataAccessInterface.getCurrentUser();
        String removedId = inputData.getRemovedId();
        String removedUsername = removeFriendDataAccessInterface.getUsernameFromId(removedId);
        String currentUsername = currentUser.getName();

        // checks both users exist
        // should never happen
        if (!removeFriendDataAccessInterface.existsByName(currentUsername)) {
            presenter.removeFriendPrepareFailView("Current user does not exist in database");
        }

        // check that friend user exists
        else if (!removeFriendDataAccessInterface.existsByName(removedUsername)) {
            presenter.removeFriendPrepareFailView("User does not exist");
        }

        else if (!currentUser.getFriendIDs().contains(removedId)) {
            presenter.removeFriendPrepareFailView("You cannot remove someone who is not your friend");
        }

        else if (!currentUser.removeFriend(removedId)) {
            presenter.removeFriendPrepareFailView("Failed to remove friend");
        }
        // removes the friend in the database
        else if (!removeFriendDataAccessInterface.removeFriend(currentUser, removedUsername, removedId)) {
            presenter.removeFriendPrepareFailView("Failed to remove friend");
        }
        else {
            RemoveFriendOutputData outputData = new RemoveFriendOutputData(
                    currentUsername, removedUsername, removedId, true);
            presenter.removeFriendPrepareSuccessView(outputData);
        }
    }
}
