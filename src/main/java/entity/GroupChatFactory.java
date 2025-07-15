package entity;

import java.util.List;

public class GroupChatFactory implements ChatFactory {

    @Override
    public GroupChat create(List<User> members){
        return new GroupChat(members);
    }
}
