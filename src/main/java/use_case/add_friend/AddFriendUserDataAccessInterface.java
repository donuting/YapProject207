package use_case.add_friend;

import entity.User;

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
//
//    /**
//     * Returns the user with the given username and ID, more secure.
//     * @param username the username to loop up
//     * @param userID the ID to look up
//     * @return the user with the given ID and username
//     */
//    User getFriendUser(String username, String userID);

    /**
     * Add friendship.
     * @param currentUsername the user performing the friendship
     * @param friendUsername the user receiving the friendship
     * @return true if friendship was successful
     */
    boolean addFriend(String currentUsername, String friendUsername);
}
