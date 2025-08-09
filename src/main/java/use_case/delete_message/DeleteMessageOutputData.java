package use_case.delete_message;

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

    public String getMessageId() {return messageId;}
    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}
