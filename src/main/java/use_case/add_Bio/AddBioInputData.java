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

    /**
     * Gets the inputted username.
     *
     * @return the inputted username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the inputted bio.
     *
     * @return the inputted bio.
     */
    public String getBio() {
        return bio;
    }

    /**
     * Gets the inputted password.
     *
     * @return the inputted password.
     */
    public String getPassword() {
        return password;
    }
}
