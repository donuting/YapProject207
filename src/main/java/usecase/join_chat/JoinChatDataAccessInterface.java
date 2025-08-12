package usecase.join_chat;

import entity.GroupChat;
import entity.User;

/**
 * The DAO for the Join Chat use case.
 */
public interface JoinChatDataAccessInterface {

    /**
     * Gets the current user of the app.
     * @return the current user.
     */
    User getCurrentUser();

    /**
     * Loads a GroupChat object given its channel URL.
     * @param channelUrl the channel URL.
     * @return the retrieved group chat.
     */
    GroupChat load(String channelUrl);

    /**
     * Adds a user to a group chat given its channel URL, and returns the updated group chat.
     * @param channelUrl the channel URL.
     * @param userId the user's ID.
     * @return the updated group chat.
     */
    GroupChat addUser(String userId, String channelUrl);

    /**
     * Adds a group chat to a user's list of group chats.
     * @param chat the group chat.
     * @param name the user's username
     */
    void saveGroupChat(GroupChat chat, String name);

    /**
     * Gets a user given their username.
     * @param username the user's username
     * @return the requested user.
     */
    User get(String username);
}
