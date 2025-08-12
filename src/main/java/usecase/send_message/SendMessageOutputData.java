package usecase.send_message;

import entity.Message;

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

    /**
     * Returns whether the use case failed.
     * @return true if unsuccessful
     */
    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }

    /**
     * Gets the message outputted by the interactor.
     * @return the message
     */
    public Message getMessage() {
        return message;
    }
}
