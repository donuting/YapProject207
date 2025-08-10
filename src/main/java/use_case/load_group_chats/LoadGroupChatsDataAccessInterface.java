package use_case.load_group_chats;

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
}
