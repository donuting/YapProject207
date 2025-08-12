package use_case.load_friends;

import entity.User;

/**
 * The interface of the DAO for the Load Friends use case.
 */
public interface LoadFriendsDataAccessInterface {
    /**
     * Gets the current user of the app.
     * @return the current user.
     */
    User getCurrentUser();

    /**
     * Gets the user using their username
     * @param username the user's username
     * @return the requested user.
     */
    User get(String username);

    /**
     * Sets the current user of the app.
     * @param user the current user.
     */
    void setCurrentUser(User user);
}
