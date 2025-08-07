package data_access;

import entity.GroupChat;
import entity.Message;
import entity.User;
import use_case.self_chat.SelfChatUserDataAccessInterface;

import java.util.List;

/**
 * In-memory implementation of the SelfChatDataAccessInterface.
 * This stores messages in memory and will be lost when the application closes.
 */
public class InMemorySelfChatUserDataAccessObject implements SelfChatUserDataAccessInterface {

    private final SendBirdUserDataAccessObject sendBirdUserDataAccessObject = new SendBirdUserDataAccessObject();
    private final MessageDataAccessObject messageDataAccessObject = new MessageDataAccessObject();

    /**
     * Sends a message in a user's self chat.
     *
     * @return the message sent
     */
    @Override
    public Message sendMessage(Message message) {
        if (message.GetText() == null) {
            throw new IllegalArgumentException("Message and timestamp cannot be null");
        }
        GroupChat selfChat = sendBirdUserDataAccessObject.getCurrentSelfChat();
        return messageDataAccessObject.sendMessage(message, selfChat);
    }

    /**
     * Gets all saved messages.
     *
     * @return list of all messages
     */
    @Override
    public List<Message> loadMessages() {
        GroupChat selfChat = sendBirdUserDataAccessObject.getCurrentSelfChat();
        messageDataAccessObject.loadMessages(selfChat);
        return selfChat.getMessageHistory();
    }

    /**
     * Clears all messages in the self chat.
     */
    @Override
    public void clearAllMessages() {
        GroupChat selfChat = sendBirdUserDataAccessObject.getCurrentSelfChat();
        for (Message message : selfChat.getMessageHistory()) {
            messageDataAccessObject.deleteMessage(message.GetMID().toString(), selfChat);
        }
    }

    /**
     * Returns the current user.
     *
     * @return the current user.
     */
    @Override
    public User getCurrentUser() {
        return sendBirdUserDataAccessObject.getCurrentUser();
    }
}