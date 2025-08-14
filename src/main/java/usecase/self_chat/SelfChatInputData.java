package usecase.self_chat;

/**
 * Input Data for the Self Chat Use Case.
 */
public class SelfChatInputData {
    private final String message;

    public SelfChatInputData(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
