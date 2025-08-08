package use_case.update_chat;

import entity.GroupChat;
import entity.Message;
import entity.User;

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
    public void execute(UpdateChatInputData updateChatInputData) {
        User currentUser = updateChatDataAccessObject.getCurrentUser();
        String channelUrl = updateChatInputData.getChannelUrl();

        // Set to viewing this chat
        for (GroupChat groupChat : currentUser.getGroupChats()) {
            if (groupChat.getChannelUrl().equals(channelUrl)) {
                updateChatDataAccessObject.setActiveGroupChat(groupChat);
            }
        }
        for (GroupChat personalChat : currentUser.getPersonalChats()) {
            if (personalChat.getChannelUrl().equals(channelUrl)) {
                updateChatDataAccessObject.setActiveGroupChat(personalChat);
            }
        }

        ScheduledExecutorService updateChatExecutor = Executors.newSingleThreadScheduledExecutor();
        ScheduledExecutorService checkIfViewingChatExecutor = Executors.newSingleThreadScheduledExecutor();

        // Check if viewing this chat every 0.5 seconds, with 2-second initial delay so that the chat is updated at least once.
        Runnable checkIfViewingChatRunnable = () -> checkIfViewingChat(channelUrl, updateChatExecutor, checkIfViewingChatExecutor);
        checkIfViewingChatExecutor.scheduleAtFixedRate(checkIfViewingChatRunnable, 2000L, 500L, TimeUnit.MILLISECONDS);

        // Update this chat every 4 seconds
        Runnable updateChatRunnable = () -> updateChat(channelUrl);
        updateChatExecutor.scheduleAtFixedRate(updateChatRunnable, 0L, 4L, TimeUnit.SECONDS);

        // Time users out after 20 minutes
        checkIfViewingChatExecutor.schedule(checkIfViewingChatExecutor::shutdownNow, 1200L, TimeUnit.SECONDS);
        updateChatExecutor.schedule(updateChatExecutor::shutdownNow, 1200L, TimeUnit.SECONDS);
    }

    private void updateChat(String channelUrl) {
        // Load the new active chat
        GroupChat updatedChat = updateChatDataAccessObject.load(channelUrl);

        // Check that the chat exists
        if (updatedChat == null) {
            UpdateChatOutputData outputData = new UpdateChatOutputData(null, new ArrayList<>(), new ArrayList<>(), false);
            presenter.updateChatPrepareFailView("chat not found", outputData);
        }
        else {
            // Update the new chat object in the DAO
            GroupChat activeChat = updateChatDataAccessObject.getActiveGroupChat();
            activeChat.setChatName(updatedChat.getChatName());
            activeChat.setMessageHistory(updatedChat.getMessageHistory());
            activeChat.setMemberIds(updatedChat.getMemberIds());

            // Make a list of new messages
            List<Message> updatedMessages = updatedChat.getMessageHistory();

            // Make a list of users
            List<String> updatedUsers = updatedChat.getMemberIds();

            // Send output data to the presenter
            String currentUserId = updateChatDataAccessObject.getCurrentUser().getID();
            UpdateChatOutputData outputData = new UpdateChatOutputData(currentUserId, updatedMessages, updatedUsers, true);
            presenter.updateChatPrepareSuccessView(outputData);
        }
    }

    private void checkIfViewingChat(String channelUrl, ScheduledExecutorService updateChatExecutor, ScheduledExecutorService checkIfViewingChatExecutor) {
        if (updateChatDataAccessObject.getActiveGroupChat() == null ||
                !updateChatDataAccessObject.getActiveGroupChat().getChannelUrl().equals(channelUrl)) {
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
