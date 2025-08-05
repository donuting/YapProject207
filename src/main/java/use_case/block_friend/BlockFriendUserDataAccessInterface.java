package use_case.block_friend;

import entity.User;

/**
 * DAO interface for the Block Friend use case.
 */
public interface BlockFriendUserDataAccessInterface {

    /**
     * Block the target user for the current user.
     * @param currentUsername The user performing the block.
     * @param blockedUsername The user to be blocked.
     * @return the blocked user's ID.
     */
    String blockFriend(String currentUsername, String blockedUsername);

    /**
     * Checks if the given username exists.
     *
     * @param username the username to look for
     * @return true if a user with the given username exists; false otherwise
     */
    boolean existsByName(String username);

    /**
     * Gets the current user.
     *
     * @return the current user.
     */
    User getCurrentUser();
}