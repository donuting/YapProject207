package use_case.add_Bio;

/**
 * The input data for the Add Bio Use Case.
 */
public class AddBioInputData {
    private final String username;
    private final String bio;
    private final String password;

    public AddBioInputData(String username, String bio, String password) {
        this.username = username;
        this.bio = bio;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getBio() {
        return bio;
    }

    public String getPassword() {
        return password;
    }
}
