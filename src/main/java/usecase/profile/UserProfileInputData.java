package usecase.profile;

/**
 * Input Data for the User Profile Use Case.
 */
public class UserProfileInputData {
    private final String oldUsername;
    private final String username;
    private final String bio;
    private final String dateOfBirth;

    public UserProfileInputData(String oldUsername, String username, String bio, String dateOfBirth) {
        this.oldUsername = oldUsername;
        this.username = username;
        this.bio = bio;
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Gets the inputted old username.
     *
     * @return the inputted old username
     */
    public String getOldUsername() {
        return oldUsername;
    }

    /**
     * Gets the inputted username.
     *
     * @return the inputted username
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
     * Gets the inputted date of birth.
     *
     * @return the inputted date of birth.
     */
    public String getDateOfBirth() {
        return dateOfBirth;
    }
}
