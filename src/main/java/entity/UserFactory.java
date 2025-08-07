package entity;

import java.util.List;

/**
 * Factory for creating users.
 */
public interface UserFactory {
    /**
     * Creates a new User.
     * @param name the name of the new user
     * @param password the password of the new user
     * @return the new user
     */
    User create (String name, String password);

    User create(String name, String password, String ID, String biography,
                String dateOfBirth, List<String> friendIDs, List<String> blockedIDs,
                List<GroupChat> groupChats, List<GroupChat> personalChats,
                GroupChat selfChat);

    User create(String name, String password, String ID, String biography,
                String dateOfBirth, List<String> friendIDs, List<String> blockedIDs,
                List<GroupChat> groupChats, List<GroupChat> personalChats);


}
