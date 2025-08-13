package use_case.create_chat;

import data_access.GroupChatDataAccessObject;
import data_access.InMemoryUserDataAccessObject;
import data_access.SendBirdUserDataAccessObject;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CreateChatInteractorTest {

    private CommonUser user;
    private CommonUser friend;
    private SendBirdUserDataAccessObject dataAccess;
    private GroupChat selfChat;
    private GroupChat groupChat;
    private GroupChatFactory groupChatFactory;


    @BeforeEach
    void setUp(){
        List<String> membersID = new ArrayList<>();
        membersID.add("100");
        selfChat = new GroupChat(membersID, "User's self chat", new ArrayList<Message>());
        user= new CommonUser("User", "Password1",
                "100", "Bio", "20250808",
                new ArrayList<String>(), new ArrayList<String>(),
                new ArrayList<GroupChat>(), new ArrayList<GroupChat>());
        friend = new CommonUser("Friend", "Password1", "200",
                "Bio", "20250823", new ArrayList<String>(),
                new ArrayList<String>(), new ArrayList<GroupChat>(),
                new ArrayList<GroupChat>());
        membersID.add("200");
        groupChat = new GroupChat(membersID, "New Chat", new ArrayList<>());
        dataAccess = mock(SendBirdUserDataAccessObject.class);
        groupChatFactory = new GroupChatFactory();

    }

    @Test
    void CreateChatInteractorSuccessTest1(){
        when(dataAccess.existsByName(user.getName())).thenReturn(true);
        when(dataAccess.get(user.getName())).thenReturn(user);
        when(dataAccess.getCurrentUser()).thenReturn(user);
        when(dataAccess.getCurrentUsername()).thenReturn(user.getName());
        when(dataAccess.create(anyList(),
                eq(selfChat.getChatName()), any(GroupChatFactory.class))).thenReturn(selfChat);

        CreateChatInputData inputData = new CreateChatInputData(selfChat.getChatName());
        CreateChatOutputBoundary presenter = new CreateChatOutputBoundary() {
            @Override
            public void prepareSuccessView(CreateChatOutputData outputData) {
                assertEquals(selfChat.getChatName(), outputData.getChatName());
                assertEquals("100", outputData.getUserId());
                assertEquals("", outputData.getAddedUsername());
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("The sucess case fails with the interactor");
            }
        };
        CreateChatInteractor interactor = new CreateChatInteractor(dataAccess, presenter, groupChatFactory);
        interactor.execute(inputData);
    }

    @Test
    void CreateChatInteractorSuccessTest2(){
        when(dataAccess.existsByName(user.getName())).thenReturn(true);
        when(dataAccess.existsByName(friend.getName())).thenReturn(true);
        when(dataAccess.get(friend.getName())).thenReturn(friend);
        when(dataAccess.get(user.getName())).thenReturn(user);
        when(dataAccess.getCurrentUser()).thenReturn(user);
        when(dataAccess.getCurrentUsername()).thenReturn(user.getName());
        when(dataAccess.create(anyList(),
                eq(groupChat.getChatName()), any(GroupChatFactory.class))).thenReturn(groupChat);

        CreateChatInputData inputData = new CreateChatInputData(groupChat.getChatName(), friend.getName());
        CreateChatOutputBoundary presenter = new CreateChatOutputBoundary() {
            @Override
            public void prepareSuccessView(CreateChatOutputData outputData) {
                assertEquals(groupChat.getChatName(), outputData.getChatName());
                assertEquals("100", outputData.getUserId());
                assertEquals(friend.getName(), outputData.getAddedUsername());
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
        when(dataAccess.existsByName(user.getName())).thenReturn(true);
        when(dataAccess.get(user.getName())).thenReturn(user);
        when(dataAccess.getCurrentUser()).thenReturn(user);
        when(dataAccess.getCurrentUsername()).thenReturn(user.getName());
        when(dataAccess.create(anyList(),
                eq(""), any(GroupChatFactory.class))).thenReturn(null);
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

    @Test
        // other user does not exists
    void CreateChatInteractorFailTest2(){
        when(dataAccess.existsByName(user.getName())).thenReturn(true);
        when(dataAccess.get(user.getName())).thenReturn(user);
        when(dataAccess.get(friend.getName())).thenReturn(null);
        when(dataAccess.getCurrentUser()).thenReturn(user);
        when(dataAccess.getCurrentUsername()).thenReturn(user.getName());
        when(dataAccess.create(anyList(),
                eq(groupChat.getChatName()), any(GroupChatFactory.class))).thenReturn(null);
        CreateChatInputData inputData = new CreateChatInputData(groupChat.getChatName(), friend.getName());
        CreateChatOutputBoundary presenter = new CreateChatOutputBoundary() {
            @Override
            public void prepareSuccessView(CreateChatOutputData outputData) {
                fail("Interactor does not check if teh user exists");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("User '" + friend.getName() + "' does not exist", errorMessage);
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
