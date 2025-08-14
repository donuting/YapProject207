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

    public String getUsername() {
        return username;
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }

    public String getPassword() {
        return password;
    }
}
