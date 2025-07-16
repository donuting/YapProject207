package entity;

import java.util.List;

public class GroupChatFactory implements ChatFactory {

    @Override
    public GroupChat create(List<String> memberIDs, String chatName){
        return new GroupChat(memberIDs, chatName);
    }
}
