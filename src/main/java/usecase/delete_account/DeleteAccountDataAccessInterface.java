package usecase.delete_account;

import entity.User;

public interface DeleteAccountDataAccessInterface {
    boolean deleteUserById(String userId, String username);

    User getCurrentUser();
}