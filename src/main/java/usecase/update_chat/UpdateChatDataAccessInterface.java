package usecase.update_chat;

import entity.GroupChat;
import entity.User;

/**
 * The DAO for the Update Chat use case.
 */
public interface UpdateChatDataAccessInterface {
    GroupChat getActiveGroupChat();

    GroupChat load(String channelUrl);

    void setActiveGroupChat(GroupChat newChat);

    User getCurrentUser();
}
