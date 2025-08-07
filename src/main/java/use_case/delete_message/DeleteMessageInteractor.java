package use_case.delete_message;

import entity.Chat;

/**
 * The delete message Interactor.
 */
public class DeleteMessageInteractor implements DeleteMessageInputBoundary {
    private final DeleteMessageDataAccessInterface messageDataAccessObject;
    private final DeleteMessageOutputBoundary messagePresenter;

    public DeleteMessageInteractor(DeleteMessageDataAccessInterface messageDataAccessObject, DeleteMessageOutputBoundary userPresenter) {
        this.messageDataAccessObject = messageDataAccessObject;
        this.messagePresenter = userPresenter;
    }


    @Override
    public void execute(DeleteMessageInputData deleteMessageInputData) {
        String messageId = deleteMessageInputData.getMID();
        Chat chat = deleteMessageInputData.getChat();
        boolean result = messageDataAccessObject.deleteMessage(messageId, chat);

        if (result) {
            // update the in memory chat object
            chat.deleteMessage(messageId);

            final DeleteMessageOutputData deleteMessageOutputData = new DeleteMessageOutputData(null, deleteMessageInputData.getChat(), false);
            messagePresenter.prepareSuccessSendMessageView(deleteMessageOutputData);
        }
        else {
            final DeleteMessageOutputData deleteMessageOutputData = new DeleteMessageOutputData(deleteMessageInputData.getMID() ,deleteMessageInputData.getChat(), true);
            messagePresenter.prepareFailSendMessageView("delete Message Failed", deleteMessageOutputData);
        }


    }
}
