package usecase.message_read;

import entity.Chat;
import entity.Message;

/**
 * DAO interface for the Message Read use case.
 */
public interface MessageReadUserDataAccessInterface {

    /**
     * Marks a message as read by a user.
     * @param userId the ID of the user.
     * @param message the message
     * @param chat the chat containing the message
     * @return true if successful
     */
    boolean markMessageAsRead(String userId, Message message, Chat chat);

    /**
     * Returns whether the message is read by a user.
     * @param userId the ID of the user.
     * @param message the message
     * @param chat the chat containing the message
     * @return the true if successful
     */
    boolean isMessageReadByUser(String userId, Message message, Chat chat);
}

