package use_case.profile;

/**
 * Output Data for the User Profile Use Case.
 */
public class UserProfileOutputData {
    private final String username;
    private final String userId;
    private final String bio;
    private final String dateOfBirth;
    private final boolean success;
    private final String message;

    public UserProfileOutputData(String username, String userId, String bio, String dateOfBirth,
                                 boolean success, String message) {
        this.username = username;
        this.userId = userId;
        this.bio = bio;
        this.dateOfBirth = dateOfBirth;
        this.success = success;
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public String getUserId() {
        return userId;
    }

    public String getBio() {
        return bio;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
