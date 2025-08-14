package usecase.self_chat;

import entity.CommonMessageFactory;
import entity.Message;
import entity.MessageFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * The Self Chat Interactor with debug logging.
 */
public class SelfChatInteractor implements SelfChatInputBoundary {

    private final SelfChatUserDataAccessInterface selfChatDataAccess;
    private final SelfChatOutputBoundary selfChatPresenter;
    private final MessageFactory messageFactory = new CommonMessageFactory();

    public SelfChatInteractor(SelfChatUserDataAccessInterface selfChatDataAccess,
                              SelfChatOutputBoundary selfChatPresenter) {
        this.selfChatDataAccess = selfChatDataAccess;
        this.selfChatPresenter = selfChatPresenter;
    }

    @Override
    public void execute(SelfChatInputData selfChatInputData) {
        try {
            System.out.println("DEBUG: Starting execute method");

            // Check if user is available
            if (selfChatDataAccess.getCurrentUser() == null) {
                System.out.println("DEBUG: No current user available");
                selfChatPresenter.presentError("No user is currently logged in");
                return;
            }

            String userId = selfChatDataAccess.getCurrentUser().getID();
            String username = selfChatDataAccess.getCurrentUser().getName();
            String messageBody = selfChatInputData.getMessage();

            System.out.println("DEBUG: User ID: " + userId);
            System.out.println("DEBUG: Username: " + username);
            System.out.println("DEBUG: Message: " + messageBody);

            // Validate message
            if (messageBody == null || messageBody.trim().isEmpty()) {
                System.out.println("DEBUG: Message is empty, showing error");
                selfChatPresenter.presentError("Message cannot be empty");
                return;
            }

            Message message = messageFactory.create(userId, messageBody);
            System.out.println("DEBUG: Created message with ID: " + message.GetMID());

            // Save the message
            Message sentMessage = selfChatDataAccess.sendMessage(message);
            System.out.println("DEBUG: Sent message, returned message ID: " + sentMessage.GetMID());

            List<Message> sentMessages = new ArrayList<>();
            sentMessages.add(sentMessage);

            // Present success
            SelfChatOutputData outputData = new SelfChatOutputData(username, sentMessages, true, null);
            System.out.println("DEBUG: Calling presentMessage with " + sentMessages.size() + " messages");
            selfChatPresenter.presentMessage(outputData);
            System.out.println("DEBUG: presentMessage called successfully");

        } catch (Exception e) {
            System.out.println("DEBUG: Exception occurred: " + e.getMessage());
            e.printStackTrace();
            selfChatPresenter.presentError("Failed to save message: " + e.getMessage());
        }
    }

    @Override
    public void clearMessages() {
        try {
            System.out.println("DEBUG: Clearing messages");

            // Check if user is available
            if (selfChatDataAccess.getCurrentUser() == null) {
                System.out.println("DEBUG: No current user available for clearing messages");
                selfChatPresenter.presentClearResult(false, "No user is currently logged in");
                return;
            }

            selfChatDataAccess.clearAllMessages();
            selfChatPresenter.presentClearResult(true, null);
            System.out.println("DEBUG: Messages cleared successfully");
        } catch (Exception e) {
            System.out.println("DEBUG: Error clearing messages: " + e.getMessage());
            selfChatPresenter.presentClearResult(false, "Failed to clear messages: " + e.getMessage());
        }
    }

    @Override
    public void loadMessages() {
        try {
            System.out.println("DEBUG: Loading messages");

            // Check if user is available
            if (selfChatDataAccess.getCurrentUser() == null) {
                System.out.println("DEBUG: No current user available, presenting empty message list");
                SelfChatOutputData outputData = new SelfChatOutputData("Guest", new ArrayList<>(), true, null);
                selfChatPresenter.presentMessage(outputData);
                return;
            }

            String username = selfChatDataAccess.getCurrentUser().getName();
            List<Message> messages = selfChatDataAccess.loadMessages();
            System.out.println("DEBUG: Loaded " + messages.size() + " messages for user: " + username);

            SelfChatOutputData outputData = new SelfChatOutputData(username, messages, true, null);
            selfChatPresenter.presentMessage(outputData);
            System.out.println("DEBUG: Messages presented to view");

        } catch (Exception e) {
            System.out.println("DEBUG: Error loading messages: " + e.getMessage());
            e.printStackTrace();
            SelfChatOutputData outputData = new SelfChatOutputData("Guest", new ArrayList<>(), false, "Failed to load messages: " + e.getMessage());
            selfChatPresenter.presentMessage(outputData);
        }
    }

    @Override
    public void saveBirthday(String name, String date) {
        try {
            System.out.println("DEBUG: Saving birthday - Name: '" + name + "', Date: '" + date + "'");

            // Validate input
            if (name == null || name.trim().isEmpty()) {
                System.out.println("DEBUG: Name validation failed - name is null or empty");
                selfChatPresenter.presentBirthdaySaveResult(false, "Name cannot be empty");
                return;
            }

            if (date == null || date.trim().isEmpty() || !date.matches("\\d{8}")) {
                System.out.println("DEBUG: Date validation failed - date: '" + date + "'");
                selfChatPresenter.presentBirthdaySaveResult(false, "Date must be 8 digits (YYYYMMDD)");
                return;
            }

            // Format date for display
            String formattedDate = date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6, 8);
            String successMessage = "Birthday saved for " + name.trim() + ": " + formattedDate;

            System.out.println("DEBUG: Birthday validation passed, success message: " + successMessage);
            selfChatPresenter.presentBirthdaySaveResult(true, successMessage);

        } catch (Exception e) {
            System.out.println("DEBUG: Exception in saveBirthday: " + e.getMessage());
            e.printStackTrace();
            selfChatPresenter.presentBirthdaySaveResult(false, "Failed to save birthday: " + e.getMessage());
        }
    }
}