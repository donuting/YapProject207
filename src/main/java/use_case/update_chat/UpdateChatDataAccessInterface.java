package use_case.update_chat;

import entity.Chat;

/**
 * The DAO for the Update Chat use case.
 */
public interface UpdateChatDataAccessInterface {
    Chat getActiveChat();

    Chat load(String channelUrl);

    void setActiveChat(Chat newChat);
}
