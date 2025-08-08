package use_case.send_message;

import entity.Chat;
import entity.Message;
import entity.User;

/**
 * Output Data for the send message Use Case.
 */
public class SendMessageOutputData {
    private final Message message;
    private final boolean useCaseFailed;

    public SendMessageOutputData(boolean useCaseFailed,
                                 Message message) {
        this.useCaseFailed = useCaseFailed;
        this.message = message;
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
    public Message getMessage() {
        return message;
    }
}
