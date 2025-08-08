package use_case.leave_chat;

/**
 * Input data for the Leave Chat use case.
 */
public class LeaveChatInputData {
    private String channelUrl;

    public LeaveChatInputData(String channelUrl) {
        this.channelUrl = channelUrl;
    }

    public String getChannelUrl() {
        return channelUrl;
    }
}
