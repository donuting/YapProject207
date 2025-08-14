package usecase.leave_chat;

/**
 * Input data for the Leave Chat use case.
 */
public class LeaveChatInputData {
    private String channelUrl;

    public LeaveChatInputData(String channelUrl) {
        this.channelUrl = channelUrl;
    }

    /**
     * Gets the inputted channel URL.
     *
     * @return the inputted channel URL.
     */
    public String getChannelUrl() {
        return channelUrl;
    }
}
