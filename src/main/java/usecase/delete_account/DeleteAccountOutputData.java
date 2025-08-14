package usecase.delete_account;

/**
 * Output Data for the Create Chat Use Case.
 */
public class DeleteAccountOutputData {
    private final boolean success;

    public DeleteAccountOutputData(boolean success) {
        this.success = success;
    }

    /**
     * Returns whether the use case succeeded.
     * @return true if successful
     */
    public boolean isSuccess() {
        return success;
    }
}
