package usecase.self_chat;

import dataaccess.InMemorySelfChatUserDataAccessObject;
import dataaccess.SendBirdUserDataAccessObject;
import entity.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SelfChatInteractorTest {

    private CommonUser user;
    private CommonMessage message;
    private GroupChat selfChat;
    private InMemorySelfChatUserDataAccessObject dataAccess;
    private SendBirdUserDataAccessObject sendBirdUserDataAccessObject;

    @BeforeEach
    void setUp() {
        GroupChatFactory groupChatFactory = new GroupChatFactory();
        user = new CommonUser("User", "Password1", "100",
                "Bio", "20250823", new ArrayList<String>(),
                new ArrayList<String>(), new ArrayList<GroupChat>(),
                new ArrayList<GroupChat>());
        dataAccess = new InMemorySelfChatUserDataAccessObject();
        sendBirdUserDataAccessObject = new SendBirdUserDataAccessObject();
        List<String> membersID = new ArrayList<String>();
        membersID.add("100");
        selfChat = sendBirdUserDataAccessObject.createSelfChat(membersID, "user's self chat") ;
        message = new CommonMessage("100", "text", 1, "0000");
        sendBirdUserDataAccessObject.save(user);
        sendBirdUserDataAccessObject.setCurrentUser(user);
        sendBirdUserDataAccessObject.setCurrentUsername(user.getName());
    }

    @Test
    void SelfChatInteractorExecuteSuccessTest() {
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
        dataAccess.sendMessage(message);
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
        dataAccess.sendMessage(message);
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
        interactor.clearMessages();
    }

    @AfterEach
    void tearDown() {
        sendBirdUserDataAccessObject.deleteUserById(user.getID(), user.getName());
        sendBirdUserDataAccessObject.deleteMessage("1", selfChat);
        sendBirdUserDataAccessObject.deleteGroupChat(selfChat);
        user = null;
        message = null;
        selfChat = null;
    }

}
