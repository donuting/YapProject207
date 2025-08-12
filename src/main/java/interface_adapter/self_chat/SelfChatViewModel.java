package interface_adapter.self_chat;

import java.util.Map;

import com.google.gson.JsonObject;
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

    /**
     * Description.
     * @param messageData message data
     */
    public void addMessages(Map<Integer, JsonObject> messageData) {
        final SelfChatState currentState = getState();
        final SelfChatState newState = new SelfChatState(currentState);
        newState.addMessageData(messageData);
        setState(newState);
    }

    /**
     * Description.
     */
    public void clearMessages() {
        final SelfChatState currentState = getState();
        final SelfChatState newState = new SelfChatState(currentState);
        newState.clearMessages();
        setState(newState);
    }

    /**
     * Description.
     * @param errorMessage error message
     */
    public void setErrorMessage(String errorMessage) {
        final SelfChatState currentState = getState();
        final SelfChatState newState = new SelfChatState(currentState);
        newState.setErrorMessage(errorMessage);
        setState(newState);
    }

    /**
     * Description.
     * @param username username
     */
    public void setUsername(String username) {
        final SelfChatState currentState = getState();
        final SelfChatState newState = new SelfChatState(currentState);
        newState.setUsername(username);
        setState(newState);
    }
}
