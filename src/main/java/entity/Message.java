package entity;

/**
 * The representation of a message in our program.
 */
public interface Message {

    /**
     * returns the sender of the message.
     * @return sender of the message
     */
    String GetSenderId();

    /**
     * returns the text of the message.
     * @return text of the message
     */
    String GetText();

    /**
     * Sets the ID of the message.
     * @param MID the ID of the message
     */
    void SetMID(Integer MID);

    /**
     * returns the ID of the message.
     * @return ID of the message
     */
    Integer GetMID();
}
