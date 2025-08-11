package use_case.create_chat;

import data_access.GroupChatDataAccessObject;
import data_access.InMemoryUserDataAccessObject;
import entity.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.create_chat.CreateChatInputData;
import use_case.create_chat.CreateChatInteractor;
import use_case.create_chat.CreateChatOutputBoundary;
import use_case.create_chat.CreateChatOutputData;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CreateChatInteractorTest {

    private CommonUser user;
    private InMemoryUserDataAccessObject dataAccess;

    @BeforeEach
    void setUp(){
        List<String> membersID = new ArrayList<>();
        membersID.add("100");
        GroupChat selfChat = new GroupChat(membersID, "User's self chat", new ArrayList<Message>());
        user= new CommonUser("User", "Password1",
                "100", "Bio", "20250808",
                new ArrayList<String>(), new ArrayList<String>(),
                new ArrayList<GroupChat>(), new ArrayList<GroupChat>(),
                selfChat);
        dataAccess = new InMemoryUserDataAccessObject();
        dataAccess.setCurrentUser(user);
        dataAccess.setCurrentUsername(user.getName());
    }

    @Test
    void CreateChatInteractorSuccessTest(){
        dataAccess.save(user);
        CreateChatInputData inputData = new CreateChatInputData("New Chat");
        CreateChatOutputBoundary presenter = new CreateChatOutputBoundary() {
            @Override
            public void prepareSuccessView(CreateChatOutputData outputData) {
                assertEquals("New Chat", outputData.getChatName());
                assertEquals("100", outputData.getUserId());
                List<GroupChat> groupChats = user.getGroupChats();
                assertEquals(1, groupChats.size());
                GroupChat groupChat = groupChats.get(0);
                assertEquals("User's self chat", groupChat.getChatName());
                assert groupChat.getMemberIds().contains("100");
                assertEquals(1, groupChat.getMemberIds().size());
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("The sucess case fails with the interactor");
            }
        };
        CreateChatInteractor interactor = new CreateChatInteractor(dataAccess, presenter, new GroupChatFactory());
        interactor.execute(inputData);
    }

    @Test
    // input chat name is empty
    void CreateChatInteractorFailTest1(){
        dataAccess.save(user);
        CreateChatInputData inputData = new CreateChatInputData("");
        CreateChatOutputBoundary presenter = new CreateChatOutputBoundary() {
            @Override
            public void prepareSuccessView(CreateChatOutputData outputData) {
                fail("Interactor does not check if chat name is empty");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("Please enter a chat name", errorMessage);
            }
        };
        CreateChatInteractor interactor = new CreateChatInteractor(dataAccess, presenter, new GroupChatFactory());
        interactor.execute(inputData);
    }

    @AfterEach
    void tearDown(){
        GroupChatDataAccessObject groupChatDataAccessObject = new GroupChatDataAccessObject();
        for (GroupChat groupChat : user.getGroupChats()) {
            groupChatDataAccessObject.delete(groupChat.getChannelUrl());
        }

        dataAccess.deleteUserById(user.getID(), user.getName());

        dataAccess = null;
        user = null;
    }
}
