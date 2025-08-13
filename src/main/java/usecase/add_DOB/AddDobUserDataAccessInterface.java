package usecase.add_DOB;

import entity.User;

/**
 * The interface of the DAO for the Add DOB Use Case.
 */
public interface AddDobUserDataAccessInterface {

    /**
     * Updates the system to record this user's DOB.
     * @param username the name of the user whose DOB is to be updated
     * @param dateOfBirth the user's date of birth
     * @return true if successful.
     */
    boolean addDob(String username, String dateOfBirth);

    /**
     * Gets a user given their username.
     * @param username the name of the user
     * @return the user requested.
     */
    User get(String username);
}
