package usecase.create_chat;

import java.util.ArrayList;
import java.util.List;

import entity.GroupChat;
import entity.GroupChatFactory;
import entity.User;

public class CreateChatInteractor implements CreateChatInputBoundary {
    private final CreateChatUserDataAccessInterface userDataAccessObject;
    private final CreateChatOutputBoundary groupChatPresenter;
    private final GroupChatFactory groupChatFactory;

    public CreateChatInteractor(CreateChatUserDataAccessInterface groupChatDataAccessObject,
                                CreateChatOutputBoundary groupChatPresenter,
                                GroupChatFactory chatFactory) {
        this.userDataAccessObject = groupChatDataAccessObject;
        this.groupChatPresenter = groupChatPresenter;
        this.groupChatFactory = chatFactory;
    }

    @Override
    public void execute(CreateChatInputData createChatInputData) {
        User currentUser = userDataAccessObject.getCurrentUser();
        if (currentUser == null) {
            groupChatPresenter.prepareFailView("The current user does not exist");
        }

        else if (checkIfEmpty(createChatInputData.getChatName())) {
            groupChatPresenter.prepareFailView("Please enter a chat name");
        }
        else {
            // Create member list starting with current user
            List<String> memberIds = new ArrayList<>();
            String currentUserId = currentUser.getID();
            memberIds.add(currentUserId);

            // NEW: Add the other user if username is provided
            // Check if the user exists
            String otherUsername = createChatInputData.getUsername();
            if (checkIfEmpty(otherUsername)) {
                groupChatPresenter.prepareFailView("User does not exist");
            }
            else if (!userDataAccessObject.existsByName(otherUsername.trim())) {
                groupChatPresenter.prepareFailView("User '" + otherUsername.trim() + "' does not exist");
            }

            // Check if trying to add themselves
            else if (otherUsername.trim().equals(currentUser.getName())) {
                groupChatPresenter.prepareFailView("You cannot add yourself to the chat");

            }
            else {
                // Get the other user and add their ID
                User otherUser = userDataAccessObject.get(otherUsername.trim());
                if (otherUser != null) {
                    memberIds.add(otherUser.getID());
                }

                // Create the group chat
                final GroupChat newGroupChat = userDataAccessObject.create(
                        memberIds, createChatInputData.getChatName().trim(), groupChatFactory);

                if (newGroupChat == null) {
                    groupChatPresenter.prepareFailView("Failed to create chat. Please try again.");
                }
                else {

                    // Save the group chat to the current user
                    String username = currentUser.getName();
                    userDataAccessObject.saveGroupChat(newGroupChat, username);
                    currentUser.addGroupChat(newGroupChat);

                    // NEW: If another user was added, save the chat to them too
                    if (otherUser != null) {
                        userDataAccessObject.saveGroupChat(newGroupChat, otherUser.getName());
                        otherUser.addGroupChat(newGroupChat);
                    }

                    // Pass the added username to output
                    final CreateChatOutputData createChatOutputData = new CreateChatOutputData(
                            newGroupChat.getChatName(),
                            currentUserId,
                            otherUsername);
                    groupChatPresenter.prepareSuccessView(createChatOutputData);
                }
            }
        }
    }

    private static boolean checkIfEmpty(String otherUsername) {
        return otherUsername == null || otherUsername.trim().isEmpty();
    }
}
