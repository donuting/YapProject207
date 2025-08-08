package use_case.send_message;

import entity.Chat;
import entity.Message;
import entity.User;

/**
 * The interface of the DAO for the send message Use Case.
 */
public interface SendMessageDataAccessInterface {

    /**
     * sends a message in a chat.
     * @param message the message to be sent
     * @param chat the chat in which the message will be sent
     */
    Message sendMessage(Message message, Chat chat);

    /**
     * Gets the current user of the app.
     * @return the current user.
     */
    User getCurrentUser();

    /**
     * Gets the active group chat the user is viewing.
     * @return the active group chat
     */
    Chat getActiveGroupChat();
}
