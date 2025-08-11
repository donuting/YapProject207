package use_case.create_chat;

/**
 * Output Data for the Create Chat Use Case.
 */
public class CreateChatOutputData {
    private final String chatName;
    private final String userId;
    private final String addedUsername;  // NEW: username that was added to chat

    // NEW: Constructor with added username
    public CreateChatOutputData(String chatName, String userId, String addedUsername) {
        this.chatName = chatName;
        this.userId = userId;
        this.addedUsername = addedUsername;
    }

    // Backwards compatibility constructor
    public CreateChatOutputData(String chatName, String userId) {
        this.chatName = chatName;
        this.userId = userId;
        this.addedUsername = "";  // Default to empty
    }

    public String getChatName() {
        return chatName;
    }

    public String getUserId() {
        return userId;
    }

    // NEW: Getter for added username
    public String getAddedUsername() {
        return addedUsername;
    }
}