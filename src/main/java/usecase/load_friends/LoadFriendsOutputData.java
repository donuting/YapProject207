package usecase.load_friends;

import java.util.Map;

/**
 * Output Data for the Load Friends use case.
 */
public class LoadFriendsOutputData {
    private final Map<String, String> channelToUserIdData;

    public LoadFriendsOutputData(Map<String, String> channelToUserIdData) {
        this.channelToUserIdData = channelToUserIdData;
    }

    /**
     * Gets the channel to user ID data outputted by the interactor.
     * This is a map from personal channel URLs to the ID of the other user in the personal chat.
     * @return the channel to user ID data
     */
    public Map<String, String> getChannelToUserIdData() {
        return channelToUserIdData;
    }
}
