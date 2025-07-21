package entity;

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
     * Returns the password of the user.
     * @return the password of the user.
     */
    String getPassword();

    /**
     * Returns the ID of the user.
     * @return the ID of the user.
     */
    String getID();

    /**
     * Adds a bio to the user.
     * @param bio The bio to be added.
     * @return true if successful otherwise false
     */
    boolean EditBiography(String bio);

    /**
     * Adds a DOB to the chat.
     * @param DOB The DOB to be added.
     * @return true if successful otherwise false
     */
    boolean EditDOB(String DOB);

    String getDOB();

    String getBio();
}
