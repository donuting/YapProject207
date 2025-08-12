package usecase.remove_friend;

import entity.User;

public interface RemoveFriendDataAccessInterface {

    /**
     * Check if a user exists.
     * @param removedUsername The user being checked.
     * @return true if the user exists.
     */
    boolean existsByName(String removedUsername);

    /**
     * Unfriend the target user for the current user.
     * @param currentUser The user performing the block.
     * @param removedUsername The user to be unfriended.
     * @param removedId The ID of the user to be unfriended.
     * @return true if successful.
     */
    boolean removeFriend(User currentUser, String removedUsername, String removedId);

    /**
     * Gets the current user.
     *
     * @return the current user.
     */
    User getCurrentUser();

    /**
     * Gets a username given an ID.
     * @param removedId the ID of the user being removed
     * @return the corresponding user.
     */
    String getUsernameFromId(String removedId);

    /**
     * Returns the user with the given username.
     * @param username of person to fetch
     * @return user with this username
     */
    User get(String username);
}
