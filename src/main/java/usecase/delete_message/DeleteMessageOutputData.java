package usecase.delete_message;

/**
 * Output Data for the delete message Use Case.
 */
public class DeleteMessageOutputData {
    private final String messageId;
    private final boolean useCaseFailed;

    public DeleteMessageOutputData(String messageId, boolean useCaseFailed) {
        this.messageId = messageId;
        this.useCaseFailed = useCaseFailed;
    }

    /**
     * Gets the deleted message ID outputted by the interactor.
     * @return the deleted message ID
     */
    public String getMessageId() {
        return messageId;
    }

    /**
     * Returns whether the use case failed.
     * @return true if unsuccessful
     */
    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}
