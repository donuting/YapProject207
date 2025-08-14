package usecase.join_chat;

/**
 * Input data for the Join Chat use case.
 */
public class JoinChatInputData {
    private final String channelUrl;
    private final String username;

    public JoinChatInputData(String channelUrl, String username) {
        this.channelUrl = channelUrl;
        this.username = username;
    }

    public String getChannelUrl() {
        return channelUrl;
    }

    public String getUsername() {
        return username;
    }
}
