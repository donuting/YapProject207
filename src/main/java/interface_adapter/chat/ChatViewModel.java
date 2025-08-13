package interface_adapter.chat;

import java.util.ArrayList;
import java.util.List;

import entity.Message;
import interface_adapter.ViewModel;

/**
 * The ViewModel for the Chat View.
 */
public class ChatViewModel extends ViewModel<ChatState> {

    public static final String TITLE_LABEL = "Chat";
    public static final String SEND_BUTTON_LABEL = "Send";
    public static final String MESSAGE_INPUT_LABEL = "Type here...";
    public static final String BACK_BUTTON_LABEL = "Back";
    public static final String CHAT_PROFILE_BUTTON_LABEL = "Chat Profile";
    public static final String ADD_MEMBER_BUTTON_LABEL = "Add Member";

    public ChatViewModel() {
        super("chat");
        setState(new ChatState());
    }

    /**
     * Adds a new message to the current chat.
     * @param message the message to add
     */
    public void addMessage(Message message) {
        final ChatState currentState = getState();
        final List<Message> messages = currentState.getMessages();
        final List<Message> messagesSentByUser = currentState.getMessagesSentByUser();
        messages.add(message);
        messagesSentByUser.add(message);
        currentState.setMessages(messages);
        currentState.setMessagesSentByUser(messagesSentByUser);
        firePropertyChanged();
    }

    /**
     * Clears the message input field.
     */
    public void clearMessageInput() {
        final ChatState currentState = getState();
        currentState.setCurrentMessage("");
        firePropertyChanged();
    }

    /**
     * Removes a message from the current chat.
     * @param messageId the ID of the message to remove
     */
    public void deleteMessage(String messageId) {
        final ChatState currentState = getState();
        final List<Message> messages = currentState.getMessages();
        final List<Message> messagesSentByUser = currentState.getMessagesSentByUser();
        for (Message message : messages) {
            if (message.GetMID().toString().equals(messageId)) {
                messagesSentByUser.remove(message);
            }
        }
        for (Message message : messagesSentByUser) {
            if (message.GetMID().toString().equals(messageId)) {
                messagesSentByUser.remove(message);
            }
        }
        currentState.setMessages(messages);
        currentState.setMessagesSentByUser(messagesSentByUser);
        currentState.setNeedsClearChat(true);
        firePropertyChanged();
    }

    /**
     * Description.
     * @param currentUserId user id of current user
     * @param messages list of messages
     * @param usernames list of usernames
     */
    public void setMessagesAndUsernames(String currentUserId, List<Message> messages, List<String> usernames) {
        final ChatState currentState = getState();
        currentState.setMessages(messages);
        currentState.setUsernames(usernames);
        currentState.setNeedsUpdate(false);
        final List<Message> messagesSentByUser = new ArrayList<>();
        for (Message message : messages) {
            if (message.GetSenderId().equals(currentUserId)) {
                messagesSentByUser.add(message);
            }
        }
        currentState.setMessagesSentByUser(messagesSentByUser);
        firePropertyChanged();
    }
}
