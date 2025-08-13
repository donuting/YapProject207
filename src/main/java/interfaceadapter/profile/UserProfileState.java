package interfaceadapter.profile;

/**
 * The state for the User Profile View Model.
 */
public class UserProfileState {
    private String username = "";
    private String userId = "";
    private String bio = "";
    private String dateOfBirth = "";
    private String error;
    private String successMessage;

    public UserProfileState(UserProfileState copy) {
        username = copy.username;
        userId = copy.userId;
        bio = copy.bio;
        dateOfBirth = copy.dateOfBirth;
        error = copy.error;
        successMessage = copy.successMessage;
    }

    // Because of the previous copy constructor, the default constructor must be explicit.
    public UserProfileState() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }

    @Override
    public String toString() {
        return "UserProfileState{"
                +
                "username='" + username + '\''
                +
                ", userId='" + userId + '\''
                +
                ", bio='" + bio + '\''
                +
                ", dateOfBirth='" + dateOfBirth + '\''
                +
                '}';
    }
}
