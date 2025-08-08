package use_case.update_chat;

public interface UpdateChatOutputBoundary {

    void leaveChatView();

    /**
     * Prepare the success view for the Update Chat use case.
     * @param outputData Output data.
     */
    void updateChatPrepareSuccessView(UpdateChatOutputData outputData);

    /**
     * Prepare the fail view for the Update Chat use case.
     * @param errorMessage Explanation of failure.
     * @param outputData Output data.
     */
    void updateChatPrepareFailView(String errorMessage, UpdateChatOutputData outputData);
}
