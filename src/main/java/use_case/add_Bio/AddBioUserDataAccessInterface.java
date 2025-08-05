package use_case.add_Bio;

/**
 * The interface of the DAO for the Add Bio Use Case.
 */
public interface AddBioUserDataAccessInterface {

    /**
     * Updates the system to record this user's bio.
     * @param username the username of the updated user
     * @param bio the bio to be updated
     */
    boolean addBio(String username, String bio);
}
