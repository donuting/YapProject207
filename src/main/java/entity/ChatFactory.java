package entity;

import java.util.List;

/**
 * Factory for creating chats.
 */
public interface ChatFactory {
    /**
     * Creates a new Chat.
     *
     * @param chatName the name of the new chat
     * @param members  the list of the members of the new chat
     * @return the new user
     */
    Chat create(String chatName, List<User> members);
}
