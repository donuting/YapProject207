package use_case.self_chat;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The Self Chat Interactor.
 */
public class SelfChatInteractor implements SelfChatInputBoundary {

    private final SelfChatUserDataAccessInterface selfChatDataAccess;
    private final SelfChatOutputBoundary selfChatPresenter;

    public SelfChatInteractor(SelfChatUserDataAccessInterface selfChatDataAccess,
                              SelfChatOutputBoundary selfChatPresenter) {
        this.selfChatDataAccess = selfChatDataAccess;
        this.selfChatPresenter = selfChatPresenter;
    }

    @Override
    public void execute(SelfChatInputData selfChatInputData) {
        try {
            String message = selfChatInputData.getMessage();
            LocalDateTime timestamp = selfChatInputData.getTimestamp();

            // Validate message
            if (message == null || message.trim().isEmpty()) {
                selfChatPresenter.presentError("Message cannot be empty");
                return;
            }

            // Save the message
            selfChatDataAccess.saveMessage(message, timestamp);

            // Present success
            SelfChatOutputData outputData = new SelfChatOutputData(message, timestamp, true, null);
            selfChatPresenter.presentMessage(outputData);

        } catch (Exception e) {
            selfChatPresenter.presentError("Failed to save message: " + e.getMessage());
        }
    }

    @Override
    public void clearMessages() {
        try {
            selfChatDataAccess.clearAllMessages();
            selfChatPresenter.presentClearResult(true, null);
        } catch (Exception e) {
            selfChatPresenter.presentClearResult(false, "Failed to clear messages: " + e.getMessage());
        }
    }

    @Override
    public void loadMessages() {
        try {
            List<String> messages = selfChatDataAccess.getAllMessages();
            List<LocalDateTime> timestamps = selfChatDataAccess.getAllTimestamps();

            SelfChatOutputData outputData = new SelfChatOutputData(messages, timestamps, true, null);
            selfChatPresenter.presentLoadedMessages(outputData);

        } catch (Exception e) {
            SelfChatOutputData outputData = new SelfChatOutputData((String) null, null, false, "Failed to load messages: " + e.getMessage());
            selfChatPresenter.presentLoadedMessages(outputData);
        }
    }
}
