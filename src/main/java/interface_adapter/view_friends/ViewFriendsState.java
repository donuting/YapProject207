package interface_adapter.view_friends;

import java.util.HashMap;
import java.util.Map;

public class ViewFriendsState {
    private String username = "";

    // A map where the keys are channel URLs of personal chats
    // and the values are the corresponding IDs.
    private Map<String, String> channelToUserIdData = new HashMap<>();
    private String errorMessage = "";

    // Records whether the friend info needs to be updated
    private boolean needsFriendInfo = false;

    public ViewFriendsState(ViewFriendsState copy) {
        username = copy.username;
        channelToUserIdData = copy.channelToUserIdData;
        errorMessage = copy.errorMessage;
        needsFriendInfo = copy.needsFriendInfo;
    }

    public ViewFriendsState() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Map<String, String> getChannelToUserIdData() {
        return channelToUserIdData;
    }

    public void setChannelToUserIdData(Map<String, String> channelToUserIdData) {
        this.channelToUserIdData = channelToUserIdData;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean getNeedsFriendInfo() {
        return needsFriendInfo;
    }

    public void setNeedsFriendInfo(boolean needsFriendInfo) {
        this.needsFriendInfo = needsFriendInfo;
    }
}
