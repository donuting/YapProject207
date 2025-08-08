package entity;

import java.util.List;

/**
 * Factory for creating chats.
 */
public interface ChatFactory {
    /**
     * Creates a new Chat.
     *
     * @param memberIds the list of the IDs of the members of the new chat.
     * @param chatName the name of the new chat.
     * @param messageHistory the message history of the new chat.
     * @return the new chat
     */
    Chat create(List<String> memberIds, String chatName, List<Message> messageHistory);

    /**
     * Creates a new Chat.
     *
     * @param memberIds the list of the IDs of the members of the new chat.
     * @param chatName the name of the new chat.
     * @param messageHistory the message history of the new chat.
     * @param channelUrl the URL of the new chat.
     * @return the new chat
     */
    Chat create(List<String> memberIds, String chatName, List<Message> messageHistory, String channelUrl);
}
