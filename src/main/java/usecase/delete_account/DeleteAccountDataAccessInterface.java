package usecase.delete_account;

import entity.User;

/**
 * Data Access for actions which are related to deleting a user.
 */
public interface DeleteAccountDataAccessInterface {

    /**
     * Deletes a user given their ID and username.
     * @param userId the user's ID
     * @param username the users username
     * @return true if successful
     */
    boolean deleteUserById(String userId, String username);

    /**
     * Get the current user.
     *
     * @return the current user.
     */
    User getCurrentUser();
}
