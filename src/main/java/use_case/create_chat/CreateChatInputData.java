package use_case.create_chat;

import entity.User;

/**
 * The input data for the Create Chat Use Case.
 */
public class CreateChatInputData {

    private final String chatName;
    private final String ID;

    public CreateChatInputData(String chatName, String ID) {
        this.chatName = chatName;
        this.ID = ID;
    }

    String getChatName() {
        return chatName;
    }

    String getID() {
        return ID;
    }
}
