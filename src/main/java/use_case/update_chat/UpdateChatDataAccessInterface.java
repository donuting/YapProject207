package use_case.update_chat;

import entity.Chat;

public interface UpdateChatDataAccessInterface {
    Chat getActiveChat();

    Chat load(String channelUrl);

    void setActiveChat(Chat newChat);
}
