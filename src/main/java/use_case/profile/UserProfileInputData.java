package use_case.profile;

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
    public String getOldUsername() {
        return oldUsername;
    }

    public String getUsername() {
        return username;
    }

    public String getBio() {
        return bio;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }
}