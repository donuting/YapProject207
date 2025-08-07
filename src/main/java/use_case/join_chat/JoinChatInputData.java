package use_case.join_chat;

/**
 * Input data for the Join Chat use case.
 */
public class JoinChatInputData {
    private final String channelUrl;

    public JoinChatInputData(String channelUrl) {
        this.channelUrl = channelUrl;
    }

    public String getChannelUrl() {
        return channelUrl;
    }
}
