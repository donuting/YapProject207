package use_case.delete_message;

import entity.Chat;

/**
 * The interface of the DAO for the Delete Message Use Case.
 */
public interface DeleteMessageDataAccessInterface {

    /**
     * Updates the system to delete a message from a chat.
     * @param MID the ID of the message to be deleted.
     * @param chat the chat in which the message was sent.
     */
    boolean deleteMessage(String MID, Chat chat);

    /**
     * Gets the active group chat the user is viewing.
     * @return the active group chat
     */
    Chat getActiveGroupChat();
}
