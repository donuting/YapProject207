package usecase.block_friend;

/**
 * The input boundary for the Block Friend use case.
 */
public interface BlockFriendInputBoundary {
    /**
     * Execute the Block Friend use case.
     * @param inputData The input data for this use case.
     */
    void execute(BlockFriendInputData inputData);
}
