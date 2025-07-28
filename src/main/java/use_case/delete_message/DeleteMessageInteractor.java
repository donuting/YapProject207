package use_case.delete_message;

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
        boolean result = messageDataAccessObject.deleteMessage(deleteMessageInputData.getMID(), deleteMessageInputData.getChat());

        if (result) {
            final DeleteMessageOutputData deleteMessageOutputData = new DeleteMessageOutputData(null, deleteMessageInputData.getChat(), false);
            messagePresenter.prepareSuccessSendMessageView(deleteMessageOutputData);
        }
        else {
            final DeleteMessageOutputData deleteMessageOutputData = new DeleteMessageOutputData(deleteMessageInputData.getMID() ,deleteMessageInputData.getChat(), true);
            messagePresenter.prepareFailSendMessageView("delete Message Failed", deleteMessageOutputData);
        }


    }
}
