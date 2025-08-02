package use_case.message_read;

public class MessageReadInteractor implements MessageReadInputBoundary {
    private final MessageReadUserDataAccessInterface messageReadDataAccessObject;
    private final MessageReadOutputBoundary presenter;

    public MessageReadInteractor(
            MessageReadUserDataAccessInterface messageReadDataAccessObject,
            MessageReadOutputBoundary presenter
    ) {
        this.messageReadDataAccessObject = messageReadDataAccessObject;
        this.presenter = presenter;
    }

    @Override
    public void execute(MessageReadInputData inputData) {
        boolean result = messageReadDataAccessObject.markMessageAsRead(
                inputData.getUserId(),
                inputData.getMessage(),
                inputData.getChat()
        );
        presenter.present(new MessageReadOutputData(result));
    }
}