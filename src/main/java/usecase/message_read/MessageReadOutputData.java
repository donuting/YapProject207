package usecase.message_read;

public class MessageReadOutputData {
    private final boolean success;

    public MessageReadOutputData(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }
}