package usecase.update_chat;

/**
 * The input data for the Update Chat Use Case.
 */
public class UpdateChatInputData {
    private final String channelUrl;

    public UpdateChatInputData(String channelUrl) {
        this.channelUrl = channelUrl;
    }

    /**
     * Gets the inputted channel URL.
     *
     * @return the inputted channel URL
     */
    public String getChannelUrl() {
        return channelUrl;
    }
}
