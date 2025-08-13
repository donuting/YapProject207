package usecase.leave_chat;

/**
 * The input boundary for the Leave Chat use case.
 */
public interface LeaveChatInputBoundary {
    /**
     * Execute the Leave Chat use case.
     * @param leaveChatInputData The input data for this use case.
     */
    void execute(LeaveChatInputData leaveChatInputData);
}
