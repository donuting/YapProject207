package use_case.load_friends;

import entity.GroupChat;
import entity.User;

import java.util.HashMap;
import java.util.Map;

public class LoadFriendsInteractor implements LoadFriendsInputBoundary{
    private final LoadFriendsDataAccessInterface loadFriendsDataAccessInterface;
    private final LoadFriendsOutputBoundary loadFriendsPresenter;

    public LoadFriendsInteractor(LoadFriendsDataAccessInterface loadFriendsDataAccessInterface, LoadFriendsOutputBoundary loadFriendsPresenter) {
        this.loadFriendsDataAccessInterface = loadFriendsDataAccessInterface;
        this.loadFriendsPresenter = loadFriendsPresenter;
    }


    /**
     * Executes the Load Friends Use Case.
     */
    @Override
    public void execute() {
        User currentUser = loadFriendsDataAccessInterface.getCurrentUser();
        if (currentUser == null) {
            loadFriendsPresenter.loadFriendsPrepareFailView("this user doesn't exist");
        } else {
            Map<String, String> channelInfo = new HashMap<>();
            for (GroupChat personalChat : currentUser.getPersonalChats()) {
                String friendId = null;
                for (String userId : personalChat.getMemberIds()) {
                    if (!userId.equals(currentUser.getID())) {
                        friendId = userId;
                    }
                }
                channelInfo.put(personalChat.getChannelUrl(), friendId);
            }
            LoadFriendsOutputData outputData = new LoadFriendsOutputData(channelInfo);
            loadFriendsPresenter.loadFriendsPrepareSuccessView(outputData);
        }

    }
}
