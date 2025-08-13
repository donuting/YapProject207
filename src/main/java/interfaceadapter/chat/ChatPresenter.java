package interfaceadapter.chat;

import java.util.ArrayList;

import interfaceadapter.ViewManagerModel;
import usecase.delete_message.DeleteMessageOutputBoundary;
import usecase.delete_message.DeleteMessageOutputData;
import usecase.join_chat.JoinChatOutputBoundary;
import usecase.join_chat.JoinChatOutputData;
import usecase.send_message.SendMessageOutputBoundary;
import usecase.send_message.SendMessageOutputData;
import usecase.update_chat.UpdateChatOutputBoundary;
import usecase.update_chat.UpdateChatOutputData;

/**
 * The Presenter for the Chat Use Case.
 */
public class ChatPresenter implements SendMessageOutputBoundary, DeleteMessageOutputBoundary,
        UpdateChatOutputBoundary, JoinChatOutputBoundary {

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
        final ChatState chatState = chatViewModel.getState();
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
        final ChatState chatState = chatViewModel.getState();
        chatState.setError(error);
        chatViewModel.firePropertyChanged();
    }

    @Override
    public void leaveChatView() {
        // Reset state objects
        final ChatState chatState = chatViewModel.getState();
        chatState.setCurrentChannelUrl(null);
        chatState.setUsernames(new ArrayList<>());
        chatState.setMessages(new ArrayList<>());
        chatState.setMessagesSentByUser(new ArrayList<>());
        chatState.setChatName(null);
        chatState.setGroupChat(false);
        chatViewModel.firePropertyChanged();

        viewManagerModel.setState("main menu");
        viewManagerModel.firePropertyChanged();
    }

    /**
     * Prepare the success view for the Update Chat use case.
     *
     * @param outputData Output data.
     */
    @Override
    public void updateChatPrepareSuccessView(UpdateChatOutputData outputData) {
        chatViewModel.setMessagesAndUsernames(outputData.getCurrentUserId(),
                outputData.getMessages(), outputData.getUsernames());
    }

    /**
     * Prepare the fail view for the Update Chat use case.
     *
     * @param error Explanation of failure.
     * @param outputData   Output data.
     */
    @Override
    public void updateChatPrepareFailView(String error, UpdateChatOutputData outputData) {
        final ChatState chatState = chatViewModel.getState();
        chatState.setError(error);
        chatState.setNeedsUpdate(false);
        chatViewModel.firePropertyChanged();
    }

    /**
     * Prepare the success view for the Update Chat use case.
     *
     * @param outputData Output data.
     */
    @Override
    public void joinChatPrepareSuccessView(JoinChatOutputData outputData) {
        final ChatState chatState = chatViewModel.getState();
        chatState.setNeedsClearChat(true);
        chatViewModel.firePropertyChanged();
    }

    /**
     * Prepare the fail view for the Update Chat use case.
     *
     * @param error Explanation of failure.
     * @param outputData   Output data.
     */
    @Override
    public void joinChatPrepareFailView(String error, JoinChatOutputData outputData) {
        final ChatState chatState = chatViewModel.getState();
        chatState.setError(error);
        chatState.setNeedsUpdate(false);
        chatViewModel.firePropertyChanged();
    }
}
