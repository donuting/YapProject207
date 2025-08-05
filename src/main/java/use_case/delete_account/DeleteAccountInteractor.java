package use_case.delete_account;

public class DeleteAccountInteractor implements DeleteAccountInputBoundary {
    private final DeleteAccountDataAccessInterface accountDeletionDataAccessObject;
    private final DeleteAccountOutputBoundary presenter;

    public DeleteAccountInteractor(DeleteAccountDataAccessInterface accountDeletionDataAccessObject, DeleteAccountOutputBoundary presenter) {
        this.accountDeletionDataAccessObject = accountDeletionDataAccessObject;
        this.presenter = presenter;
    }

    @Override
    public void execute(DeleteAccountInputData inputData) {
        String userId = accountDeletionDataAccessObject.getCurrentUser().getID();
        boolean result = accountDeletionDataAccessObject.deleteUserById(userId);
        presenter.present(new DeleteAccountOutputData(result));
    }
}