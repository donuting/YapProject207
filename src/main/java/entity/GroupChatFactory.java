package entity;

import java.util.List;

public class GroupChatFactory implements ChatFactory {

    @Override
    public GroupChat create(List<String> memberIDs, String chatName, List<Message> messageHistory){
        return new GroupChat(memberIDs, chatName, messageHistory);
    }

    public GroupChat create(List<String> memberIDs, String chatName,
                            List<Message> messageHistory, String channelURL){
        GroupChat groupChat = new GroupChat(memberIDs, chatName, messageHistory);
        groupChat.setChannelURL(channelURL);
        return groupChat;
    }
}
