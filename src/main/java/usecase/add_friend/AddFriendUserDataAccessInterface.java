package usecase.add_friend;

import entity.GroupChat;
import entity.GroupChatFactory;
import entity.User;

import java.util.List;

/**
 * DAO for the AddFriend Use Case.
 */
public interface AddFriendUserDataAccessInterface {

    /**
     * Checks if the given user exists.
     * @param username the username to look for
     * @return true if a user with the given username exists and the userid matches that same user; false otherwise
     */
    boolean existsByName(String username);

    /**
     * Returns the user with the given username.
     * @param username the ID to look up
     * @return the user with the given ID
     */
    User get(String username);

    /**
     * Add friendship.
     * @param currentUsername the user performing the friendship.
     * @param friendUsername the user receiving the friendship.
     * @param chat the new chat between the two users.
     * @return true if friendship was successful
     */
    boolean addFriend(String currentUsername, String friendUsername, GroupChat chat);

    /**
     * Creates a SendBirdGroupChannel, adds the users using their ID,
     * creates a GroupChat and adds the channel as an attribute.
     * @param memberIds the IDs of the members to be added
     * @param chatName the name of the new chat
     * @param groupChatFactory a Group Chat Factory
     * @return the GroupChat object.
     */
    GroupChat create(List<String> memberIds, String chatName, GroupChatFactory groupChatFactory);


    /**
     * Returns the current user.
     * @return the current user.
     */
    User getCurrentUser();
}
