package use_case.logout;

import entity.User;

/**
 * DAO for the Logout Use Case.
 */
public interface LogoutUserDataAccessInterface {

    /**
     * Returns the username of the curren user of the application.
     * @return the username of the current user
     */
    String getCurrentUsername();

    /**
     * Sets the username indicating who is the current user of the application.
     * @param user the new current usernserame
     */
    void setCurrentUser(User user);
}
