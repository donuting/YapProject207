package use_case.remove_friend;

import entity.User;

public class RemoveFriendInteractor implements RemoveFriendInputBoundary{
    private final RemoveFriendDataAccessInterface removeFriendDataAccessInterface;
    private final RemoveFriendOutputBoundary presenter;

    public RemoveFriendInteractor(RemoveFriendDataAccessInterface removeFriendDataAccessInterface, RemoveFriendOutputBoundary presenter) {
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
        User removedUser = removeFriendDataAccessInterface.get(removedUsername);


        // checks both users exist
        // should never happen
//        if (!removeFriendDataAccessInterface.existsByName(currentUsername)) {
//            RemoveFriendOutputData outputData = new RemoveFriendOutputData(currentUsername, removedUsername, removedId, false);
//            presenter.removeFriendPrepareFailView("Current user does not exist in database", outputData);
//        }
        // check that friend user exists
//        if (!removeFriendDataAccessInterface.existsByName(removedUsername)) {
//            //RemoveFriendOutputData outputData = new RemoveFriendOutputData(currentUsername, removedUsername, removedId, false);
//            presenter.removeFriendPrepareFailView("User does not exist");
//        }

//        if (!currentUser.getFriendIDs().contains(removedId)) {
//            //RemoveFriendOutputData outputData = new RemoveFriendOutputData(currentUsername, removedUsername, removedId, false);
//            presenter.removeFriendPrepareFailView("You cannot remove someone who is not your friend");
//        }
//        else {
            // removes the friend in currentUser
//            if (!currentUser.removeFriend(removedId)) {
//                RemoveFriendOutputData outputData = new RemoveFriendOutputData(currentUsername, removedUsername, removedId, false);
//                presenter.removeFriendPrepareFailView("Failed to remove friend");
//            }
            // remove friendship from both users (from friends list and chats)
        currentUser.removeFriend(removedId);
        removedUser.removeFriend(currentUsername);


            // removes the friend in the database
        removeFriendDataAccessInterface.removeFriend(currentUser, removedUsername, removedId);
        removeFriendDataAccessInterface.removeFriend(removedUser, currentUsername, currentUser.getID());

//            if (!removeFriendDataAccessInterface.removeFriend(currentUser, removedUsername, removedId)) {
//                RemoveFriendOutputData outputData = new RemoveFriendOutputData(currentUsername, removedUsername, removedId, false);
//                presenter.removeFriendPrepareFailView("Failed to remove friend");
//            }

        RemoveFriendOutputData outputData = new RemoveFriendOutputData(currentUsername, removedUsername, removedId, true);
        presenter.removeFriendPrepareSuccessView(outputData);
//        }

    }
}
