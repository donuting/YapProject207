package use_case.update_chat;

/**
 * Input data for the Update Chat use case.
 */
public class UpdateChatInputData {
    private final String channelUrl;

    public UpdateChatInputData(String channelUrl) {
        this.channelUrl = channelUrl;
    }

    public String getChannelUrl() {
        return channelUrl;
    }
}
