package interfaceadapter.self_chat;

import java.util.Map;

import com.google.gson.JsonObject;
import interfaceadapter.ViewModel;

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
        // Explicitly fire property change to ensure view updates
        firePropertyChanged();
    }

    /**
     * Description.
     */
    public void clearMessages() {
        final SelfChatState currentState = getState();
        final SelfChatState newState = new SelfChatState(currentState);
        newState.clearMessages();
        newState.setErrorMessage(""); // Clear any error messages too
        setState(newState);
        // Explicitly fire property change to ensure view updates
        firePropertyChanged();
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
        // Explicitly fire property change to ensure view updates
        firePropertyChanged();
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
        // Explicitly fire property change to ensure view updates
        firePropertyChanged();
    }
}