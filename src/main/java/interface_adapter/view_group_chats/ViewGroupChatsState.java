package interface_adapter.view_group_chats;

import java.util.HashMap;
import java.util.Map;

public class ViewGroupChatsState {
    private String username = "";

    // A map where the keys are channel URLs and the values are the corresponding channel name.
    private Map<String, String> channelInfo = new HashMap<>();
    private String errorMessage = "";

    // Records whether the channel info needs to be updated
    private boolean needsGroupChatInfo;

    public ViewGroupChatsState(ViewGroupChatsState copy) {
        username = copy.username;
        channelInfo = copy.channelInfo;
        errorMessage = copy.errorMessage;
        needsGroupChatInfo = copy.needsGroupChatInfo;
    }

    public ViewGroupChatsState() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Map<String, String> getChannelInfo() {
        return channelInfo;
    }

    public void setChannelInfo(Map<String, String> channelInfo) {
        this.channelInfo = channelInfo;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean getNeedsGroupChatInfo() {
        return needsGroupChatInfo;
    }

    public void setNeedsGroupChatInfo(boolean needsGroupChatInfo) {
        this.needsGroupChatInfo = needsGroupChatInfo;
    }
}
