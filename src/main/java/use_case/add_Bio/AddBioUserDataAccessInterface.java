package use_case.add_Bio;

import entity.User;

/**
 * The interface of the DAO for the Add Bio Use Case.
 */
public interface AddBioUserDataAccessInterface {

    /**
     * Updates the system to record this user's bio.
     * @param user the user whose bio is to be updated
     */
    boolean addBio(User user);
}
