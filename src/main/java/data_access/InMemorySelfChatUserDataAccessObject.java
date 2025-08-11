package data_access;

import entity.GroupChat;
import entity.Message;
import entity.User;
import entity.CommonMessageFactory;
import entity.MessageFactory;
import use_case.self_chat.SelfChatUserDataAccessInterface;

import java.util.List;
import java.util.ArrayList;

/**
 * In-memory implementation of the SelfChatDataAccessInterface.
 * This stores messages in memory and will be lost when the application closes.
 */
public class InMemorySelfChatUserDataAccessObject implements SelfChatUserDataAccessInterface {

    private final SendBirdUserDataAccessObject sendBirdUserDataAccessObject;
    private final MessageDataAccessObject messageDataAccessObject = new MessageDataAccessObject();
    private final MessageFactory messageFactory = new CommonMessageFactory();

    // In-memory storage for messages as fallback
    private final List<Message> inMemoryMessages = new ArrayList<>();

    /**
     * Constructor that takes the shared SendBirdUserDataAccessObject instance.
     */
    public InMemorySelfChatUserDataAccessObject(SendBirdUserDataAccessObject sendBirdUserDataAccessObject) {
        this.sendBirdUserDataAccessObject = sendBirdUserDataAccessObject;
    }

    /**
     * Default constructor for backward compatibility.
     */
    public InMemorySelfChatUserDataAccessObject() {
        this.sendBirdUserDataAccessObject = new SendBirdUserDataAccessObject();
    }

    /**
     * Sends a message in a user's self chat.
     *
     * @return the message sent
     */
    @Override
    public Message sendMessage(Message message) {
        if (message == null || message.GetText() == null) {
            throw new IllegalArgumentException("Message and text cannot be null");
        }

        System.out.println("DEBUG: Sending message: " + message.GetText());

        GroupChat selfChat = sendBirdUserDataAccessObject.getCurrentSelfChat();

        if (selfChat != null) {
            System.out.println("DEBUG: Using SendBird self chat");
            return messageDataAccessObject.sendMessage(message, selfChat);
        } else {
            System.out.println("DEBUG: Using in-memory storage for messages");

            // Create a new message with proper ID and timestamp if needed
            String userId = getCurrentUser() != null ? getCurrentUser().getID() : "self_user_" + System.currentTimeMillis();
            Message newMessage;

            if (message.GetMID() == null) {
                newMessage = messageFactory.create(userId, message.GetText());
            } else {
                newMessage = message;
            }

            inMemoryMessages.add(newMessage);
            System.out.println("DEBUG: Message stored. Total messages: " + inMemoryMessages.size());
            return newMessage;
        }
    }

    /**
     * Gets all saved messages.
     *
     * @return list of all messages
     */
    @Override
    public List<Message> loadMessages() {
        System.out.println("DEBUG: Loading messages...");

        GroupChat selfChat = sendBirdUserDataAccessObject.getCurrentSelfChat();

        if (selfChat != null) {
            System.out.println("DEBUG: Loading from SendBird self chat");
            messageDataAccessObject.loadMessages(selfChat);
            return selfChat.getMessageHistory();
        } else {
            System.out.println("DEBUG: Loading from in-memory storage. Count: " + inMemoryMessages.size());
            return new ArrayList<>(inMemoryMessages);
        }
    }

    /**
     * Clears all messages in the self chat.
     */
    @Override
    public void clearAllMessages() {
        System.out.println("DEBUG: Clearing all messages");

        GroupChat selfChat = sendBirdUserDataAccessObject.getCurrentSelfChat();

        if (selfChat != null) {
            for (Message message : selfChat.getMessageHistory()) {
                messageDataAccessObject.deleteMessage(message.GetMID().toString(), selfChat);
            }
        }

        // Always clear in-memory messages too
        inMemoryMessages.clear();
        System.out.println("DEBUG: Messages cleared");
    }

    /**
     * Returns the current user.
     *
     * @return the current user.
     */
    @Override
    public User getCurrentUser() {
        User user = sendBirdUserDataAccessObject.getCurrentUser();
        System.out.println("DEBUG: Current user: " + (user != null ? user.getName() : "null"));
        return user;
    }
}