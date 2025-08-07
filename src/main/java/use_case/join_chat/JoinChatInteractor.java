package use_case.join_chat;

import entity.GroupChat;
import entity.User;

/**
 * The interactor for the Join Chat use case.
 */
public class JoinChatInteractor implements JoinChatInputBoundary {
    private final JoinChatDataAccessInterface joinChatDataAccessInterface;
    private final JoinChatOutputBoundary presenter;

    public JoinChatInteractor(JoinChatDataAccessInterface joinChatDataAccessInterface, JoinChatOutputBoundary presenter) {
        this.joinChatDataAccessInterface = joinChatDataAccessInterface;
        this.presenter = presenter;
    }

    /**
     * Execute the Join Chat use case.
     *
     * @param inputData The input data for this use case.
     */
    @Override
    public void execute(JoinChatInputData inputData) {
        User currentUser = joinChatDataAccessInterface.getCurrentUser();
        if (currentUser == null) {
            JoinChatOutputData outputData = new JoinChatOutputData(null);
            presenter.prepareFailView("This user doesn't exist", outputData);
        } else {
            String channelUrl = inputData.getChannelUrl();
            GroupChat groupChat = joinChatDataAccessInterface.load(channelUrl);
            if (groupChat == null) {
                JoinChatOutputData outputData = new JoinChatOutputData(null);
                presenter.prepareFailView("This chat doesn't exist", outputData);
            } else {
                GroupChat updatedChat = joinChatDataAccessInterface.addUser(currentUser.getID(), channelUrl);
                if (updatedChat == null) {
                    JoinChatOutputData outputData = new JoinChatOutputData(null);
                    presenter.prepareFailView("This chat doesn't exist", outputData);
                }
                currentUser.addGroupChat(updatedChat);
                JoinChatOutputData outputData = new JoinChatOutputData(updatedChat);
                presenter.prepareSuccessView(outputData);
            }
        }
    }

    /**
     * Switches the view to return to the list of chats
     */
    @Override
    public void switchToChatsView() {
        presenter.switchToChatsView();
    }
}
