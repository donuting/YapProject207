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
}
