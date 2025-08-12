package usecase.delete_account;

/**
 * Output Boundary for the Delete Account Use Case.
 */
public interface DeleteAccountOutputBoundary {

    /**
     * Presents the output data from deleting a user.
     * @param outputData the output data
     */
    void present(DeleteAccountOutputData outputData);
}
