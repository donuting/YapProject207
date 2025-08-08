package interface_adapter.chat;

import use_case.send_message.SendMessageOutputBoundary;
import use_case.send_message.SendMessageOutputData;

/**
 * The Presenter for the Chat Use Case.
 */
public class ChatPresenter implements SendMessageOutputBoundary {

    private final ChatViewModel chatViewModel;

    public ChatPresenter(ChatViewModel chatViewModel) {
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
}