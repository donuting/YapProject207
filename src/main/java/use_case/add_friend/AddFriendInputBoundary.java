package use_case.add_friend;

/**
 * Input Boundary for actions which are related to adding a friend.
 */
public interface AddFriendInputBoundary {
    /**
     * Executes the Add Friend use case.
     * @param inputData input data for this use case
     */
    void execute(AddFriendInputData inputData);

    /**
     * Executes the switch to the Main Menu View.
     */
    void switchToMainMenuView();
}
