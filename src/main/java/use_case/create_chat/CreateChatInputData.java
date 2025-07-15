package use_case.create_chat;

import entity.User;

/**
 * The input data for the Create Chat Use Case.
 */
public class CreateChatInputData {

    private final User user;

    public CreateChatInputData(User user) {
        this.user = user;
    }

    User getUser() {
        return user;
    }
}
