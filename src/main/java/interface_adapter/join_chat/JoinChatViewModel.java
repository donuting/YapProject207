package interface_adapter.join_chat;

import interface_adapter.ViewModel;

public class JoinChatViewModel extends ViewModel<JoinChatState> {

    public static final String TITLE = "Join Chat";
    public static final String CHANNEL_URL = "channel_url";
    public static final String JOIN_BUTTON = "Join Chat";
    public static final String CANCEL_BUTTON = "Cancel";

    public JoinChatViewModel(String viewName) {
        super(viewName);
        setState(new JoinChatState());
    }

    public void setErrorMessage(String errorMessage) {
        JoinChatState currentState = getState();
        JoinChatState newState = new JoinChatState(currentState);
        newState.setErrorMessage(errorMessage);
        setState(newState);

    }
}
