package entity;

/**
 * The representation of a message in our program.
 */
public interface Message {

    /**
     * returns the sender of the message.
     * @return sender of the message
     */
    User GetSender();

    /**
     * returns the text of the message.
     * @return text of the message
     */
    String GetText();
}
