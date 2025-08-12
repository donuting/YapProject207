package usecase.send_message;

import dataaccess.SendBirdUserDataAccessObject;
import entity.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SendMessageInteractorTest {

    private CommonUser user;
    private CommonMessage message;
    private GroupChat chat;
    private SendBirdUserDataAccessObject dataAccess;

    @BeforeEach
    void setUp() {

        List<String> memberIds = new ArrayList<String>();
        memberIds.add("100");
        dataAccess = new SendBirdUserDataAccessObject();
        user = new CommonUser("User", "Password1", "100",
                "Bio", "20250823", new ArrayList<String>(),
                new ArrayList<String>(), new ArrayList<GroupChat>(),
                new ArrayList<GroupChat>());
        dataAccess.save(user);
        message = new CommonMessage("100", "Message");
        chat = dataAccess.create(memberIds, "Group chat", new GroupChatFactory());
        dataAccess.addUser(user.getID(), chat.getChannelUrl());
    }

    @Test
    void sendMessageInteractorSuccessTest() {
        SendMessageInputData inputData = new SendMessageInputData(message.GetText());
        SendMessageOutputBoundary presenter = new SendMessageOutputBoundary() {
            @Override
            public void prepareSuccessSendMessageView(SendMessageOutputData sendMessageOutputData) {
                assertFalse(sendMessageOutputData.isUseCaseFailed());
                CommonMessage outputMessage = (CommonMessage) sendMessageOutputData.getMessage();
                assertEquals(message.GetText(), outputMessage.GetText());
                assertEquals(message.GetSenderId(), outputMessage.GetSenderId());
                assertFalse(chat.getMessageHistory().isEmpty());
                assertEquals(chat.getMessageHistory().get(0), outputMessage);
            }

            @Override
            public void prepareFailSendMessageView(String errorMessage, SendMessageOutputData sendMessageOutputData) {
                fail("Interactor fails success case");
            }
        };
        SendMessageInteractor interactor = new SendMessageInteractor(dataAccess, presenter, new CommonMessageFactory());
        interactor.execute(inputData);
    }

    @Test
    // empty message
    void sendMessageInteractorFailTest() {
        SendMessageInputData inputData = new SendMessageInputData("");
        SendMessageOutputBoundary presenter = new SendMessageOutputBoundary() {
            @Override
            public void prepareSuccessSendMessageView(SendMessageOutputData sendMessageOutputData) {
                fail("Interactor does not check if the message is empty");

            }

            @Override
            public void prepareFailSendMessageView(String errorMessage, SendMessageOutputData sendMessageOutputData) {
                assertTrue(sendMessageOutputData.isUseCaseFailed());
                assertTrue(chat.getMessageHistory().isEmpty());
            }
        };
        SendMessageInteractor interactor = new SendMessageInteractor(dataAccess, presenter, new CommonMessageFactory());
        interactor.execute(inputData);
    }

    @AfterEach
    void tearDown() {
        dataAccess.deleteMessage(message.GetMID().toString(), chat);
        dataAccess.deleteGroupChat(chat);
        dataAccess.deleteUserById(user.getID(), user.getName());
        dataAccess = null;
        message = null;
        chat = null;
        user = null;

    }
}
