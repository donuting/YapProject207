package entity;

import java.util.List;

/**
 * The representation of a user in our program.
 */
public interface User {

    /**
     * Returns the username of the user.
     * @return the username of the user.
     */
    String getName();

    /**
     * Returns a list of blocked users.
     * @return List of blocked users
     */
    List<User> getBlockedUsers();

    /**
     * Returns the password of the user.
     * @return the password of the user.
     */
    String getPassword();

    /**
     * Returns the ID of the user.
     * @return the ID of the user.
     */
    Integer getID();
}
