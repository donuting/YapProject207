package use_case.add_friend;

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
     * Checks if user already has friend added.
     * @param currentUsername user performing friendship
     * @param friendUsername user receiving friendship
     * @return true if the two users have each other added already
     */
    boolean alreadyFriend(String currentUsername, String friendUsername);

    /**
     * Returns the user with the given username.
     * @param username the ID to look up
     * @return the user with the given ID
     */
    User get(String username);

    /**
     * Add friendship.
     * @param currentUsername the user performing the friendship
     * @param friendUsername the user receiving the friendship
     * @return true if friendship was successful
     */
    boolean addFriend(String currentUsername, String friendUsername);

    /**
     * Creates the chat between the two friends when added.
     *
     * @return
     */
    GroupChat create(List<String> memberIds, String chatName, GroupChatFactory groupChatFactory);

    /**
     *
     */
    void saveGroupChat(GroupChat newGroupChat, String username);

    /**
     * Returns the current user.
     * @return the current user.
     */
    User getCurrentUser();
}
