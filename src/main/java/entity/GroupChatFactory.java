package entity;

import java.util.List;

public class GroupChatFactory implements ChatFactory {

    @Override
    public GroupChat create(List<String> memberIds, String chatName, List<Message> messageHistory) {
        return new GroupChat(memberIds, chatName, messageHistory);
    }

    @Override
    public GroupChat create(List<String> memberIds, String chatName,
                            List<Message> messageHistory, String channelUrl) {
        GroupChat groupChat = new GroupChat(memberIds, chatName, messageHistory);
        groupChat.setChannelUrl(channelUrl);
        return groupChat;
    }
}
