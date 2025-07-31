package interface_adapter.add_chat;

import interface_adapter.ViewManagerModel;
import use_case.create_chat.CreateChatInputBoundary;

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
     */
    public void execute(String chatName, String ID) {
        final use_case.create_chat.CreateChatInputData addChatInputData = new use_case.create_chat.CreateChatInputData(chatName, ID);

        addChatUseCaseInteractor.execute(addChatInputData);
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