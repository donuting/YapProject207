package entity;

import org.openapitools.client.model.SendBirdGroupChannel;

import java.util.List;

public class PersonalChat implements Chat{
    /**
     * Adds a user to the chat.
     *
     * @param user The user to be added.
     * @return true if successful otherwise false
     */
    @Override
    public boolean AddMember(User user) {
        return false;
    }

    /**
     * Adds a message to the chat.
     *
     * @param message The message to be added.
     * @return true if successful otherwise false
     */
    @Override
    public boolean AddMessage(Message message) {
        return false;
    }

    /**
     * deletes a message from the chat.
     *
     * @param message The message to be deleted.
     * @return true if successful otherwise false
     */
    @Override
    public boolean DeleteMessage(Message message) {
        return false;
    }

    /**
     * checks if a user is in the chat.
     *
     * @param user The user to be searched.
     * @return true if successful otherwise false
     */
    @Override
    public boolean HasMember(User user) {
        return false;
    }

    /**
     * Sets the channel used by the chat.
     *
     * @param channelURL The URL of the channel to be used.
     */
    @Override
    public void setChannelURL(String channelURL) {

    }

    /**
     * Returns a list of the members in the chat.
     *
     * @returns the list of members.
     */
    @Override
    public List<String> getMemberIDs() {
        return List.of();
    }
}
