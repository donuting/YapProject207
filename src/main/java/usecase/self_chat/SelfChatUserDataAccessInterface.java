package usecase.self_chat;

import java.util.List;

import entity.Message;
import entity.User;

/**
 * Data Access Interface for Self Chat functionality.
 */
public interface SelfChatUserDataAccessInterface {

    /**
     * Saves a message in self chat.
     *
     * @param message the message to save
     * @return the sent message
     */
    Message sendMessage(Message message);

    /**
     * Gets all saved messages.
     *
     * @return list of all messages
     */
    List<Message> loadMessages();

    /**
     * Clears all saved messages.
     */
    void clearAllMessages();

    /**
     * Gets the current user.
     *
     * @return the current user.
     */
    User getCurrentUser();
}
