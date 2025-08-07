package use_case.create_chat;

import entity.GroupChat;
import entity.GroupChatFactory;
import entity.User;

import java.util.List;

public interface CreateChatUserDataAccessInterface {

    /**
     * Creates a SendBirdGroupChannel, adds the users using their ID, creates a GroupChat and adds the channel as an attribute.
     * @param memberIDs the member IDs of the users in the chat
     * @param chatName the name of the chat
     * @param groupChatFactory the factory for the GroupChat
     * @return the newly created GroupChat
     */
    GroupChat create(List<String> memberIDs, String chatName, GroupChatFactory groupChatFactory);

    /**
     * Get the current user.
     *
     * @return the current user.
     */
    User getCurrentUser();

    /**
     * Save a group chat to a user.
     *
     * @param newGroupChat the group chat.
     */
    void saveGroupChat(GroupChat newGroupChat, String username);
}
