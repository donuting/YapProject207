package use_case.delete_message;

import entity.Chat;
import entity.Message;
import entity.User;

/**
 * Output Data for the delete message Use Case.
 */
public class DeleteMessageOutputData {
    private final String MID;
    private final Chat chat;
    private final boolean useCaseFailed;

    public DeleteMessageOutputData(String MID, Chat chat, boolean useCaseFailed) {
        this.MID = MID;
        this.useCaseFailed = useCaseFailed;
        this.chat = chat;
    }

    public String getMID() {return MID;}
    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
    public Chat getChat() {return chat;}
}
