package usecase.leave_chat;

/**
 * Output Data for the Leave Chat use case.
 */
public class LeaveChatOutputData {
    private final String channelUrl;

    public LeaveChatOutputData(String channelUrl) {
        this.channelUrl = channelUrl;
    }

    public String getChannelUrl() {
        return channelUrl;
    }
}
