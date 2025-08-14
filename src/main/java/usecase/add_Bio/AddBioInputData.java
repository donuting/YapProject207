package usecase.add_Bio;

/**
 * The input data for the Add Bio Use Case.
 */
public class AddBioInputData {
    private final String username;
    private final String oldBio;
    private final String newBio;
    private final String password;

    public AddBioInputData(String username, String oldBio,  String newBio, String password) {
        this.username = username;
        this.newBio = newBio;
        this.password = password;
        this.oldBio = oldBio;
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
    public String getNewBio() {
        return newBio;
    }

    /**
     * Gets the inputted password.
     *
     * @return the inputted password.
     */
    public String getPassword() {
        return password;
    }

    public String getOldBio() {return oldBio;}
}
