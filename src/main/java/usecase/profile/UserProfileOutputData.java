package usecase.profile;

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

    /**
     * Gets the username outputted by the interactor.
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the user ID outputted by the interactor.
     * @return the user ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Gets the bio outputted by the interactor.
     * @return the bio
     */
    public String getBio() {
        return bio;
    }

    /**
     * Gets the date of birth outputted by the interactor.
     * @return the date of birth
     */
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Returns whether the use case succeeded.
     * @return true if successful
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Gets the message outputted by the interactor.
     * @return the message
     */
    public String getMessage() {
        return message;
    }
}
