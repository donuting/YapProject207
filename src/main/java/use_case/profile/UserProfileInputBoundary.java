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
     * @param userId the user ID
     */
    void loadProfile(String userId);
}

