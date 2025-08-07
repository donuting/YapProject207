package use_case.block_friend;

/**
 * Input data for the Block Friend use case.
 */
public class BlockFriendInputData {
    private final String blockedUsername;

    public BlockFriendInputData(String currentUsername, String blockedUsername) {;
        this.blockedUsername = blockedUsername;
    }

    public String getBlockedUsername() {
        return blockedUsername;
    }
}