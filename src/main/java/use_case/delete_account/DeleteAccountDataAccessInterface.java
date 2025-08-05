package use_case.delete_account;

import entity.User;

public interface DeleteAccountDataAccessInterface {
    boolean deleteUserById(String userId);

    User getCurrentUser();
}