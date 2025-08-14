package use_case.self_chat;

import data_access.InMemorySelfChatUserDataAccessObject;
import data_access.SendBirdUserDataAccessObject;
import entity.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SelfChatInteractorTest {

    private CommonUser user;
    private CommonMessage message;
    private GroupChat selfChat;
    private InMemorySelfChatUserDataAccessObject dataAccess;

    @BeforeEach
    void setUp() {
        GroupChatFactory groupChatFactory = new GroupChatFactory();
        user = new CommonUser("User", "Password1", "100",
                "Bio", "20250823", new ArrayList<String>(),
                new ArrayList<String>(), new ArrayList<GroupChat>(),
                new ArrayList<GroupChat>());
        dataAccess = mock(InMemorySelfChatUserDataAccessObject.class);
        List<String> membersID = new ArrayList<String>();
        membersID.add("100");
        selfChat = new GroupChat(membersID, "user's self chat", new ArrayList<>(), "chat.com");
        message = new CommonMessage("100", "text", 1, "0000");
        when(dataAccess.getCurrentUser()).thenReturn(user);

    }

    @Test
    void SelfChatInteractorExecuteSuccessTest() {
        when(dataAccess.sendMessage(any())).thenReturn(message);
        SelfChatInputData inputData = new SelfChatInputData("text");
        SelfChatOutputBoundary presenter = new SelfChatOutputBoundary() {
            @Override
            public void presentMessage(SelfChatOutputData outputData) {
                assertEquals(user.getName(), outputData.getUsername());
                assertTrue(outputData.isSuccess());
                Message newMessage = outputData.getMessages().get(0);
                assertEquals(message.GetSenderId(), newMessage.GetSenderId());
                assertEquals(message.GetText(), newMessage.GetText());
            }

            @Override
            public void presentClearResult(boolean success, String errorMessage) {
                fail("Interactor fails execute success case");
            }

            @Override
            public void presentError(String errorMessage) {
                fail("Failed to save message: " + errorMessage);
            }
        };
        SelfChatInteractor interactor = new SelfChatInteractor(dataAccess, presenter);
        interactor.execute(inputData);
    }

    @Test
    void SelfChatInteractorExecuteFailTest() {
        when(dataAccess.sendMessage(message)).thenReturn(null);
        SelfChatInputData inputData = new SelfChatInputData("");
        SelfChatOutputBoundary presenter = new SelfChatOutputBoundary() {
            @Override
            public void presentMessage(SelfChatOutputData outputData) {
                fail("empty text is sent as a message");
            }

            @Override
            public void presentClearResult(boolean success, String errorMessage) {
                fail("Interactor fails execute success case");
            }

            @Override
            public void presentError(String errorMessage) {
                assertEquals("Message cannot be empty", errorMessage);
            }
        };
        SelfChatInteractor interactor = new SelfChatInteractor(dataAccess, presenter);
        interactor.execute(inputData);
    }

    @Test
    void SelfChatInteractorClearMessagesSuccessTest() {
        selfChat.addMessage(message);
        SelfChatOutputBoundary presenter = new SelfChatOutputBoundary() {
            @Override
            public void presentMessage(SelfChatOutputData outputData) {

            }

            @Override
            public void presentClearResult(boolean success, String errorMessage) {
                assertTrue(success);
            }

            @Override
            public void presentError(String errorMessage) {
                fail("Failed to save message: " + errorMessage);
            }
        };
        SelfChatInteractor interactor = new SelfChatInteractor(dataAccess, presenter);
        interactor.clearMessages();
    }

    @Test
    void SelfChatInteractorLoadMessagesSuccessTest() {
        selfChat.addMessage(message);
        when(dataAccess.loadMessages()).thenReturn(selfChat.getMessageHistory());
        SelfChatOutputBoundary presenter = new SelfChatOutputBoundary() {
            @Override
            public void presentMessage(SelfChatOutputData outputData) {
                assertEquals(user.getName(), outputData.getUsername());
                assertTrue(outputData.isSuccess());
                Message newMessage = outputData.getMessages().get(0);
                assertEquals(message, newMessage);
            }

            @Override
            public void presentClearResult(boolean success, String errorMessage) {
                fail("fails load message");
            }

            @Override
            public void presentError(String errorMessage) {
                fail("Failed to save message: " + errorMessage);
            }
        };
        SelfChatInteractor interactor = new SelfChatInteractor(dataAccess, presenter);
        interactor.loadMessages();
    }

    @Test
    void selfChatSaveBirthdaySuccessTest(){
        SelfChatOutputBoundary presenter = new SelfChatOutputBoundary() {

            @Override
            public void presentMessage(SelfChatOutputData outputData) {

            }

            @Override
            public void presentClearResult(boolean success, String errorMessage) {

            }

            @Override
            public void presentError(String errorMessage) {

            }
        };
        SelfChatInteractor interactor = new SelfChatInteractor(dataAccess, presenter);
        interactor.saveBirthday(user.getName(), "20250813");
    }

    @AfterEach
    void tearDown() {
        user = null;
        message = null;
        selfChat = null;
    }

}
