package use_case.block_friend;

import entity.User;

/**
 * DAO interface for the Block Friend use case.
 */
public interface BlockFriendUserDataAccessInterface {
    /**
     * Get a user object by username.
     */
    User getUser(String username);

    /**
     * Block the target user for the current user.
     * @param currentUser The user performing the block.
     * @param blockedUser The user to be blocked.
     * @return true if blocking successful, false otherwise.
     */
    boolean blockFriend(User currentUser, User blockedUser);
}