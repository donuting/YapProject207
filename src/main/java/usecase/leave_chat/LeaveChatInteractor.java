package usecase.leave_chat;

import entity.User;

/**
 * The interactor for the Leave Chat use case.
 */
public class LeaveChatInteractor implements LeaveChatInputBoundary {
    private final LeaveChatDataAccessInterface leaveChatDataAccessInterface;
    private final LeaveChatOutputBoundary viewGroupChatsPresenter;

    public LeaveChatInteractor(LeaveChatDataAccessInterface leaveChatDataAccessInterface,
                               LeaveChatOutputBoundary viewGroupChatsPresenter) {
        this.leaveChatDataAccessInterface = leaveChatDataAccessInterface;
        this.viewGroupChatsPresenter = viewGroupChatsPresenter;
    }

    /**
     * Execute the Leave Chat use case.
     *
     * @param leaveChatInputData The input data for this use case.
     */
    @Override
    public void execute(LeaveChatInputData leaveChatInputData) {
        User currentUser = leaveChatDataAccessInterface.getCurrentUser();
        if (currentUser == null) {
            LeaveChatOutputData outputData = new LeaveChatOutputData(null);
            viewGroupChatsPresenter.leaveChatPrepareFailView("This user doesn't exist", outputData);
        }
        else {
            String channelUrl = leaveChatInputData.getChannelUrl();
            boolean success = leaveChatDataAccessInterface.leaveGroupChat(
                    channelUrl, currentUser.getID(), currentUser.getName());
            if (!success) {
                LeaveChatOutputData outputData = new LeaveChatOutputData(null);
                viewGroupChatsPresenter.leaveChatPrepareFailView("Failed to leave chat", outputData);
            }
            else {
                currentUser.removeGroupChat(channelUrl);
                LeaveChatOutputData outputData = new LeaveChatOutputData(channelUrl);
                viewGroupChatsPresenter.leaveChatPrepareSuccessView(outputData);
            }
        }
    }
}
