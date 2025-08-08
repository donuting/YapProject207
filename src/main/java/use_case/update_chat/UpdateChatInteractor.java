package use_case.update_chat;

import entity.Chat;
import entity.GroupChat;
import entity.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * The interactor for the Update Chat use case.
 */
public class UpdateChatInteractor implements UpdateChatInputBoundary {
    private final UpdateChatDataAccessInterface updateChatDataAccessObject;
    private final UpdateChatOutputBoundary presenter;

    public UpdateChatInteractor(UpdateChatDataAccessInterface updateChatDataAccessInterface, UpdateChatOutputBoundary presenter) {
        this.updateChatDataAccessObject = updateChatDataAccessInterface;
        this.presenter = presenter;
    }

    /**
     * Execute the Update Chat use case.
     */
    @Override
    public void execute() {
        String channelUrl = updateChatDataAccessObject.getActiveGroupChat().getChannelUrl();

        ScheduledExecutorService updateChatExecutor = Executors.newSingleThreadScheduledExecutor();
        ScheduledExecutorService checkIfViewingChatExecutor = Executors.newSingleThreadScheduledExecutor();

        // Check if viewing this chat every 1 second, with 3-second initial delay so that the chat is updated at least once.
        Runnable checkIfViewingChatRunnable = () -> checkIfViewingChat(channelUrl, updateChatExecutor, checkIfViewingChatExecutor);
        checkIfViewingChatExecutor.scheduleAtFixedRate(checkIfViewingChatRunnable, 3, 1, TimeUnit.SECONDS);

        // Update this chat every 2 seconds
        Runnable updateChatRunnable = () -> updateChat(channelUrl);
        updateChatExecutor.scheduleAtFixedRate(updateChatRunnable, 0, 2, TimeUnit.SECONDS);

        // Time users out after 20 minutes
        checkIfViewingChatExecutor.schedule(checkIfViewingChatExecutor::shutdownNow, 1200, TimeUnit.SECONDS);
        updateChatExecutor.schedule(updateChatExecutor::shutdownNow, 1200, TimeUnit.SECONDS);
    }

    private void updateChat(String channelUrl) {
        // Load the new active chat
        GroupChat newActiveChat = updateChatDataAccessObject.load(channelUrl);

        // Check that the chat exists
        if (newActiveChat == null) {
            UpdateChatOutputData outputData = new UpdateChatOutputData(new ArrayList<>(), new ArrayList<>(), false);
            presenter.updateChatPrepareFailView("chat not found", outputData);
        }
        else {
            // Save the new chat object in the DAO
            updateChatDataAccessObject.setActiveGroupChat(newActiveChat);

            // Make a list of new messages
            List<Message> updatedMessages = newActiveChat.getMessageHistory();

            // Make a list of users
            List<String> updatedUsers = newActiveChat.getMemberIds();

            // Send output data to the presenter
            UpdateChatOutputData outputData = new UpdateChatOutputData(updatedMessages, updatedUsers, true);
            presenter.updateChatPrepareSuccessView(outputData);
        }
    }

    private void checkIfViewingChat(String channelUrl, ScheduledExecutorService updateChatExecutor, ScheduledExecutorService checkIfViewingChatExecutor) {
        if (!updateChatDataAccessObject.getActiveGroupChat().getChannelUrl().equals(channelUrl)) {
            updateChatExecutor.shutdownNow();
            checkIfViewingChatExecutor.shutdownNow();
        }
    }

    /**
     * Switches the view to return to the list of chats.
     */
    @Override
    public void leaveChatView() {
        // Set the current chat to null in the DAO
        updateChatDataAccessObject.setActiveGroupChat(null);

        presenter.leaveChatView();
    }
}
