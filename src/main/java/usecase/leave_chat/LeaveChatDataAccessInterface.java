package usecase.leave_chat;

import entity.User;

/**
 * The interface of the DAO for the Leave Chat use case.
 */
public interface LeaveChatDataAccessInterface {

    /**
     * Gets the current user of the app.
     * @return the current user.
     */
    User getCurrentUser();

    /**
     * Allows a user to leave a group chat.
     * @param channelUrl the URL of the channel
     * @param userId the user ID of the user leaving
     * @param username the username of the user leaving
     * @return the current user.
     */
    boolean leaveGroupChat(String channelUrl, String userId, String username);
}
