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

    /**
     * Gets the current username outputted by the interactor.
     * @return the current username.
     */
    public String getCurrentUsername() {
        return currentUsername;
    }

    /**
     * Gets the blocked username outputted by the interactor.
     * @return the blocked username.
     */
    public String getBlockedUsername() {
        return blockedUsername;
    }

    /**
     * Gets the blocked ID outputted by the interactor.
     * @return the blocked ID.
     */
    public String getBlockedId() {
        return blockedId;
    }

    /**
     * Returns whether the use case succeeded.
     * @return true if successful
     */
    public boolean isSuccess() {
        return success;
    }
}
