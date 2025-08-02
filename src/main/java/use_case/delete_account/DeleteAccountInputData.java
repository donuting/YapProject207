package use_case.delete_account;

public class DeleteAccountInputData {
    private final String username;

    public DeleteAccountInputData(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}