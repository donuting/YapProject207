package dataaccess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.GroupChat;
import entity.GroupChatFactory;
import entity.User;
import usecase.add_Bio.AddBioUserDataAccessInterface;
import usecase.add_DOB.AddDOBUserDataAccessInterface;
import usecase.add_friend.AddFriendUserDataAccessInterface;
import usecase.block_friend.BlockFriendUserDataAccessInterface;
import usecase.change_password.ChangePasswordUserDataAccessInterface;
import usecase.create_chat.CreateChatUserDataAccessInterface;
import usecase.delete_account.DeleteAccountDataAccessInterface;
import usecase.join_chat.JoinChatDataAccessInterface;
import usecase.leave_chat.LeaveChatDataAccessInterface;
import usecase.load_group_chats.LoadGroupChatsDataAccessInterface;
import usecase.login.LoginUserDataAccessInterface;
import usecase.logout.LogoutUserDataAccessInterface;
import usecase.signup.SignupUserDataAccessInterface;
import usecase.update_chat.UpdateChatDataAccessInterface;
import usecase.profile.UserProfileDataAccessInterface; // ADD THIS IMPORT

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
        BlockFriendUserDataAccessInterface,
        DeleteAccountDataAccessInterface,
        AddFriendUserDataAccessInterface,
        CreateChatUserDataAccessInterface,
        JoinChatDataAccessInterface,
        UpdateChatDataAccessInterface,
        LeaveChatDataAccessInterface,
        LoadGroupChatsDataAccessInterface,
        UserProfileDataAccessInterface { // ADD THIS INTERFACE

    private final Map<String, User> users = new HashMap<>();
    private String currentUsername;
    private User currentUser; // ADD THIS FIELD

    @Override
    public boolean existsByName(String identifier) {
        return users.containsKey(identifier);
    }

    public boolean alreadyFriend(String currentUsername, String friendUsername) {
        User user = users.get(currentUsername);
        User friend = users.get(friendUsername);
        if (user.getFriendIDs().contains(friend.getID())) {
            return true;
        }
        return false;
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
    public boolean addFriend(String currentUsername, String friendUsername) {
        return false;
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

//    @Override
//    public boolean alreadyFriend(String currentUsername, String friendUsername) {
//        return false;
//    }
//
//    @Override
//    public boolean addFriend(String currentUsername, String friendUsername) {
//        return false;
//    }

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
     * @param newGroupChat
     * @param username
     */
    @Override
    public void savePersonalChat(GroupChat newGroupChat, String username) {

    }

    /**
     * Get the current user.
     *
     * @return the current user.
     */
    @Override
    public User getCurrentUser() {
        return this.currentUser;
    }

    /**
     * Gets a username given an ID.
     *
     * @param blockedId
     * @return the corresponding user.
     */
    @Override
    public String getUsernameFromId(String blockedId) {
        return "";
    }

    @Override
    public boolean leaveGroupChat(String channelUrl, String userId, String username) {
        return false;
    }

    @Override
    public GroupChat getActiveGroupChat() {
        return null;
    }

    /**
     * Loads a GroupChat object given its channel URL
     *
     * @param channelUrl the channel URL.
     * @return the retrieved group chat.
     */
    @Override
    public GroupChat load(String channelUrl) {
        return null;
    }

    @Override
    public void setActiveGroupChat(GroupChat newChat) {

    }

    /**
     * Adds a user to a group chat given its channel URL, and returns the updated group chat.
     *
     * @param userId     the user's ID.
     * @param channelUrl the channel URL.
     * @return the updated group chat.
     */
    @Override
    public GroupChat addUser(String userId, String channelUrl) {
        return null;
        // IMPLEMENT THIS
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
    public String saveProfile(String oldUsername, String username, String bio, String dateOfBirth) {
        if (users.containsKey(oldUsername)) {
            User user = users.get(oldUsername);
            // Remove old entry
            users.remove(user.getName());
            // Update user data
            user.setName(username);
            user.EditBiography(bio);
            user.EditDOB(dateOfBirth);
            // Add back with new key
            users.put(username, user);

            // Update current username if it's the current user
            if (currentUser != null && currentUser.getName().equals(username)) {
                currentUsername = username;
            }

            return user.getID();
        }
        return null;
    }

    @Override
    public List<String> loadProfile(String username) {
        if (users.containsKey(username)) {
            User user = users.get(username);
            List<String> userProfile = new ArrayList<>();
            userProfile.add(user.getID());
            userProfile.add(user.getBio());
            userProfile.add(user.getDOB());
            return userProfile;
        }
        return null;
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
          
    /**
     * Delete user by id and username. Return true only if a stored user with the given id
     * exists and its name matches the provided username.
     * @param userId   the unique id of the user
     * @param username the username expected for the user
     * @return true if deletion succeeded
     */
    @Override
    public boolean deleteUserById(String userId, String username) {
        if (userId == null || username == null) return false;
        for (Map.Entry<String, User> entry : users.entrySet()) {
            User u = entry.getValue();
            if (u != null && userId.equals(u.getID()) && username.equals(u.getName())) {
                users.remove(entry.getKey());
                if (username.equals(this.currentUsername)) {
                    this.currentUsername = null;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Block the target user for the current user.
     * @param currentUser The user performing the block.
     * @param blockedUsername the name of the user to be blocked
     * @param blockedUserId The user to be blocked.
     * @return true if successful
     */
    @Override
    public boolean blockFriend(User currentUser, String blockedUsername, String blockedUserId) {
        User blockedUser = users.get(blockedUsername);
        if (currentUser == null || blockedUser == null) {
            return false;
        }
        boolean success = currentUser.blockUser(blockedUser.getID());
        if (success) {
            return true;
        }
        return false;
    }
}
