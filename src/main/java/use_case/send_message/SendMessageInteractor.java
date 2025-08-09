package use_case.send_message;

import entity.Chat;
import entity.Message;
import entity.MessageFactory;
import entity.User;

/**
 * The send message Interactor.
 */
public class SendMessageInteractor implements SendMessageInputBoundary {
    private final SendMessageDataAccessInterface userDataAccessObject;
    private final SendMessageOutputBoundary messagePresenter;
    private final MessageFactory messageFactory;

    public SendMessageInteractor(SendMessageDataAccessInterface userDataAccessObject, SendMessageOutputBoundary userPresenter
                            , MessageFactory messageFactory) {
        this.userDataAccessObject = userDataAccessObject;
        this.messagePresenter = userPresenter;
        this.messageFactory = messageFactory;
    }

    @Override
    public void execute(SendMessageInputData sendMessageInputData) {
        User sender = userDataAccessObject.getCurrentUser();

        // Verify the current user exists
        if (sender == null) {
            final SendMessageOutputData sendMessageOutputData = new SendMessageOutputData(true, null);
            messagePresenter.prepareFailSendMessageView("This user doesn't exist", sendMessageOutputData);
            return;
        }

        final Chat chat = userDataAccessObject.getActiveGroupChat();

        // verify the current chat exists
        if (chat == null) {
            final SendMessageOutputData sendMessageOutputData = new SendMessageOutputData(true, null);
            messagePresenter.prepareFailSendMessageView("This chat doesn't exist", sendMessageOutputData);
            return;
        }

        final Message message = messageFactory.create(sender.getID(),
                sendMessageInputData.getText());
        final Message updatedMessage = userDataAccessObject.sendMessage(message, chat);
        boolean result = (updatedMessage != null);

        if (result) {
            // Update the in memory chat object
            chat.addMessage(updatedMessage);

            final SendMessageOutputData sendMessageOutputData = new SendMessageOutputData(false, updatedMessage);
            messagePresenter.prepareSuccessSendMessageView(sendMessageOutputData);
        }
        else {
            final SendMessageOutputData sendMessageOutputData = new SendMessageOutputData(true, null);
            messagePresenter.prepareFailSendMessageView("Send Message Failed", sendMessageOutputData);
        }
    }
}
