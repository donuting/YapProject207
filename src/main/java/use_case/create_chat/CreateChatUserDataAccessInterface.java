package use_case.create_chat;

import entity.GroupChat;

public interface CreateChatUserDataAccessInterface {

    /**
     * Creates a channel and sets it as the channel of newChat, which is a newly created chat.
     * @param newChat the newly created chat
     */
    public void create(GroupChat newChat);
}
