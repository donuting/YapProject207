package interface_adapter.chat;

import interface_adapter.ViewManagerModel;
import use_case.delete_message.DeleteMessageOutputBoundary;
import use_case.delete_message.DeleteMessageOutputData;
import use_case.send_message.SendMessageOutputBoundary;
import use_case.send_message.SendMessageOutputData;
import use_case.update_chat.UpdateChatOutputBoundary;
import use_case.update_chat.UpdateChatOutputData;

import java.util.ArrayList;

/**
 * The Presenter for the Chat Use Case.
 */
public class ChatPresenter implements SendMessageOutputBoundary, DeleteMessageOutputBoundary, UpdateChatOutputBoundary {

    private final ViewManagerModel viewManagerModel;
    private final ChatViewModel chatViewModel;

    public ChatPresenter(ViewManagerModel viewManagerModel, ChatViewModel chatViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.chatViewModel = chatViewModel;
    }

    @Override
    public void prepareSuccessSendMessageView(SendMessageOutputData response) {
        // On success, add the message to the chat view
        chatViewModel.addMessage(response.getMessage());
        chatViewModel.clearMessageInput();
    }

    @Override
    public void prepareFailSendMessageView(String error, SendMessageOutputData response) {
        ChatState chatState = chatViewModel.getState();
        chatState.setError(error);
        chatViewModel.firePropertyChanged();
    }

    /**
     * Prepares the success view for the delete message Use Case.
     *
     * @param deleteMessageOutputData the output data.
     */
    @Override
    public void prepareSuccessDeleteMessageView(DeleteMessageOutputData deleteMessageOutputData) {
        chatViewModel.deleteMessage(deleteMessageOutputData.getMessageId());
    }

    /**
     * Prepares the fail view for the delete message Use Case.
     *
     * @param error            The error message to be shown.
     * @param deleteMessageOutputData the output data.
     */
    @Override
    public void prepareFailDeleteMessageView(String error, DeleteMessageOutputData deleteMessageOutputData) {
        ChatState chatState = chatViewModel.getState();
        chatState.setError(error);
        chatViewModel.firePropertyChanged();
    }

    @Override
    public void leaveChatView() {
        // Reset state objects
        ChatState chatState = chatViewModel.getState();
        chatState.setCurrentChannelUrl(null);
        chatState.setUsernames(new ArrayList<>());
        chatState.setMessages(new ArrayList<>());
        chatState.setMessagesSentByUser(new ArrayList<>());
        chatState.setChatName(null);
        chatViewModel.firePropertyChanged();

        viewManagerModel.setState("view chats");
        viewManagerModel.firePropertyChanged();
    }

    /**
     * Prepare the success view for the Update Chat use case.
     *
     * @param outputData Output data.
     */
    @Override
    public void updateChatPrepareSuccessView(UpdateChatOutputData outputData) {
        chatViewModel.setMessagesAndUsernames(outputData.getCurrentUserId(), outputData.getMessages(), outputData.getUsernames());
    }

    /**
     * Prepare the fail view for the Update Chat use case.
     *
     * @param error Explanation of failure.
     * @param outputData   Output data.
     */
    @Override
    public void updateChatPrepareFailView(String error, UpdateChatOutputData outputData) {
        ChatState chatState = chatViewModel.getState();
        chatState.setError(error);
        chatState.setNeedsUpdate(false);
        chatViewModel.firePropertyChanged();
    }
}