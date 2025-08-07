package use_case.change_password;

import entity.User;

/**
 * The interface of the DAO for the Change Password Use Case.
 */
public interface ChangePasswordUserDataAccessInterface {

    /**
     * Updates the system to record this user's password.
     * @param username the user whose password is to be updated
     * @param password password the new password
     */
    void changePassword(String username, String password);
}
