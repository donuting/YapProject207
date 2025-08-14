package usecase.login;

/**
 * Output Data for the Login Use Case.
 */
public class LoginOutputData {

    private final String username;
    private final boolean useCaseFailed;
    private final String password;
    private final String Bio;
    private final String DOB;
    private final String userId;

    public LoginOutputData(String username, boolean useCaseFailed,
                           String password, String userId, String Bio, String DOB) {
        this.username = username;
        this.useCaseFailed = useCaseFailed;
        this.password = password;
        this.Bio = Bio;
        this.DOB = DOB;
        this.userId = userId;
    }

    /**
     * Gets the username outputted by the interactor.
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    public String getBio() {return Bio;}
    public String getDOB() {return DOB;}
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
