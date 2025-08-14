package usecase.remove_friend;

/**
 * The input boundary for the Remove Friend use case.
 */
public interface RemoveFriendInputBoundary {
    /**
     * Execute the Remove Friend use case.
     * @param inputData The input data for this use case.
     */
    void execute(RemoveFriendInputData inputData);
}

