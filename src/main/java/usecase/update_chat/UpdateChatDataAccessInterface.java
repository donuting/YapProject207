package usecase.update_chat;

import entity.GroupChat;
import entity.User;

/**
 * The DAO for the Update Chat use case.
 */
public interface UpdateChatDataAccessInterface {
    /**
     * Gets the current active group chat.
     * @return the active group chat
     */
    GroupChat getActiveGroupChat();

    /**
     * Loads a group chat given its channel URL.
     * @param channelUrl the URL of the channel
     * @return the group chat
     */
    GroupChat load(String channelUrl);

    /**
     * Sets the current active group chat.
     * @param newChat active group chat
     */
    void setActiveGroupChat(GroupChat newChat);

    /**
     * Gets the current user of the app.
     * @return the current user
     */
    User getCurrentUser();
}
