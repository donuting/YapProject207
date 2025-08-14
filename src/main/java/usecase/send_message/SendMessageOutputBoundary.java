package usecase.send_message;

/**
 * The output boundary for the send message Use Case.
 */
public interface SendMessageOutputBoundary {

    /**
     * Prepares the success view for the send message Use Case.
     * @param sendMessageOutputData the output data.
     */
    void prepareSuccessSendMessageView(SendMessageOutputData sendMessageOutputData);

    /**
     * Prepares the fail view for the send message Use Case.
     * @param sendMessageOutputData the output data.
     * @param errorMessage The error message to be shown.
     */
    void prepareFailSendMessageView(String errorMessage, SendMessageOutputData sendMessageOutputData);
}
