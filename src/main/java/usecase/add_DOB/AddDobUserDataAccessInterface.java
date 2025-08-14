package usecase.add_DOB;

import entity.User;

/**
 * The interface of the DAO for the Add DOB Use Case.
 */
public interface AddDobUserDataAccessInterface {

    /**
     * Updates the system to record this user's DOB.
     * @param username the name of the user whose DOB is to be updated
     * @param dob the user's date of birth
     */
    boolean addDOB(String username, String dob);

    User get(String username);
}
