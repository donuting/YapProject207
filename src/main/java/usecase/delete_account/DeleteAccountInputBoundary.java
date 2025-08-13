package usecase.delete_account;

/**
 * Input Boundary for actions which are related to deleting a user.
 */
public interface DeleteAccountInputBoundary {

    /**
     * Executes the use case for deleting a user.
     * @param inputData the input data
     */
    void execute(DeleteAccountInputData inputData);
}
