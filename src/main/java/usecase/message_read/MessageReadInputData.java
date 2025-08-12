package usecase.message_read;

import entity.Chat;
import entity.Message;

public class MessageReadInputData {
    private final String userId;
    private final Message message;
    private final Chat chat;

    public MessageReadInputData(String userId, Message message, Chat chat) {
        this.userId = userId;
        this.message = message;
        this.chat = chat;
    }

    /**
     * Gets the inputted user ID.
     *
     * @return the inputted user ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Gets the inputted message.
     *
     * @return the inputted message
     */
    public Message getMessage() {
        return message;
    }

    /**
     * Gets the inputted chat.
     *
     * @return the inputted chat
     */
    public Chat getChat() {
        return chat;
    }
}
