package usecase.create_chat;

/**
 * The input data for the Create Chat Use Case.
 */
public class CreateChatInputData {
    private final String chatName;
    // NEW: username to add to chat
    private final String username;

    // NEW: Constructor with both parameters
    public CreateChatInputData(String chatName, String username) {
        this.chatName = chatName;
        this.username = username;
    }

    // Backwards compatibility constructor
    public CreateChatInputData(String chatName) {
        this.chatName = chatName;
        // Default to empty username
        this.username = "";
    }

    /**
     * Gets the inputted chat name.
     *
     * @return the inputted chat name.
     */
    public String getChatName() {
        return chatName;
    }

    // NEW: Getter for username
    /**
     * Gets the inputted username to be added to the chat (not the current user).
     *
     * @return the inputted username.
     */
    public String getUsername() {
        return username;
    }
}
