package use_case.join_chat;

import entity.GroupChat;

/**
 * Output Data for the Join Chat use case.
 */
public class JoinChatOutputData {
    private final GroupChat groupChat;

    public JoinChatOutputData(GroupChat groupChat) {
        this.groupChat = groupChat;
    }

    public GroupChat getGroupChat() {
        return groupChat;
    }
}
