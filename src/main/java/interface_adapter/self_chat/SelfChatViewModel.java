package interface_adapter.self_chat;

import interface_adapter.ViewModel;

/**
 * The View Model for the Self Chat View.
 */
public class SelfChatViewModel extends ViewModel<SelfChatState> {

    public static final String TITLE_LABEL = "Self Chat";
    public static final String SEND_BUTTON_LABEL = "Send";
    public static final String BACK_BUTTON_LABEL = "Back";
    public static final String CLEAR_BUTTON_LABEL = "Clear Chat";

    public SelfChatViewModel() {
        super("self chat");
        setState(new SelfChatState());
    }

    public void addMessage(String message) {
        SelfChatState currentState = getState();
        SelfChatState newState = new SelfChatState(currentState);
        newState.addMessage(message, java.time.LocalDateTime.now());
        setState(newState);
    }

    public void clearMessages() {
        SelfChatState currentState = getState();
        SelfChatState newState = new SelfChatState(currentState);
        newState.clearMessages();
        setState(newState);
    }

    public void setErrorMessage(String errorMessage) {
        SelfChatState currentState = getState();
        SelfChatState newState = new SelfChatState(currentState);
        newState.setErrorMessage(errorMessage);
        setState(newState);
    }

    public void setUsername(String username) {
        SelfChatState currentState = getState();
        SelfChatState newState = new SelfChatState(currentState);
        newState.setUsername(username);
        setState(newState);
    }
}