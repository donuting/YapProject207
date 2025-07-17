package entity;

import java.util.List;

/**
 * Factory for creating CommonUser objects.
 */
public class CommonUserFactory implements UserFactory {

    @Override
    public User create(String name,
                       String password,
                       String ID,
                       String biography,
                       String dateOfBirth,
                       List<String> friendIDs,
                       List<String> blockedIDs,
                       List<GroupChat> groupChats,
                       List<PersonalChat> personalChats) {
        return new CommonUser(name, password, ID, biography, dateOfBirth, friendIDs, blockedIDs, groupChats, personalChats);
    }
}
