package interfaceadapter.chat;

import interfaceadapter.ViewManagerModel;
import interfaceadapter.view_chats.ViewChatsViewModel;
import usecase.delete_message.DeleteMessageInputBoundary;
import usecase.delete_message.DeleteMessageInputData;
import usecase.join_chat.JoinChatInputBoundary;
import usecase.join_chat.JoinChatInputData;
import usecase.send_message.SendMessageInputBoundary;
import usecase.send_message.SendMessageInputData;
import usecase.update_chat.UpdateChatInputBoundary;
import usecase.update_chat.UpdateChatInputData;

/**
 * The controller for the Chat Use Case.
 */
public class ChatController {

    private final SendMessageInputBoundary sendMessageUseCaseInteractor;
    private final DeleteMessageInputBoundary deleteMessageUseCaseInteractor;
    private final UpdateChatInputBoundary updateChatInputUseCaseInteractor;
    private final JoinChatInputBoundary joinChatInputUseCaseInteractor;
    private final ViewManagerModel viewManagerModel;
    private final ViewChatsViewModel viewChatsViewModel;

    public ChatController(SendMessageInputBoundary sendMessageUseCaseInteractor,
                          DeleteMessageInputBoundary deleteMessageUseCaseInteractor,
                          UpdateChatInputBoundary updateChatInputUseCaseInteractor,
                          JoinChatInputBoundary joinChatInputUseCaseInteractor,
                          ViewManagerModel viewManagerModel,
                          ViewChatsViewModel viewChatsViewModel) {
        this.sendMessageUseCaseInteractor = sendMessageUseCaseInteractor;
        this.deleteMessageUseCaseInteractor = deleteMessageUseCaseInteractor;
        this.updateChatInputUseCaseInteractor = updateChatInputUseCaseInteractor;
        this.joinChatInputUseCaseInteractor = joinChatInputUseCaseInteractor;
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
    public void switchToMainMenu() {
        updateChatInputUseCaseInteractor.leaveChatView();
    }

    /**
     * Executes the "delete message" Use Case.
     * @param messageId message id
     */
    public void deleteMessage(Integer messageId) {
        final DeleteMessageInputData deleteMessageInputData = new DeleteMessageInputData(messageId.toString());
        deleteMessageUseCaseInteractor.execute(deleteMessageInputData);
    }

    /**
     * Executes the "update chat" Use Case.
     * @param channelUrl channel url
     */
    public void updateChat(String channelUrl) {
        final UpdateChatInputData updateChatInputData = new UpdateChatInputData(channelUrl);
        updateChatInputUseCaseInteractor.execute(updateChatInputData);
    }

    /**
     * Executes the "join chat" Use Case.
     * @param channelUrl channel url
     * @param newUsername username
     */
    public void joinChat(String channelUrl, String newUsername) {
        final JoinChatInputData joinChatInputData = new JoinChatInputData(channelUrl, newUsername);
        joinChatInputUseCaseInteractor.execute(joinChatInputData);
    }
}
