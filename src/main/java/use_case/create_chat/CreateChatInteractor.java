package use_case.create_chat;


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

        // Create the group chat in SendBird
        List<String> memberIDs = new ArrayList<>();
        String currentUserID = currentUser.getID();
        memberIDs.add(currentUserID); // only the current user is a member initially
        final GroupChat newGroupChat = userDataAccessObject.create(memberIDs, createChatInputData.getChatName(), groupChatFactory);

        // Save the group chat to the user.
        String username = currentUser.getName();
        userDataAccessObject.saveGroupChat(newGroupChat, username);
        currentUser.addGroupChat(newGroupChat);

        final CreateChatOutputData createChatOutputData = new CreateChatOutputData(newGroupChat.getChatName(), currentUserID);
        groupChatPresenter.prepareSuccessView(createChatOutputData);
    }
}
