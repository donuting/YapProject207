package use_case.add_DOB;

/**
 * The input data for the Add DOB Use Case.
 */
public class AddDOBInputData {
    private final String DOB;
    private final String username;
    private final String password;

    public AddDOBInputData(String DOB, String username, String password) {
        this.DOB = DOB;
        this.username = username;
        this.password = password;
    }

    public String getDOB() {
        return DOB;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

