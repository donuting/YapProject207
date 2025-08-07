package entity;

import java.util.List;

/**
 * Factory for creating chats.
 */
public interface ChatFactory {
    /**
     * Creates a new Chat.
     *
     * @param memberIDs the list of the IDs of the members of the new chat
     * @return the new user
     */
    Chat create(List<String> memberIDs, String chatName, List<Message> messageHistory);

    Chat create(List<String> memberIDs, String chatName, List<Message> messageHistory, String channelURL);
}
