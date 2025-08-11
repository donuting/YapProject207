package use_case.block_friend;

/**
 * Input data for the Block Friend use case.
 */
public class BlockFriendInputData {
    private final String blockedId;

    public BlockFriendInputData(String blockedUsername) {;
        this.blockedId = blockedUsername;
    }

    public String getBlockedId() {
        return blockedId;
    }
}