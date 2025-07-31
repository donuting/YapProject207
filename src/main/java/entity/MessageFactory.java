package entity;

/**
 * Factory for creating messages.
 */
public interface MessageFactory {
    /**
     * Creates a new Message.
     *
     * @param senderId the sender of the new message
     * @param text the text of the new message
     * @return the new user
     */
    Message create(String senderId, String text);

    /**
     * Creates a Message with preexisting Message ID.
     *
     * @param senderId the sender of the new message
     * @param text the text of the new message
     * @return the new user
     */
    Message create(String senderId, String text, Integer messageId);
}