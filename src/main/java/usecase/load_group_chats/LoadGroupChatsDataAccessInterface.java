package usecase.load_group_chats;

import entity.User;

/**
 * The interface of the DAO for the Load Group Chats use case.
 */
public interface LoadGroupChatsDataAccessInterface {
    /**
     * Gets the current user of the app.
     * @return the current user.
     */
    User getCurrentUser();

    /**
     * Gets the user using their username.
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
