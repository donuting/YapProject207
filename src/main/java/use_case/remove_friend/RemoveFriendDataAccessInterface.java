package use_case.remove_friend;

import entity.User;

public interface RemoveFriendDataAccessInterface {
    boolean existsByName(String removedUsername);

    boolean removeFriend(String currentUsername, String removedUsername);

    User getCurrentUser();

    User get(String removedUsername);
}
