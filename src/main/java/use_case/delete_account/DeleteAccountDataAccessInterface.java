package use_case.delete_account;

public interface DeleteAccountDataAccessInterface {
    boolean deleteUserById(String userId);
    boolean deleteUserByUsername(String username);
}