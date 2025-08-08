package use_case.load_group_chats;

public interface LoadGroupChatsOutputBoundary {
    /**
     * Prepare the fail view for the Update Chat use case.
     * @param errorMessage Explanation of failure.
     */
    void loadGroupChatsPrepareFailView(String errorMessage);

    /**
     * Prepare the success view for the Leave Chat use case.
     * @param outputData Output data.
     */
    void loadGroupChatsPrepareSuccessView(LoadGroupChatsOutputData outputData);
}
