package entity;

import java.util.List;

/**
 * Factory for creating chats.
 */
public interface ChatFactory {
    /**
     * Creates a new Chat.
     *
     * @param members  the list of the members of the new chat
     * @return the new user
     */
    Chat create(List<User> members, String chatName);
}
