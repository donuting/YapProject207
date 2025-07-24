package use_case.create_chat;


import data_access.SendBirdUserDataAccessObject;
import entity.GroupChat;
import entity.GroupChatFactory;
import entity.User;
import entity.UserFactory;

import java.util.ArrayList;
import java.util.List;

public class CreateChatInteractor implements CreateChatInputBoundary {
    private final CreateChatUserDataAccessInterface groupChatDataAccessObject;
    private final CreateChatOutputBoundary groupChatPresenter;
    private final GroupChatFactory groupChatFactory;
    private final SendBirdUserDataAccessObject sendBirdUserDataAccessObject;


    public CreateChatInteractor(CreateChatUserDataAccessInterface groupChatDataAccessObject,
                                CreateChatOutputBoundary groupChatPresenter,
                                GroupChatFactory chatFactory,
                                SendBirdUserDataAccessObject sendBirdUserDataAccessObject
                                ) {
        this.groupChatDataAccessObject = groupChatDataAccessObject;
        this.groupChatPresenter = groupChatPresenter;
        this.groupChatFactory = chatFactory;
        this.sendBirdUserDataAccessObject = sendBirdUserDataAccessObject;
    }

    @Override
    public void execute(CreateChatInputData createChatInputData) {
        List<String> memberIDs = new ArrayList<>();
        User user = sendBirdUserDataAccessObject.get(sendBirdUserDataAccessObject.getCurrentUsername()); // The logged-in user is creating the chat.
        memberIDs.add(user.getID()); // only the user who created the chat is a member
        final GroupChat newGroupChat = groupChatDataAccessObject.create(memberIDs, createChatInputData.getChatName(), groupChatFactory);

        // Add the group chat to the user's list of group chats
        user.addGroupChat(newGroupChat);

        final CreateChatOutputData createChatOutputData = new CreateChatOutputData(); // Todo: decide what data needs to be passed to the presenter
        groupChatPresenter.prepareSuccessView(createChatOutputData);
    }

    @Override
    public void switchToChatView() {
        groupChatPresenter.switchToChatView();
    }
}
