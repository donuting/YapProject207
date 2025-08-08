package use_case.join_chat;

public interface JoinChatOutputBoundary {

    /**
     * Prepare the success view for the Update Chat use case.
     * @param outputData Output data.
     */
    void joinChatPrepareSuccessView(JoinChatOutputData outputData);

    /**
     * Prepare the fail view for the Update Chat use case.
     * @param errorMessage Explanation of failure.
     * @param outputData Output data.
     */
    void joinChatPrepareFailView(String errorMessage, JoinChatOutputData outputData);
}
