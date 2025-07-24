package use_case.create_chat;


import entity.GroupChat;
import entity.GroupChatFactory;
import entity.User;

import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.List;

public class CreateChatInteractor implements CreateChatInputBoundary {
    private final CreateChatUserDataAccessInterface groupChatDataAccessObject;
    private final CreateChatOutputBoundary groupChatPresenter;
    private final GroupChatFactory groupChatFactory;

    public CreateChatInteractor(CreateChatUserDataAccessInterface groupChatDataAccessObject,
                                CreateChatOutputBoundary groupChatPresenter,
                                GroupChatFactory chatFactory) {
        this.groupChatDataAccessObject = groupChatDataAccessObject;
        this.groupChatPresenter = groupChatPresenter;
        this.groupChatFactory = chatFactory;
    }

    @Override
    public void execute(CreateChatInputData createChatInputData) {
        List<String> memberIDs = new ArrayList<String>();
        String ID = createChatInputData.getID();
        memberIDs.add(ID); // only the user who created the chat is a member
        final GroupChat newGroupChat = groupChatDataAccessObject.create(memberIDs, createChatInputData.getChatName(), groupChatFactory);

        // Add the group chat to the user's list of group chats
        // user.addGroupChat(newGroupChat);

        final CreateChatOutputData createChatOutputData = new CreateChatOutputData(); // Todo: decide what data needs to be passed to the presenter
        groupChatPresenter.prepareSuccessView(createChatOutputData);
    }

    @Override
    public void switchToChatView() {
        groupChatPresenter.switchToChatView();
    }
}
