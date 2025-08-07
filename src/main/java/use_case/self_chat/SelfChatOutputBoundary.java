package use_case.self_chat;

/**
 * Output Boundary for the Self Chat Use Case.
 */
public interface SelfChatOutputBoundary {

    /**
     * Prepares the success view for the Self Chat Use Case.
     *
     * @param outputData the output data
     */
    void presentMessage(SelfChatOutputData outputData);

    /**
     * Prepares the view for clearing messages.
     *
     * @param success      whether the operation was successful
     * @param errorMessage error message if unsuccessful
     */
    void presentClearResult(boolean success, String errorMessage);

    /**
     * Prepares the failure view for the Self Chat Use Case.
     *
     * @param errorMessage the explanation of the failure
     */
    void presentError(String errorMessage);
}
