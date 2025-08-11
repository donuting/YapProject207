package use_case.load_friends;

import java.util.Map;

/**
 * Output Data for the Load Friends use case.
 */
public class LoadFriendsOutputData {
    private final Map<String, String> channelToUserIdData;

    public LoadFriendsOutputData(Map<String, String> channelToUserIdData) {
        this.channelToUserIdData = channelToUserIdData;
    }

    public Map<String, String> getChannelToUserIdData() {
        return channelToUserIdData;
    }
}
