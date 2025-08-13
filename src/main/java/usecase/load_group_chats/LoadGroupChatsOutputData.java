package usecase.load_group_chats;

import java.util.Map;

/**
 * Output Data for the Load Group Chat use case.
 */
public class LoadGroupChatsOutputData {
    private final Map<String, String> channelInfo;

    public LoadGroupChatsOutputData(Map<String, String> channelInfo) {
        this.channelInfo = channelInfo;
    }

    /**
     * Gets the channel info outputted by the interactor.
     * This is a map where the keys are channel URLs and the values are the corresponding channel name.
     * @return the channel info
     */
    public Map<String, String> getChannelInfo() {
        return channelInfo;
    }
}
