package use_case.leave_chat;

public interface LeaveChatOutputBoundary {

    /**
     * Prepare the fail view for the Update Chat use case.
     * @param errorMessage Explanation of failure.
     * @param outputData Output data.
     */
    void leaveChatPrepareFailView(String errorMessage, LeaveChatOutputData outputData);

    /**
     * Prepare the success view for the Leave Chat use case.
     * @param outputData Output data.
     */
    void leaveChatPrepareSuccessView(LeaveChatOutputData outputData);
}
