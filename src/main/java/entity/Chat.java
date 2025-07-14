package entity;


/**
 * The representation of a chat in our program.
 */
public interface Chat {

    /**
     * Adds a user to the chat.
     * @param user The user to be added.
     * @return true if successful otherwise false
     */
    boolean AddMember(User user);

    /**
     * Adds a message to the chat.
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
     * @param user The user to be searched.
     * @return true if successful otherwise false
     */
    boolean HasMember(User user);

}
