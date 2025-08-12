package usecase.login;

/**
 * Output Data for the Login Use Case.
 */
public class LoginOutputData {

    private final String username;
    private final boolean useCaseFailed;
    private final String password;
    private final String UID;

    public LoginOutputData(String username, boolean useCaseFailed, String password, String UID) {
        this.username = username;
        this.useCaseFailed = useCaseFailed;
        this.password = password;
        this.UID = UID;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {return password;}
    public String getUID() {return UID;}

}
