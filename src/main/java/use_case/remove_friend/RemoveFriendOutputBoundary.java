package use_case.remove_friend;

/**
 * Output boundary for the Remove Friend use case.
 */
public interface RemoveFriendOutputBoundary {
    /**
     * Prepare the success view for the Block Friend use case.
     * @param outputData Output data.
     */
    void removeFriendPrepareSuccessView(RemoveFriendOutputData outputData);

    /**
     * Prepare the fail view for the Block Friend use case.
     * @param errorMessage Explanation of failure.
     * @param outputData Output data.
     */
    void removeFriendPrepareFailView(String errorMessage);
}
