package entity;


import org.openapitools.client.model.SendBirdGroupChannel;

import java.util.List;

/**
 * The representation of a chat in our program.
 */
public interface Chat {

    /**
     * Adds a user to the chat.
     * If the user is blocked, they will not be added.
     * @param userID The ID of the user to be added.
     * @return true if successful otherwise false
     */
    boolean AddMember(String userID);

    /**
     * Adds a message to the chat.
     * If the recipient is blocked, no message will be sent.
     * @param message The message to be added.
     * @return true if successful otherwise false
     */
    boolean AddMessage(Message message);

    /**
     * deletes a message from the chat.
     * @param message The message to be deleted.
     * @return true if successful otherwise false
     */
    boolean DeleteMessage(Message message);

    /**
     * checks if a user is in the chat.
     * @param userID The user to be searched.
     * @return true if successful otherwise false
     */
    boolean HasMember(String userID);

    /**
     * Sets the channel used by the chat.
     * @param channelURL The URL of the channel to be used.
     */
    void setChannelURL(String channelURL);

    /**
     * Returns a list of the members in the chat.
     * @returns the list of members.
     */
    List<String> getMemberIDs();
}
