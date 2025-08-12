package usecase.add_DOB;

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
}
