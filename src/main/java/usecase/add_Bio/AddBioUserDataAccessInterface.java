package usecase.add_Bio;

/**
 * The interface of the DAO for the Add Bio Use Case.
 */
public interface AddBioUserDataAccessInterface {

    /**
     * Updates the system to record this user's bio.
     * @param username the username of the updated user
     * @param bio the bio to be updated
     * @return true if successful
     */
    boolean addBio(String username, String bio);
}
