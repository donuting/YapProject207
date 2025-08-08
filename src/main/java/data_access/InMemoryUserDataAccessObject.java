package data_access;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.GroupChat;
import entity.GroupChatFactory;
import entity.User;
import use_case.add_Bio.AddBioUserDataAccessInterface;
import use_case.add_DOB.AddDOBUserDataAccessInterface;
import use_case.add_friend.AddFriendUserDataAccessInterface;
import use_case.change_password.ChangePasswordUserDataAccessInterface;
import use_case.create_chat.CreateChatUserDataAccessInterface;
import use_case.login.LoginUserDataAccessInterface;
import use_case.logout.LogoutUserDataAccessInterface;
import use_case.signup.SignupUserDataAccessInterface;
import use_case.profile.UserProfileDataAccessInterface; // ADD THIS IMPORT

/**
 * In-memory implementation of the DAO for storing user data. This implementation does
 * NOT persist data between runs of the program. This DAO should not be implemented.
 */
public class InMemoryUserDataAccessObject implements
        SignupUserDataAccessInterface,
        LoginUserDataAccessInterface,
        ChangePasswordUserDataAccessInterface,
        LogoutUserDataAccessInterface,
        AddBioUserDataAccessInterface,
        AddDOBUserDataAccessInterface,
        AddFriendUserDataAccessInterface,
        CreateChatUserDataAccessInterface,
        UserProfileDataAccessInterface { // ADD THIS INTERFACE

    private final Map<String, User> users = new HashMap<>();

    private String currentUsername;
    private User currentUser; // ADD THIS FIELD

    @Override
    public boolean existsByName(String identifier) {
        return users.containsKey(identifier);
    }

    @Override
    public void save(User user) {
        users.put(user.getName(), user);
    }

    /**
     * Creates a new self chat for the user.
     *
     * @param memberIds the list of members of the self chat (just the user)
     * @param chatName  the name of the self chat
     */
    @Override
    public GroupChat createSelfChat(List<String> memberIds, String chatName) {
        return null;
    }

    @Override
    public User get(String username) {
        return users.get(username);
    }

    @Override
    public void changePassword(String username , String password) {
        // Replace the old entry with the new password
        User user = users.get(username);
        if (user != null) {
            // You'll need to implement this in your User implementation
            // For now, this is a placeholder
        }
    }

    @Override
    public void setCurrentUsername(String name) {
        this.currentUsername = name;
        this.currentUser = users.get(name); // UPDATE CURRENT USER TOO
    }

    @Override
    public String getCurrentUsername() {
        return this.currentUsername;
    }

    /**
     * Sets the user indicating who is the current user of the application.
     *
     * @param user the new current user; null to indicate that no one is currently logged into the application.
     */
    @Override
    public void setCurrentUser(User user) {
        this.currentUser = user;
        if (user != null) {
            this.currentUsername = user.getName();
        }
    }

    @Override
    public void setCurrentSelfChat(GroupChat selfChat) {

    }

    /**
     * Updates the system to record this user's bio.
     *
     * @param username the username of the updated user
     * @param bio      the bio to be updated
     */
    @Override
    public boolean addBio(String username, String bio) {
        User user = users.get(username);
        if (user != null) {
            return user.EditBiography(bio); // USE EXISTING USER METHOD
        }
        return false;
    }

    /**
     * Updates the system to record this user's DOB.
     *
     * @param username the name of the user whose DOB is to be updated
     * @param dob      the user's date of birth
     */
    @Override
    public boolean addDOB(String username, String dob) {
        User user = users.get(username);
        if (user != null) {
            return user.EditDOB(dob); // USE EXISTING USER METHOD
        }
        return false;
    }

    @Override
    public boolean alreadyFriend(String currentUsername, String friendUsername) {
        return false;
    }

    @Override
    public boolean addFriend(String currentUsername, String friendUsername) {
        return false;
    }

    /**
     * Creates a SendBirdGroupChannel, adds the users using their ID, creates a GroupChat and adds the channel as an attribute.
     *
     * @param memberIDs        the member IDs of the users in the chat
     * @param chatName         the name of the chat
     * @param groupChatFactory the factory for the GroupChat
     * @return the newly created GroupChat
     */
    @Override
    public GroupChat create(List<String> memberIDs, String chatName, GroupChatFactory groupChatFactory) {
        return null;
    }

    /**
     * Get the current user.
     *
     * @return the current user.
     */
    @Override
    public User getCurrentUser() {
        return this.currentUser; // IMPLEMENT THIS
    }

    /**
     * Save a group chat to a user.
     *
     * @param newGroupChat the group chat.
     * @param username
     */
    @Override
    public void saveGroupChat(GroupChat newGroupChat, String username) {

    }

    // ========== NEW METHODS FOR UserProfileDataAccessInterface ==========

    @Override
    public void updateUsername(String userId, String username) {
        // Since you're using username as the key, this is more complex
        // You might need to use the user's ID instead, or handle this differently
        User user = findUserById(userId);
        if (user != null) {
            // Remove old entry
            users.remove(user.getName());
            // Update username
            user.setName(username);
            // Add back with new key
            users.put(username, user);

            // Update current username if it's the current user
            if (currentUser != null && currentUser.getID().equals(userId)) {
                currentUsername = username;
            }
        }
    }

    @Override
    public void updateBio(String userId, String bio) {
        User user = findUserById(userId);
        if (user != null) {
            user.EditBiography(bio);
        }
    }

    @Override
    public void updateDateOfBirth(String userId, String dateOfBirth) {
        User user = findUserById(userId);
        if (user != null) {
            user.EditDOB(dateOfBirth);
        }
    }

    @Override
    public String getUsername(String userId) {
        User user = findUserById(userId);
        return user != null ? user.getName() : "";
    }

    @Override
    public String getBio(String userId) {
        User user = findUserById(userId);
        return user != null ? user.getBio() : "";
    }

    @Override
    public String getDateOfBirth(String userId) {
        User user = findUserById(userId);
        return user != null ? user.getDOB() : "";
    }

    /**
     * Helper method to find a user by their ID rather than username.
     * This is important because usernames can change, but IDs should be stable.
     */
    private User findUserById(String userId) {
        return users.values().stream()
                .filter(user -> user.getID().equals(userId))
                .findFirst()
                .orElse(null);
    }
}