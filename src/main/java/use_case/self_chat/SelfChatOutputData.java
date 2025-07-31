package use_case.self_chat;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Output Data for the Self Chat Use Case.
 */
public class SelfChatOutputData {
    private final String message;
    private final LocalDateTime timestamp;
    private final boolean success;
    private final String errorMessage;
    private final List<String> allMessages;
    private final List<LocalDateTime> allTimestamps;

    // Constructor for single message
    public SelfChatOutputData(String message, LocalDateTime timestamp, boolean success, String errorMessage) {
        this.message = message;
        this.timestamp = timestamp;
        this.success = success;
        this.errorMessage = errorMessage;
        this.allMessages = null;
        this.allTimestamps = null;
    }

    // Constructor for multiple messages (loading)
    public SelfChatOutputData(List<String> allMessages, List<LocalDateTime> allTimestamps, boolean success, String errorMessage) {
        this.message = null;
        this.timestamp = null;
        this.success = success;
        this.errorMessage = errorMessage;
        this.allMessages = allMessages;
        this.allTimestamps = allTimestamps;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public List<String> getAllMessages() {
        return allMessages;
    }

    public List<LocalDateTime> getAllTimestamps() {
        return allTimestamps;
    }
}

