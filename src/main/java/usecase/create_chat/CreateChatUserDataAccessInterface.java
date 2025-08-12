package usecase.create_chat;

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
     * @param username the username of the user
     */
    void saveGroupChat(GroupChat newGroupChat, String username);

    /**
     * Checks if the given username exists.
     *
     * @param username the username to look for
     * @return true if a user with the given username exists; false otherwise
     */
    boolean existsByName(String username);

    /**
     * Returns the user with the given username.
     * @param username the username to look up
     * @return the user with the given username or null if the user does not exist
     */
    User get(String username);
}