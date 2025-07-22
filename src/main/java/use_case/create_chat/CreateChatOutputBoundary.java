package use_case.create_chat;

public interface CreateChatOutputBoundary {

    /**
     * Prepares the success view for the Create Chat Case.
     * @param outputData the output data
     */
    void prepareSuccessView(CreateChatOutputData outputData);

    /**
     * Prepares the failure view for the Create Chat Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);

    /**
     * Switches the view to the newly created chat.
     */
    void switchToChatView();
}
