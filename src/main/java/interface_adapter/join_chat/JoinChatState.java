package interface_adapter.join_chat;

public class JoinChatState {
    String channelUrl;
    String errorMessage;

    public JoinChatState() {

    }

    public JoinChatState(JoinChatState copy) {
        this.channelUrl = copy.channelUrl;
        this.errorMessage = copy.errorMessage;
    }

    public String getChannelUrl() {
        return channelUrl;
    }

    public void setChannelUrl(String channelUrl) {
        this.channelUrl = channelUrl;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String error) {
        this.errorMessage = error;
    }
}
