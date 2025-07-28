package use_case.send_message;

import entity.Chat;
import entity.Message;
import entity.User;

/**
 * Output Data for the send message Use Case.
 */
public class SendMessageOutputData {
    private final User user;
    private final Message message;
    private final Chat chat;
    private final boolean useCaseFailed;

    public SendMessageOutputData(User user, boolean useCaseFailed,
                                 Message message, Chat chat) {
        this.user = user;
        this.useCaseFailed = useCaseFailed;
        this.message = message;
        this.chat = chat;
    }

    public User getUser() {
        return user;
    }
    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
    public Message getMessage() {
        return message;
    }
    public Chat getChat() {return chat;}
}
