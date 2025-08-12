package usecase.update_chat;

/**
 * The input data for the Update Chat Use Case.
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
