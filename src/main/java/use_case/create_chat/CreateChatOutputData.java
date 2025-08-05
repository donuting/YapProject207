package use_case.create_chat;

/**
 * Output Data for the Create Chat Case.
 */
public class CreateChatOutputData {

    private final String chatName;
    private final String userId;

    public CreateChatOutputData(String chatName, String userId) {
        this.chatName = chatName;
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public String getChatName() {
        return chatName;
    }
}
