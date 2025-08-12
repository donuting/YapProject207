package usecase.create_chat;

import entity.GroupChat;
import entity.GroupChatFactory;
import entity.User;

import java.util.ArrayList;
import java.util.List;

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
            return;
        }

        if (createChatInputData.getChatName() == null || createChatInputData.getChatName().trim().isEmpty()) {
            groupChatPresenter.prepareFailView("Please enter a chat name");
            return;
        }

        // Create member list starting with current user
        List<String> memberIDs = new ArrayList<>();
        String currentUserID = currentUser.getID();
        memberIDs.add(currentUserID);

        // NEW: Add the other user if username is provided
        String otherUsername = createChatInputData.getUsername();
        if (otherUsername != null && !otherUsername.trim().isEmpty()) {
            // Check if the user exists
            if (!userDataAccessObject.existsByName(otherUsername.trim())) {
                groupChatPresenter.prepareFailView("User '" + otherUsername.trim() + "' does not exist");
                return;
            }

            // Check if trying to add themselves
            if (otherUsername.trim().equals(currentUser.getName())) {
                groupChatPresenter.prepareFailView("You cannot add yourself to the chat");
                return;
            }

            // Get the other user and add their ID
            User otherUser = userDataAccessObject.get(otherUsername.trim());
            if (otherUser != null) {
                memberIDs.add(otherUser.getID());
            }
        }

        // Create the group chat
        final GroupChat newGroupChat = userDataAccessObject.create(memberIDs, createChatInputData.getChatName().trim(), groupChatFactory);

        if (newGroupChat == null) {
            groupChatPresenter.prepareFailView("Failed to create chat. Please try again.");
            return;
        }

        // Save the group chat to the current user
        String username = currentUser.getName();
        userDataAccessObject.saveGroupChat(newGroupChat, username);
        currentUser.addGroupChat(newGroupChat);

        // NEW: If another user was added, save the chat to them too
        if (otherUsername != null && !otherUsername.trim().isEmpty()) {
            User otherUser = userDataAccessObject.get(otherUsername.trim());
            if (otherUser != null) {
                userDataAccessObject.saveGroupChat(newGroupChat, otherUser.getName());
                otherUser.addGroupChat(newGroupChat);
            }
        }

        final CreateChatOutputData createChatOutputData = new CreateChatOutputData(
                newGroupChat.getChatName(),
                currentUserID,
                otherUsername  // Pass the added username to output
        );
        groupChatPresenter.prepareSuccessView(createChatOutputData);
    }
}