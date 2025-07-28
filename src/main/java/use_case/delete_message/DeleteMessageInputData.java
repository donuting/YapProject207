package use_case.delete_message;

import entity.Chat;

/**
 * The input data for the delete message Use Case.
 */
public class DeleteMessageInputData {
    private final String MID;
    private final Chat chat;

    public DeleteMessageInputData(String MID, Chat chat) {
        this.MID = MID;
        this.chat = chat;
    }

    public String getMID() {
        return MID;
    }

    public Chat getChat() {
        return chat;
    }
}
