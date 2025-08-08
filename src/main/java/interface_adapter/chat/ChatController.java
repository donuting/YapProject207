package interface_adapter.chat;

import entity.Chat;
import entity.User;
import interface_adapter.ViewManagerModel;
import interface_adapter.view_chats.ViewChatsViewModel;
import use_case.send_message.SendMessageInputBoundary;
import use_case.send_message.SendMessageInputData;

/**
 * The controller for the Chat Use Case.
 */
public class ChatController {

    private final SendMessageInputBoundary sendMessageUseCaseInteractor;
    private final ViewManagerModel viewManagerModel;
    private final ViewChatsViewModel viewChatsViewModel;

    public ChatController(SendMessageInputBoundary sendMessageUseCaseInteractor,
                          ViewManagerModel viewManagerModel,
                          ViewChatsViewModel viewChatsViewModel) {
        this.sendMessageUseCaseInteractor = sendMessageUseCaseInteractor;
        this.viewManagerModel = viewManagerModel;
        this.viewChatsViewModel = viewChatsViewModel;
    }

    /**
     * Executes the "send message" Use Case.
     * @param sender the text of the message to send
     * @param text the ID of the chat to send the message to
     * @param chat the ID of the user sending the message
     */
    public void execute(User sender, String text, Chat chat) {
        final SendMessageInputData sendMessageInputData = new SendMessageInputData(
                sender, text, chat);

        sendMessageUseCaseInteractor.execute(sendMessageInputData);
    }

    /**
     * Executes the "switch to ViewChats" Use Case.
     */
    public void switchToViewChatsView() {
        viewManagerModel.setState("view chats");
        viewManagerModel.firePropertyChanged();
    }
}