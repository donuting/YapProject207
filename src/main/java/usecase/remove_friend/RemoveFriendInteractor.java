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
//        if (!removeFriendDataAccessInterface.existsByName(currentUsername)) {
//            presenter.removeFriendPrepareFailView("Current user does not exist in database");
//            return;
//        }
//        // check that friend user exists
//        if (!removeFriendDataAccessInterface.existsByName(removedUsername)) {
//            presenter.removeFriendPrepareFailView("User does not exist");
//            return;
//        }
//
//        if (!currentUser.getFriendIDs().contains(removedId)) {
//            presenter.removeFriendPrepareFailView("You cannot remove someone who is not your friend");
//            return;
//        }
//        else {
//            // Remove friend for the current user in memory
//            if (!currentUser.removeFriend(removedId)) {
//                presenter.removeFriendPrepareFailView("Failed to remove friend");
//                return;
//            }
//
//            // removes the friend in the database
//            if (!removeFriendDataAccessInterface.removeFriend(currentUser, removedUsername, removedId)) {
//                presenter.removeFriendPrepareFailView("Failed to remove friend");
//                return;
//            }

        currentUser.removeFriend(removedId);
        removeFriendDataAccessInterface.removeFriend(currentUser, removedUsername, removedId);

        RemoveFriendOutputData outputData = new RemoveFriendOutputData(currentUsername, removedUsername, removedId, true);
        presenter.removeFriendPrepareSuccessView(outputData);
        }
}
