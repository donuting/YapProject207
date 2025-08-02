package use_case.delete_account;

public class DeleteAccountOutputData {
    private final boolean success;

    public DeleteAccountOutputData(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }
}