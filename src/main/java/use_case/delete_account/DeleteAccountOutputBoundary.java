package use_case.delete_account;

/**
 * The output boundary for the delete account Use Case.
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