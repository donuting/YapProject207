package usecase.delete_message;

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
        String messageId = deleteMessageInputData.getMessageId();
        Chat chat = messageDataAccessObject.getActiveGroupChat();
        boolean result = messageDataAccessObject.deleteMessage(messageId, chat);

        if (result) {
            // update the in memory chat object
            chat.deleteMessage(messageId);

            final DeleteMessageOutputData deleteMessageOutputData = new DeleteMessageOutputData(deleteMessageInputData.getMessageId(), false);
            messagePresenter.prepareSuccessDeleteMessageView(deleteMessageOutputData);
        }
        else {
            final DeleteMessageOutputData deleteMessageOutputData = new DeleteMessageOutputData(null, true);
            messagePresenter.prepareFailDeleteMessageView("Delete Message Failed", deleteMessageOutputData);
        }


    }
}
