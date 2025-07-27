package use_case.delete_message;

/**
 * The delete message Use Case.
 */
public interface DeleteMessageInputBoundary {

    /**
     * Execute the delete message Use Case.
     * @param deleteMessageInputData the input data for this use case
     */
    void execute(DeleteMessageInputData deleteMessageInputData);
}
