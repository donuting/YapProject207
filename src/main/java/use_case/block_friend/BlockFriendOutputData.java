package use_case.block_friend;

/**
 * Output data for the Block Friend use case.
 */
public class BlockFriendOutputData {
    private final String currentUsername;
    private final String blockedUsername;
    private final boolean success;

    public BlockFriendOutputData(String currentUsername, String blockedUsername, boolean success) {
        this.currentUsername = currentUsername;
        this.blockedUsername = blockedUsername;
        this.success = success;
    }

    public String getCurrentUsername() {
        return currentUsername;
    }

    public String getBlockedUsername() {
        return blockedUsername;
    }

    public boolean isSuccess() {
        return success;
    }
}