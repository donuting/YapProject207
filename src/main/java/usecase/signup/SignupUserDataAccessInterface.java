package usecase.signup;

import java.util.List;

import entity.GroupChat;
import entity.User;

/**
 * DAO for the Signup Use Case.
 */
public interface SignupUserDataAccessInterface {

    /**
     * Sets the username of the current user.
     * @param name the username
     */
    void setCurrentUsername(String name);

    /**
     * Checks if the given username exists.
     * @param username the username to look for
     * @return true if a user with the given username exists; false otherwise
     */
    boolean existsByName(String username);

    /**
     * Saves the user.
     * @param user the user to save
     */
    void save(User user);

    /**
     * Creates a new self chat for the user.
     * @param memberIds the list of members of the self chat (just the user)
     * @param chatName the name of the self chat
     * @return the new group chat
     */
    GroupChat createSelfChat(List<String> memberIds, String chatName);
}
