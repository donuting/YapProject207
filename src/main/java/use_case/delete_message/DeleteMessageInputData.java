package use_case.delete_message;

/**
 * The input data for the delete message Use Case.
 */
public class DeleteMessageInputData {
    private final String messageId;

    public DeleteMessageInputData(String messageId) {
        this.messageId = messageId;
    }

    public String getMessageId() {
        return messageId;
    }
}
