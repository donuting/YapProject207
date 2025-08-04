package use_case.self_chat;

import entity.CommonMessageFactory;
import entity.Message;
import entity.MessageFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * The Self Chat Interactor.
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
            String userId = selfChatDataAccess.getCurrentUser().getID();
            String username = selfChatDataAccess.getCurrentUser().getName();
            String messageBody = selfChatInputData.getMessage();

            // Validate message
            if (messageBody == null || messageBody.trim().isEmpty()) {
                selfChatPresenter.presentError("Message cannot be empty");
                return;
            }

            Message message = messageFactory.create(userId, messageBody);

            // Save the message
            List<Message> sentMessages = new ArrayList<>();
            sentMessages.add(selfChatDataAccess.sendMessage(message));

            // Present success
            SelfChatOutputData outputData = new SelfChatOutputData(username, sentMessages, true, null);
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
            String username = selfChatDataAccess.getCurrentUser().getName();
            List<Message> messages = selfChatDataAccess.loadMessages();

            SelfChatOutputData outputData = new SelfChatOutputData(username, messages, true, null);
            selfChatPresenter.presentMessage(outputData);

        } catch (Exception e) {
            SelfChatOutputData outputData = new SelfChatOutputData("", new ArrayList<>(), false, "Failed to load messages: " + e.getMessage());
            selfChatPresenter.presentMessage(outputData);
        }
    }
}
