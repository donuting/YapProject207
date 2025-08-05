package use_case.create_chat;

/**
 * The input data for the Create Chat Use Case.
 */
public class CreateChatInputData {

    private final String chatName;

    public CreateChatInputData(String chatName) {
        this.chatName = chatName;
    }

    String getChatName() {
        return chatName;
    }
}
