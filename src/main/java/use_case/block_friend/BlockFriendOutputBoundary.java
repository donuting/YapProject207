package use_case.block_friend;

/**
 * Output boundary for the Block Friend use case.
 */
public interface BlockFriendOutputBoundary {
    /**
     * Prepare the success view for the Block Friend use case.
     * @param outputData Output data.
     */
    void blockFriendPrepareSuccessView(BlockFriendOutputData outputData);

    /**
     * Prepare the fail view for the Block Friend use case.
     * @param errorMessage Explanation of failure.
     * @param outputData Output data.
     */
    void blockFriendPrepareFailView(String errorMessage, BlockFriendOutputData outputData);
}