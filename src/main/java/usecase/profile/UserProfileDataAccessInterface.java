package usecase.profile;

import java.util.List;

/**
 * Data Access Interface for User Profile operations.
 */
public interface UserProfileDataAccessInterface {
    /**
     * Updates the username, bio and date of birth for the given user ID.
     * @param username the new username.
     * @param oldUsername the old user's username.
     * @param bio the new bio.
     * @param dateOfBirth the new date of birth.
     * @return the saved user's ID, or null if the user doesn't exist
     */
    String saveProfile(String oldUsername, String username, String bio, String dateOfBirth);

    /**
     * Gets user profile data for a user given their username.
     * @param username the user's name
     * @return a list containing the user's ID, bio, and date of birth as strings, or null if the user doesn't exist
     */
    List<String> loadProfile(String username);
}
