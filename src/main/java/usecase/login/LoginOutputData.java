package usecase.login;

/**
 * Output Data for the Login Use Case.
 */
public class LoginOutputData {

    private final String username;
    private final boolean useCaseFailed;
    private final String password;
    private final String userId;

    public LoginOutputData(String username, boolean useCaseFailed, String password, String userId) {
        this.username = username;
        this.useCaseFailed = useCaseFailed;
        this.password = password;
        this.userId = userId;
    }

    /**
     * Gets the username outputted by the interactor.
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the password outputted by the interactor.
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets the user ID outputted by the interactor.
     * @return the user ID
     */
    public String getUserId() {
        return userId;
    }

}
