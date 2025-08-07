package use_case.join_chat;

public interface JoinChatOutputBoundary {

    /**
     * Switches the view to return to the list of chats
     */
    void switchToChatsView();

    /**
     * Prepare the success view for the Update Chat use case.
     * @param outputData Output data.
     */
    void prepareSuccessView(JoinChatOutputData outputData);

    /**
     * Prepare the fail view for the Update Chat use case.
     * @param errorMessage Explanation of failure.
     * @param outputData Output data.
     */
    void prepareFailView(String errorMessage, JoinChatOutputData outputData);
}
