package use_case.message_read;

import entity.Chat;
import entity.Message;

/**
 * DAO interface for the Message Read use case.
 */
public interface MessageReadUserDataAccessInterface {

    boolean markMessageAsRead(String userId, Message message, Chat chat);

    boolean isMessageReadByUser(String userId, Message message, Chat chat);
}