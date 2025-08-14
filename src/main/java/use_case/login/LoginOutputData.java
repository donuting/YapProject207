package use_case.login;

/**
 * Output Data for the Login Use Case.
 */
public class LoginOutputData {

    private final String username;
    private final boolean useCaseFailed;
    private final String password;
    private final String UID;
    private final String Bio;
    private final String DOB;

    public LoginOutputData(String username, boolean useCaseFailed, String password, String UID, String Bio, String DOB) {
        this.username = username;
        this.useCaseFailed = useCaseFailed;
        this.password = password;
        this.UID = UID;
        this.Bio = Bio;
        this.DOB = DOB;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {return password;}
    public String getUID() {return UID;}
    public String getBio() {return Bio;}
    public String getDOB() {return DOB;}

}
