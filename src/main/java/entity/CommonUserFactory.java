package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Factory for creating CommonUser objects.
 */
public class CommonUserFactory implements UserFactory {

    @Override
    public CommonUser create(String name, String password) {

        return new CommonUser(name, password, null,
                "", "", new ArrayList<String>(),
                new ArrayList<String>(), new ArrayList<GroupChat>(),
                new ArrayList<GroupChat>());

    }

    @Override
    public CommonUser create(String name, String password,
                       String ID, String biography,
                       String dateOfBirth, List<String> friendIDs,
                       List<String> blockedIDs, List<GroupChat> groupChats,
                       List<GroupChat> personalChats, GroupChat selfChat) {

        return new CommonUser(name, password,
                ID, biography, dateOfBirth,
                friendIDs, blockedIDs, groupChats,
                personalChats, selfChat);

    }

    @Override
    public CommonUser create(String name, String password,
                       String ID, String biography,
                       String dateOfBirth, List<String> friendIDs,
                       List<String> blockedIDs, List<GroupChat> groupChats,
                       List<GroupChat> personalChats) {

        return new CommonUser(name, password, ID, biography, dateOfBirth,
                friendIDs, blockedIDs, groupChats, personalChats);

    }


}
