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
     *
     * @param inputData The input data for this use case.
     */
    @Override
    public void execute(RemoveFriendInputData inputData) {
        User currentUser = removeFriendDataAccessInterface.getCurrentUser();
        User removedUser = removeFriendDataAccessInterface.get(inputData.getRemovedUsername());
        String currentUsername = currentUser.getName();
        String removedUsername = removedUser.getName();

        // checks both users exist
        if (!removeFriendDataAccessInterface.existsByName(currentUsername)) {
            RemoveFriendOutputData outputData = new RemoveFriendOutputData(currentUsername, removedUsername, false);
            presenter.prepareFailView("Current user does not exist in database", outputData);
        }
        if (!removeFriendDataAccessInterface.existsByName(removedUsername)) {
            RemoveFriendOutputData outputData = new RemoveFriendOutputData(currentUsername, removedUsername, false);
            presenter.prepareFailView("Failed to remove friend", outputData);
        }
        else {
            // removes the friend in currentUser
            if (!currentUser.removeFriend(removedUser.getID())) {
                RemoveFriendOutputData outputData = new RemoveFriendOutputData(currentUsername, removedUsername, false);
                presenter.prepareFailView("Failed to remove friend", outputData);
            }

            // removes the friend in the database
            if (!removeFriendDataAccessInterface.removeFriend(currentUsername, removedUsername)) {
                RemoveFriendOutputData outputData = new RemoveFriendOutputData(currentUsername, removedUsername, false);
                presenter.prepareFailView("Failed to remove friend", outputData);
            }

            RemoveFriendOutputData outputData = new RemoveFriendOutputData(currentUsername, removedUsername, true);
            presenter.prepareSuccessView(outputData);
        }

    }
}
