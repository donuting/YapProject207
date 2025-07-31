package use_case.block_friend;

/**
 * Input data for the Block Friend use case.
 */
public class BlockFriendInputData {
    private final String currentUsername;
    private final String blockedUsername;

    public BlockFriendInputData(String currentUsername, String blockedUsername) {
        this.currentUsername = currentUsername;
        this.blockedUsername = blockedUsername;
    }

    public String getCurrentUsername() {
        return currentUsername;
    }

    public String getBlockedUsername() {
        return blockedUsername;
    }
}