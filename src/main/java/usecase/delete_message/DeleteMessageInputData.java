package usecase.delete_message;

/**
 * The input data for the delete message Use Case.
 */
public class DeleteMessageInputData {
    private final String messageId;

    public DeleteMessageInputData(String messageId) {
        this.messageId = messageId;
    }

    /**
     * Gets the inputted message ID.
     *
     * @return the inputted message ID.
     */
    public String getMessageId() {
        return messageId;
    }
}
