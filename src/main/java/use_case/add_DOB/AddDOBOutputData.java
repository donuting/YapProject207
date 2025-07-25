package use_case.add_DOB;

/**
 * Output Data for the Add DOB Use Case.
 */
public class AddDOBOutputData {
    private final String username;
    private final boolean useCaseFailed;
    private final String dob;

    public AddDOBOutputData(String username, boolean useCaseFailed, String dob) {
        this.username = username;
        this.useCaseFailed = useCaseFailed;
        this.dob = dob;
    }

    public String getUsername() {
        return username;
    }

    public boolean getUseCaseFailed() {
        return useCaseFailed;
    }

    public String getDob() {
        return dob;
    }
}
