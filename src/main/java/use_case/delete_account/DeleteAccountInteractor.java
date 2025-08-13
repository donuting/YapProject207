package use_case.delete_account;

public class DeleteAccountInteractor implements DeleteAccountInputBoundary {
    private final DeleteAccountDataAccessInterface accountDeletionDataAccessObject;
    private final DeleteAccountOutputBoundary presenter;

    public DeleteAccountInteractor(DeleteAccountDataAccessInterface accountDeletionDataAccessObject,
                                   DeleteAccountOutputBoundary presenter) {
        this.accountDeletionDataAccessObject = accountDeletionDataAccessObject;
        this.presenter = presenter;
    }

    @Override
    public void execute(DeleteAccountInputData inputData) {
        if (accountDeletionDataAccessObject.getCurrentUser() == null) {
            DeleteAccountOutputData outputData = new DeleteAccountOutputData(false);
            presenter.prepareFailDeleteAccountView("Failed to delete account.", outputData);
            return;
        }

        String userId = accountDeletionDataAccessObject.getCurrentUser().getID();
        String username = accountDeletionDataAccessObject.getCurrentUser().getName();

        boolean result = accountDeletionDataAccessObject.deleteUserById(userId, username);
        DeleteAccountOutputData outputData = new DeleteAccountOutputData(result);

        if (result) {
            presenter.prepareSuccessDeleteAccountView(outputData);
        } else {
            presenter.prepareFailDeleteAccountView("Failed to delete account.", outputData);
        }
    }
}