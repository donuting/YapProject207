package use_case.load_group_chats;

import java.util.Map;

/**
 * Output Data for the Load Group Chat use case.
 */
public class LoadGroupChatsOutputData {
    private final Map<String, String> channelInfo;

    public LoadGroupChatsOutputData(Map<String, String> channelInfo) {
        this.channelInfo = channelInfo;
    }

    public Map<String, String> getChannelInfo() {
        return channelInfo;
    }
}
