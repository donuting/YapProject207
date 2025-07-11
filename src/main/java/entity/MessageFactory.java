package entity;

import java.util.List;

/**
 * Factory for creating messages.
 */
public interface MessageFactory {
    /**
     * Creates a new Message.
     *
     * @param sender the sender of the new message
     * @param text  the text of the new message
     * @return the new user
     */
    Message create(User sender, String text);
}