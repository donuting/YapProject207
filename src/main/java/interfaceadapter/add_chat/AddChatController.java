package interfaceadapter.add_chat;

import interfaceadapter.ViewManagerModel;
import usecase.create_chat.CreateChatInputBoundary;

/**
 * The controller for the Add Chat View.
 */
public class AddChatController {
    private final CreateChatInputBoundary addChatUseCaseInteractor;
    private final ViewManagerModel viewManagerModel;

    public AddChatController(CreateChatInputBoundary addChatUseCaseInteractor, ViewManagerModel viewManagerModel) {
        this.addChatUseCaseInteractor = addChatUseCaseInteractor;
        this.viewManagerModel = viewManagerModel;
    }

    /**
     * Executes the Add Chat Use Case.
     * @param chatName the name of the chat to create
     * @param username the username of the user to add to the chat (can be empty)
     */
    public void execute(String chatName, String username) {
        final usecase.create_chat.CreateChatInputData addChatInputData =
                new usecase.create_chat.CreateChatInputData(chatName, username);

        addChatUseCaseInteractor.execute(addChatInputData);
    }

    /**
     * Backwards compatibility method - creates chat with just chat name.
     * @param chatName the name of the chat to create
     */
    public void execute(String chatName) {
        execute(chatName, "");
        // Call the new method with empty username
    }

    /**
     * Executes the back to view chat use case.
     */
    public void backToViewChat() {
        // Navigate back to view chats screen
        viewManagerModel.setState("view chats");
        viewManagerModel.firePropertyChanged();
    }
}
