package usecase.delete_account;

/**
 * Output Boundary for the Delete Account Use Case.
 */
public interface DeleteAccountOutputBoundary {

    /**
     * Prepares the success view for the delete account Use Case.
     * @param deleteAccountOutputData the output data.
     */
    void prepareSuccessDeleteAccountView(DeleteAccountOutputData deleteAccountOutputData);

    /**
     * Prepares the fail view for the delete account Use Case.
     * @param deleteAccountOutputData the output data.
     * @param errorMessage The error message to be shown.
     */
    void prepareFailDeleteAccountView(String errorMessage, DeleteAccountOutputData deleteAccountOutputData);

}
