package use_case.delete_message;

/**
 * The output boundary for the delete message Use Case.
 */
public interface DeleteMessageOutputBoundary {

    /**
     * Prepares the success view for the delete message Use Case.
     * @param deleteMessageOutputData the output data.
     */
    void prepareSuccessSendMessageView(DeleteMessageOutputData deleteMessageOutputData);

    /**
     * Prepares the fail view for the delete message Use Case.
     * @param deleteMessageOutputData the output data.
     * @param errorMessage The error message to be shown.
     */
    void prepareFailSendMessageView(String errorMessage, DeleteMessageOutputData deleteMessageOutputData);
}
