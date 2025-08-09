package use_case.leave_chat;

import entity.User;

/**
 * The interface of the DAO for the Leave Chat use case
 */
public interface LeaveChatDataAccessInterface {

    /**
     * Gets the current user of the app.
     * @return the current user.
     */
    User getCurrentUser();

    boolean leaveGroupChat(String channelUrl, String userId, String username);
}
