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
        // In the case where the current user is joining a chat
        if (inputData.getUsername() == null || inputData.getUsername().isEmpty()) {
            User user = joinChatDataAccessInterface.getCurrentUser();
            String channelUrl = inputData.getChannelUrl();
            GroupChat groupChat = joinChatDataAccessInterface.load(channelUrl);
            if (groupChat == null) {
                JoinChatOutputData outputData = new JoinChatOutputData(null);
                presenter.joinChatPrepareFailView("This chat doesn't exist", outputData);
            } else {
                GroupChat updatedChat = joinChatDataAccessInterface.addUser(user.getID(), channelUrl);
                // Save the chat to the user
                joinChatDataAccessInterface.saveGroupChat(updatedChat, user.getName());
                user.addGroupChat(updatedChat);

                JoinChatOutputData outputData = new JoinChatOutputData(updatedChat);
                presenter.joinChatPrepareSuccessView(outputData);
            }
        }
        // In the case where the current user is adding another user to a group chat they are in.
        else {
            User user = joinChatDataAccessInterface.get(inputData.getUsername());
            if (user == null) {
                JoinChatOutputData outputData = new JoinChatOutputData(null);
                presenter.joinChatPrepareFailView("This user doesn't exist", outputData);
            } else {
                // Grab the group chat from the current user's group chats
                String channelUrl = inputData.getChannelUrl();
                User currentUser = joinChatDataAccessInterface.getCurrentUser();
                GroupChat groupChat = null;
                for (GroupChat gc : currentUser.getGroupChats()) {
                    if (gc.getChannelUrl().equals(channelUrl)) {
                        groupChat = gc;
                    }
                }
                if (groupChat == null) {
                    JoinChatOutputData outputData = new JoinChatOutputData(null);
                    presenter.joinChatPrepareFailView("This chat doesn't exist", outputData);
                } else {
                    GroupChat updatedChat = joinChatDataAccessInterface.addUser(user.getID(), channelUrl);
                    // Save the chat to the user
                    joinChatDataAccessInterface.saveGroupChat(updatedChat, user.getName());

                    // Update the group chat object in memory
                    groupChat.addMember(user.getID());

                    JoinChatOutputData outputData = new JoinChatOutputData(updatedChat);
                    presenter.joinChatPrepareSuccessView(outputData);
                }
            }
        }
    }
}
