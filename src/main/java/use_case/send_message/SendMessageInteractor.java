package use_case.send_message;

import entity.Message;
import entity.MessageFactory;

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
        final Message message = messageFactory.create(sendMessageInputData.getSender(),
                                            sendMessageInputData.getText());
        boolean result = userDataAccessObject.sendMessage(message, sendMessageInputData.getChat());

        if (result) {
            final SendMessageOutputData sendMessageOutputData = new SendMessageOutputData(sendMessageInputData.getSender(), false,
                                                                                            message, sendMessageInputData.getChat());
            messagePresenter.prepareSuccessSendMessageView(sendMessageOutputData);
        }
        else {
            final SendMessageOutputData sendMessageOutputData = new SendMessageOutputData(sendMessageInputData.getSender(), true,
                                                                                        message, sendMessageInputData.getChat());
            messagePresenter.prepareFailSendMessageView("Send Message Failed", sendMessageOutputData);
        }


    }
}
