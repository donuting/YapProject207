package use_case.join_chat;

import entity.GroupChat;

public class JoinChatOutputData {
    private final GroupChat groupChat;

    public JoinChatOutputData(GroupChat groupChat) {
        this.groupChat = groupChat;
    }

    public GroupChat getGroupChat() {
        return groupChat;
    }
}
