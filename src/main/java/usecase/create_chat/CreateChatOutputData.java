package usecase.create_chat;

/**
 * Output Data for the Create Chat Use Case.
 */
public class CreateChatOutputData {
    private final String chatName;
    private final String userId;
    // NEW: username that was added to chat
    private final String addedUsername;

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
        // Default to empty
        this.addedUsername = "";
    }

    /**
     * Gets the chat name outputted by the interactor.
     * @return the chat name.
     */
    public String getChatName() {
        return chatName;
    }

    /**
     * Gets the user ID outputted by the interactor.
     * @return the user ID.
     */
    public String getUserId() {
        return userId;
    }

    // NEW: Getter for added username
    /**
     * Gets the added user's username outputted by the interactor.
     * @return the added user's username.
     */
    public String getAddedUsername() {
        return addedUsername;
    }
}
