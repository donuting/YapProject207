package usecase.create_chat;

/**
 * The input data for the Create Chat Use Case.
 */
public class CreateChatInputData {
    private final String chatName;
    private final String username;  // NEW: username to add to chat

    // NEW: Constructor with both parameters
    public CreateChatInputData(String chatName, String username) {
        this.chatName = chatName;
        this.username = username;
    }

    // Backwards compatibility constructor
    public CreateChatInputData(String chatName) {
        this.chatName = chatName;
        this.username = "";  // Default to empty username
    }

    public String getChatName() {
        return chatName;
    }

    // NEW: Getter for username
    public String getUsername() {
        return username;
    }
}