package use_case.self_chat;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Data Access Interface for Self Chat functionality.
 */
public interface SelfChatUserDataAccessInterface {

    /**
     * Saves a message with timestamp.
     *
     * @param message   the message to save
     * @param timestamp when the message was created
     */
    void saveMessage(String message, LocalDateTime timestamp);

    /**
     * Gets all saved messages.
     *
     * @return list of all messages
     */
    List<String> getAllMessages();

    /**
     * Gets all timestamps for saved messages.
     *
     * @return list of all timestamps
     */
    List<LocalDateTime> getAllTimestamps();

    /**
     * Clears all saved messages.
     */
    void clearAllMessages();
}
