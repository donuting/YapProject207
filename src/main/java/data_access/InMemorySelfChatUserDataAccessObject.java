package data_access;

import use_case.self_chat.SelfChatUserDataAccessInterface;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * In-memory implementation of the SelfChatDataAccessInterface.
 * This stores messages in memory and will be lost when the application closes.
 */
public class InMemorySelfChatUserDataAccessObject implements SelfChatUserDataAccessInterface {

    private final List<String> messages;
    private final List<LocalDateTime> timestamps;

    public InMemorySelfChatUserDataAccessObject() {
        this.messages = new ArrayList<>();
        this.timestamps = new ArrayList<>();
    }

    @Override
    public void saveMessage(String message, LocalDateTime timestamp) {
        if (message == null || timestamp == null) {
            throw new IllegalArgumentException("Message and timestamp cannot be null");
        }

        messages.add(message);
        timestamps.add(timestamp);
    }

    @Override
    public List<String> getAllMessages() {
        return new ArrayList<>(messages);
    }

    @Override
    public List<LocalDateTime> getAllTimestamps() {
        return new ArrayList<>(timestamps);
    }

    @Override
    public void clearAllMessages() {
        messages.clear();
        timestamps.clear();
    }

    /**
     * Gets the number of stored messages.
     * @return the count of messages
     */
    public int getMessageCount() {
        return messages.size();
    }

    /**
     * Gets a specific message by index.
     * @param index the index of the message
     * @return the message at the specified index
     * @throws IndexOutOfBoundsException if index is invalid
     */
    public String getMessage(int index) {
        return messages.get(index);
    }

    /**
     * Gets a specific timestamp by index.
     * @param index the index of the timestamp
     * @return the timestamp at the specified index
     * @throws IndexOutOfBoundsException if index is invalid
     */
    public LocalDateTime getTimestamp(int index) {
        return timestamps.get(index);
    }
}