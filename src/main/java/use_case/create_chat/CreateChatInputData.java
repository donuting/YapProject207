package use_case.create_chat;

import entity.User;

/**
 * The input data for the Create Chat Use Case.
 */
public class CreateChatInputData {

    private final User user;
    private final String chatName;

    public CreateChatInputData(User user, String chatName) {
        this.user = user;
        this.chatName = chatName;
    }

    User getUser() {
        return user;
    }

    String getChatName() {
        return chatName;
    }
}
