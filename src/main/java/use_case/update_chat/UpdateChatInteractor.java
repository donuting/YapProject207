package use_case.update_chat;

import entity.Chat;
import entity.GroupChatFactory;
import entity.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class UpdateChatInteractor implements UpdateChatInputBoundary {
    private final UpdateChatDataAccessInterface updateChatDataAccessObject;
    private final UpdateChatOutputBoundary presenter;

    public UpdateChatInteractor(UpdateChatDataAccessInterface updateChatDataAccessInterface, UpdateChatOutputBoundary presenter) {
        this.updateChatDataAccessObject = updateChatDataAccessInterface;
        this.presenter = presenter;
    }

    /**
     * Execute the Update Chat use case.
     *
     * @param inputData The input data for this use case.
     */
    @Override
    public void execute(UpdateChatInputData inputData) {
        ScheduledExecutorService updateChatExecutor = Executors.newSingleThreadScheduledExecutor();
        ScheduledExecutorService checkIfViewingChatExecutor = Executors.newSingleThreadScheduledExecutor();

        // Check if viewing this chat every 1 second, with 3-second initial delay so that the chat is updated at least once.
        Runnable checkIfViewingChatRunnable = () -> checkIfViewingChat(inputData, updateChatExecutor, checkIfViewingChatExecutor);
        checkIfViewingChatExecutor.scheduleAtFixedRate(checkIfViewingChatRunnable, 3, 1, TimeUnit.SECONDS);

        // Update this chat every 2 seconds
        Runnable updateChatRunnable = () -> updateChat(inputData);
        updateChatExecutor.scheduleAtFixedRate(updateChatRunnable, 0, 2, TimeUnit.SECONDS);

        // Time users out after 20 minutes
        checkIfViewingChatExecutor.schedule(checkIfViewingChatExecutor::shutdownNow, 1200, TimeUnit.SECONDS);
        updateChatExecutor.schedule(updateChatExecutor::shutdownNow, 1200, TimeUnit.SECONDS);
    }

    private void updateChat(UpdateChatInputData inputData) {
        // Load the previous active chat
        Chat previousActiveChat = updateChatDataAccessObject.getActiveChat();
        if (previousActiveChat == null) {
            GroupChatFactory groupChatFactory = new GroupChatFactory();
            previousActiveChat = groupChatFactory.create(new ArrayList<>(), "", new ArrayList<>(), "");
        }

        // Load the new active chat
        String channelUrl = inputData.getChannelUrl();
        Chat newActiveChat = updateChatDataAccessObject.load(channelUrl);

        // Check that the chat exists
        if (newActiveChat == null) {
            UpdateChatOutputData outputData = new UpdateChatOutputData(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), false);
            presenter.prepareFailView("chat not found", outputData);
        }
        else {
            // Save the new chat object in the DAO
            updateChatDataAccessObject.setActiveChat(newActiveChat);

            // Make a list of new messages and a list of deleted messages.
            List<Message> oldMessages = previousActiveChat.getMessageHistory();
            List<Message> updatedMessages = newActiveChat.getMessageHistory();

            List<Message> newMessages = new ArrayList<>(updatedMessages);
            newMessages.removeAll(oldMessages);

            List<Message> removedMessages = new ArrayList<>(oldMessages);
            removedMessages.removeAll(updatedMessages);

            // Make a list of new users and a list of removed users.
            List<String> oldUsers = previousActiveChat.getMemberIDs();
            List<String> updatedUsers = newActiveChat.getMemberIDs();

            List<String> newUsers = new ArrayList<>(updatedUsers);
            newUsers.removeAll(oldUsers);

            List<String> removedUsers = new ArrayList<>(oldUsers);
            removedUsers.removeAll(updatedUsers);

            // Send output data to the presenter
            UpdateChatOutputData outputData = new UpdateChatOutputData(newMessages, removedMessages, newUsers, removedUsers, true);
            presenter.prepareSuccessView(outputData);
        }
    }

    private void checkIfViewingChat(UpdateChatInputData inputData, ScheduledExecutorService updateChatExecutor, ScheduledExecutorService checkIfViewingChatExecutor) {
        if (!updateChatDataAccessObject.getActiveChat().getChannelURL().equals(inputData.getChannelUrl())) {
            updateChatExecutor.shutdownNow();
            checkIfViewingChatExecutor.shutdownNow();
        }
    }

    /**
     * Switches the view so that the chat is shown.
     */
    @Override
    public void switchToChatView() {
        presenter.switchToChatView();
    }

    /**
     * Switches the view to return to the list of chats.
     */
    @Override
    public void leaveChatView() {
        // Set the current chat to null in the DAO
        updateChatDataAccessObject.setActiveChat(null);

        presenter.leaveChatView();
    }
}
