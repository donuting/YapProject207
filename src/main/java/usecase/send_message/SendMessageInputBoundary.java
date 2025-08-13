package usecase.send_message;

/**
 * The send message Use Case.
 */
public interface SendMessageInputBoundary {

    /**
     * Execute the send message Use Case.
     * @param sendMessageInputData the input data for this use case
     */
    void execute(SendMessageInputData sendMessageInputData);
}
