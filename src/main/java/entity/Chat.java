package entity;

import java.util.List;

/**
 * The representation of a chat in our program.
 */
public interface Chat {

    /**
     * Adds a user to the chat.
     * @param userID The ID of the user to be added.
     * @return true if successful otherwise false
     */
    boolean addMember(String userID);

    /**
     * Adds a message to the chat.
     * @param message The message to be added.
     * @return true if successful otherwise false
     */
    boolean addMessage(Message message);

    /**
     * Deletes a message from the chat.
     * @param messageId The message to be deleted.
     * @return true if successful otherwise false
     */
    boolean deleteMessage(String messageId);

    /**
     * Checks if a user is in the chat.
     * @param userID The user to be searched.
     * @return true if successful otherwise false
     */
    boolean hasMember(String userID);

    /**
     * Sets the channel used by the chat.
     * @param channelUrl The URL of the channel to be used.
     */
    void setChannelUrl(String channelUrl);

    /**
     * Returns a list of the members in the chat.
     * @return the list of members.
     */
    List<String> getMemberIds();

    /**
     * Returns the URL of the channel associated with the chat.
     * @return the channel URL.
     */
    String getChannelUrl();

    /**
     * Sets the message history of the chat.
     * @param messageHistory the message history
     */
    void setMessageHistory(List<Message> messageHistory);

    /**
     * Returns the message history of the chat.
     * @return the message history.
     */
    List<Message> getMessageHistory();

    /**
     * Returns the name of this chat.
     * @return the chat name.
     */
    String getChatName();
}
