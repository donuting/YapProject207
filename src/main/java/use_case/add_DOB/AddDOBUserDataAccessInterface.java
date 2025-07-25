package use_case.add_DOB;

import entity.User;

/**
 * The interface of the DAO for the Add DOB Use Case.
 */
public interface AddDOBUserDataAccessInterface {

    /**
     * Updates the system to record this user's DOB.
     * @param user the user whose DOB is to be updated
     */
    boolean addDOB(User user);
}
