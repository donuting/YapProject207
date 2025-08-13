package usecase.change_password;

/**
 * Output Data for the Change Password Use Case.
 */
public class ChangePasswordOutputData {

    private final String username;

    private final boolean useCaseFailed;
    private final String password;

    public ChangePasswordOutputData(String username, boolean useCaseFailed, String password) {
        this.username = username;
        this.useCaseFailed = useCaseFailed;
        this.password = password;
    }

    /**
     * Gets the username outputted by the interactor.
     * @return the username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns whether the use case failed.
     * @return true if the use case failed.
     */
    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }

    /**
     * Gets the password outputted by the interactor.
     * @return the password.
     */
    public String getPassword() {
        return password;
    }
}
