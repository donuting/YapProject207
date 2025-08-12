package usecase.signup;

import entity.GroupChat;
import entity.User;

import java.util.List;

/**
 * DAO for the Signup Use Case.
 */
public interface SignupUserDataAccessInterface {

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
     */
    GroupChat createSelfChat(List<String> memberIds, String chatName);
}
