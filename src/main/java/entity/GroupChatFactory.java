package entity;

import java.util.List;

public class GroupChatFactory implements ChatFactory {

    @Override
    public GroupChat create(List<User> members, String chatName){
        return new GroupChat(members, chatName);
    }
}
