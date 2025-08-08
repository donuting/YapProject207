package interface_adapter.chat;

import interface_adapter.ViewModel;
import entity.GroupChat;
import entity.Message;
import java.util.List;
import java.util.ArrayList;

/**
 * The ViewModel for the Chat View.
 */
public class ChatViewModel extends ViewModel<ChatState> {

    public static final String TITLE_LABEL = "Chat";
    public static final String SEND_BUTTON_LABEL = "Send";
    public static final String MESSAGE_INPUT_LABEL = "Type here...";
    public static final String BACK_BUTTON_LABEL = "Back";
    public static final String CHAT_PROFILE_BUTTON_LABEL = "Chat Profile";

    public ChatViewModel() {
        super("chat");
        setState(new ChatState());
    }

    /**
     * Sets the current chat being viewed.
     * @param chat the GroupChat object
     */
    public void setCurrentChat(GroupChat chat) {
        ChatState currentState = getState();
        currentState.setCurrentChat(chat);
        currentState.setChatName(chat.getName());
        currentState.setMessages(chat.getMessageHistory());
        firePropertyChanged();
    }

    /**
     * Adds a new message to the current chat.
     * @param message the message to add
     */
    public void addMessage(Message message) {
        ChatState currentState = getState();
        List<Message> messages = new ArrayList<>(currentState.getMessages());
        messages.add(message);
        currentState.setMessages(messages);
        firePropertyChanged();
    }

    /**
     * Clears the message input field.
     */
    public void clearMessageInput() {
        ChatState currentState = getState();
        currentState.setCurrentMessage("");
        firePropertyChanged();
    }
}