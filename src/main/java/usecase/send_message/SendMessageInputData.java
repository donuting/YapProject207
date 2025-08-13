package usecase.send_message;

/**
 * The input data for the Send message Use Case.
 */
public class SendMessageInputData {
    private final String text;

    public SendMessageInputData(String text) {
        this.text = text;
    }

    /**
     * Gets the message body.
     *
     * @return the message body.
     */
    public String getText() {
        return text;
    }
}
