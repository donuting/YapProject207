package usecase.join_chat;

import entity.GroupChat;

/**
 * Output Data for the Join Chat use case.
 */
public class JoinChatOutputData {
    private final GroupChat groupChat;

    public JoinChatOutputData(GroupChat groupChat) {
        this.groupChat = groupChat;
    }

    /**
     * Gets the group chat outputted by the interactor.
     * @return the group chat
     */
    public GroupChat getGroupChat() {
        return groupChat;
    }
}
