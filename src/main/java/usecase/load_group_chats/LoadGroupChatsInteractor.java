package usecase.load_group_chats;

import entity.GroupChat;
import entity.User;

import java.util.HashMap;
import java.util.Map;

/**
 * The interactor for the Load Group Chats use case.
 */
public class LoadGroupChatsInteractor implements LoadGroupChatsInputBoundary{
    private final LoadGroupChatsDataAccessInterface loadGroupChatsDataAccessInterface;
    private final LoadGroupChatsOutputBoundary viewGroupChatsPresenter;

    public LoadGroupChatsInteractor(LoadGroupChatsDataAccessInterface loadGroupChatsDataAccessInterface, LoadGroupChatsOutputBoundary viewGroupChatsPresenter) {
        this.loadGroupChatsDataAccessInterface = loadGroupChatsDataAccessInterface;
        this.viewGroupChatsPresenter = viewGroupChatsPresenter;
    }

    /**
     * Execute the Load Group Chats use case.
     */
    @Override
    public void execute() {
        User currentUser = loadGroupChatsDataAccessInterface.getCurrentUser();
        User updatedCurrentUser = null;
        if (currentUser != null) {
            updatedCurrentUser = loadGroupChatsDataAccessInterface.get(currentUser.getName());
            loadGroupChatsDataAccessInterface.setCurrentUser(updatedCurrentUser);
        }
        if (updatedCurrentUser == null) {
            viewGroupChatsPresenter.loadGroupChatsPrepareFailView("this user doesn't exist");
        } else {
            Map<String, String> channelInfo = new HashMap<>();
            for (GroupChat groupChat : updatedCurrentUser.getGroupChats()) {
                channelInfo.put(groupChat.getChannelUrl(), groupChat.getChatName());
            }
            LoadGroupChatsOutputData outputData = new LoadGroupChatsOutputData(channelInfo);
            viewGroupChatsPresenter.loadGroupChatsPrepareSuccessView(outputData);
        }
    }
}
