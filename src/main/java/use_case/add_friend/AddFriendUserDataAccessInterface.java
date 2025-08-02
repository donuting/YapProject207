package use_case.add_friend;

import entity.User;

/**
 * DAO for the AddFriend Use Case.
 */
public interface AddFriendUserDataAccessInterface {

    /**
     * Checks if the given user exists.
     * @param username the username to look for
     * @param userID the id that must match the username
     * @return true if a user with the given username exists and the userid matches that same user; false otherwise
     */
    boolean userExists(String username, String userID);

    /**
     * Checks if user already has friend added.
     * @param currentUser user performing friendship
     * @param friend user receiving friendship
     * @return true if the two users have each other added already
     */
    boolean alreadyFriend(User currentUser, User friend);

    /**
     * Returns the user with the given ID.
     * @param userID the ID to look up
     * @return the user with the given ID
     */
    User getUser(String userID);

    /**
     * Returns the user with the given username and ID, more secure.
     * @param username the username to loop up
     * @param userID the ID to look up
     * @return the user with the given ID and username
     */
    User getFriendUser(String username, String userID);

    /**
     * Add friendship.
     * @param currentUser the user performing the friendship
     * @param friend the user receiving the friendship
     * @return true if friendship was successful
     */
    boolean addFriend(User currentUser, User friend);
}
