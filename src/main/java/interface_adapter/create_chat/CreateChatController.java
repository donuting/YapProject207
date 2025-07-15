package interface_adapter.create_chat;

import use_case.create_chat.CreateChatInputBoundary;
import use_case.create_chat.CreateChatInputData;
import entity.User;

/**
 * Controller for the Create Chat Use Case.
 */
public class CreateChatController {

    private final CreateChatInputBoundary createChatUseCaseInteractor;

    public CreateChatController(CreateChatInputBoundary createChatUseCaseInteractor) {
        this.createChatUseCaseInteractor = createChatUseCaseInteractor;
    }
    /**
     * Executes the Create Chat Case.
     * @param user the User creating the chat
     */
    public void execute(User user) {
        final CreateChatInputData createChatInputData = new CreateChatInputData(user);

        createChatUseCaseInteractor.execute(createChatInputData);
    }

    /**
     * Executes the "switch to ChatView" Use Case.
     */
    public void switchToChatView() {
        createChatUseCaseInteractor.switchToChatView();
    }
}
