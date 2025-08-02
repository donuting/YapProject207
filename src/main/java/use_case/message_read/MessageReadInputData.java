package use_case.message_read;

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

    public String getUserId() {
        return userId;
    }

    public Message getMessage() {
        return message;
    }

    public Chat getChat() {
        return chat;
    }
}