package use_case.profile;

/**
 * Data Access Interface for User Profile operations.
 */
public interface UserProfileDataAccessInterface {
    /**
     * Updates the username for the given user ID.
     * @param userId the user ID
     * @param username the new username
     */
    void updateUsername(String userId, String username);

    /**
     * Updates the bio for the given user ID.
     * @param userId the user ID
     * @param bio the new bio
     */
    void updateBio(String userId, String bio);

    /**
     * Updates the date of birth for the given user ID.
     * @param userId the user ID
     * @param dateOfBirth the new date of birth
     */
    void updateDateOfBirth(String userId, String dateOfBirth);

    /**
     * Gets the username for the given user ID.
     * @param userId the user ID
     * @return the username
     */
    String getUsername(String userId);

    /**
     * Gets the bio for the given user ID.
     * @param userId the user ID
     * @return the bio
     */
    String getBio(String userId);

    /**
     * Gets the date of birth for the given user ID.
     * @param userId the user ID
     * @return the date of birth
     */
    String getDateOfBirth(String userId);
}