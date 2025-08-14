package usecase.block_friend;

/**
 * Input data for the Block Friend use case.
 */
public class BlockFriendInputData {
    private final String blockedId;

    public BlockFriendInputData(String blockedUsername) {
        this.blockedId = blockedUsername;
    }

    /**
     * Gets the inputted user ID.
     *
     * @return the inputted user ID.
     */
    public String getBlockedId() {
        return blockedId;
    }
}
