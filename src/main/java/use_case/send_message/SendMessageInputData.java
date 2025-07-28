package use_case.send_message;

import entity.Chat;
import entity.User;

/**
 * The input data for the Send message Use Case.
 */
public class SendMessageInputData {
    private final User sender;
    private final String text;
    private final Chat chat;

    public SendMessageInputData(User sender, String text, Chat chat) {
        this.sender = sender;
        this.text = text;
        this.chat = chat;
    }

    public User getSender() {
        return sender;
    }

    public String getText() {
        return text;
    }

    public Chat getChat() {
        return chat;
    }
}
