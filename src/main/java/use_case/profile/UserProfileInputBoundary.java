package use_case.profile;

/**
 * Input Boundary for actions related to user profile.
 */
public interface UserProfileInputBoundary {
    /**
     * Executes the save profile use case.
     * @param userProfileInputData the input data
     */
    void saveProfile(UserProfileInputData userProfileInputData);

    /**
     * Executes the load profile use case.
     * @param username the user's name
     */
    void loadProfile(String username);
}

