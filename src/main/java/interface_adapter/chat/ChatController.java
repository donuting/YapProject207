package interface_adapter.chat;

import interface_adapter.ViewManagerModel;
import interface_adapter.view_chats.ViewChatsViewModel;
import use_case.delete_message.DeleteMessageInputBoundary;
import use_case.delete_message.DeleteMessageInputData;
import use_case.send_message.SendMessageInputBoundary;
import use_case.send_message.SendMessageInputData;
import use_case.update_chat.UpdateChatInputBoundary;

/**
 * The controller for the Chat Use Case.
 */
public class ChatController {

    private final SendMessageInputBoundary sendMessageUseCaseInteractor;
    private final DeleteMessageInputBoundary deleteMessageUseCaseInteractor;
    private final UpdateChatInputBoundary updateChatInputUseCaseInteractor;
    private final ViewManagerModel viewManagerModel;
    private final ViewChatsViewModel viewChatsViewModel;

    public ChatController(SendMessageInputBoundary sendMessageUseCaseInteractor, DeleteMessageInputBoundary deleteMessageUseCaseInteractor, UpdateChatInputBoundary updateChatInputUseCaseInteractor,
                          ViewManagerModel viewManagerModel,
                          ViewChatsViewModel viewChatsViewModel) {
        this.sendMessageUseCaseInteractor = sendMessageUseCaseInteractor;
        this.deleteMessageUseCaseInteractor = deleteMessageUseCaseInteractor;
        this.updateChatInputUseCaseInteractor = updateChatInputUseCaseInteractor;
        this.viewManagerModel = viewManagerModel;
        this.viewChatsViewModel = viewChatsViewModel;
    }

    /**
     * Executes the "send message" Use Case.
     * @param text the body of the message.
     */
    public void sendMessage(String text) {
        final SendMessageInputData sendMessageInputData = new SendMessageInputData(text);
        sendMessageUseCaseInteractor.execute(sendMessageInputData);
    }

    /**
     * Executes the "switch to ViewChats" Use Case.
     */
    public void switchToViewChatsView() {
        updateChatInputUseCaseInteractor.leaveChatView();
    }

    /**
     * Executes the "delete message" Use Case.
     */
    public void deleteMessage(Integer messageId) {
        DeleteMessageInputData deleteMessageInputData = new DeleteMessageInputData(messageId.toString());
        deleteMessageUseCaseInteractor.execute(deleteMessageInputData);
    }

    public void updateChat() {
        updateChatInputUseCaseInteractor.execute();
    }
}