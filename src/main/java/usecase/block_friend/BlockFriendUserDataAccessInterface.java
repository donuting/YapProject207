package usecase.block_friend;

import entity.User;

/**
 * DAO interface for the Block Friend use case.
 */
public interface BlockFriendUserDataAccessInterface {

    /**
     * Block the target user for the current user.
     * @param currentUser The user performing the block.
     * @param blockedUsername the name of the user to be blocked
     * @param blockedUserId The user to be blocked.
     * @return true if successful
     */
    boolean blockFriend(User currentUser, String blockedUsername, String blockedUserId);

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

    /**
     * Gets a username given an ID.
     *
     * @param blockedId the ID to be blocked.
     * @return the corresponding username.
     */
    String getUsernameFromId(String blockedId);
}
