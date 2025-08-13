package usecase.delete_account;

/**
 * The input data for the Delete Account Use Case.
 */
public class DeleteAccountInputData {
    private final String username;

    public DeleteAccountInputData(String username) {
        this.username = username;
    }

    /**
     * Gets the inputted username.
     *
     * @return the inputted username.
     */
    public String getUsername() {
        return username;
    }
}
