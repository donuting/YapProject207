package use_case.self_chat;

import java.time.LocalDateTime;

/**
 * Input Data for the Self Chat Use Case.
 */
public class SelfChatInputData {
    private final String message;
    private final LocalDateTime timestamp;

    public SelfChatInputData(String message) {
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
