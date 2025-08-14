package usecase.block_friend;

/**
 * Output data for the Block Friend use case.
 */
public class BlockFriendOutputData {
    private final String currentUsername;
    private final String blockedUsername;
    private final String blockedId;
    private final boolean success;

    public BlockFriendOutputData(String currentUsername, String blockedUsername, String blockedId, boolean success) {
        this.currentUsername = currentUsername;
        this.blockedUsername = blockedUsername;
        this.blockedId = blockedId;
        this.success = success;
    }

    public String getCurrentUsername() {
        return currentUsername;
    }

    public String getBlockedUsername() {
        return blockedUsername;
    }

    public String getBlockedId() {
        return blockedId;
    }

    public boolean isSuccess() {
        return success;
    }
}