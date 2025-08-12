package usecase.leave_chat;

/**
 * Output Data for the Leave Chat use case.
 */
public class LeaveChatOutputData {
    private final String channelUrl;

    public LeaveChatOutputData(String channelUrl) {
        this.channelUrl = channelUrl;
    }

    /**
     * Gets the channel URL outputted by the interactor.
     * @return the channel URL
     */
    public String getChannelUrl() {
        return channelUrl;
    }
}
