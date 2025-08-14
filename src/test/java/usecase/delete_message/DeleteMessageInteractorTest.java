package usecase.delete_message;

import dataaccess.SendBirdUserDataAccessObject;
import entity.CommonMessage;
import entity.GroupChat;
import entity.Message;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DeleteMessageInteractorTest {


    private GroupChat chat;
    private CommonMessage message;
    private SendBirdUserDataAccessObject dataAccess = mock(SendBirdUserDataAccessObject.class);

    @BeforeEach
    void setUp() {
        List<String> memberIds = new ArrayList<String>();
        memberIds.add("100");
        message = new CommonMessage("100", "Message", 10, "2316");
        chat = new GroupChat(memberIds, "group chat", new ArrayList<>(), "chat.com");

    }

    @Test
    // success case for the delete message use case
    void DeleteMessageInteractorSuccessTest() {
        chat.addMessage(message);
        when(dataAccess.getActiveGroupChat()).thenReturn(chat);
        when(dataAccess.deleteMessage(message.GetMID().toString(), chat)).thenReturn(true);
        DeleteMessageInputData inputData = new DeleteMessageInputData(message.GetMID().toString());
        DeleteMessageOutputBoundary presenter = new DeleteMessageOutputBoundary() {
            @Override
            public void prepareSuccessDeleteMessageView(DeleteMessageOutputData deleteMessageOutputData) {
                assertEquals(message.GetMID(), Integer.valueOf(deleteMessageOutputData.getMessageId()));
                assertFalse(deleteMessageOutputData.isUseCaseFailed());
                List<Message> messageHistory = chat.getMessageHistory();
                assertTrue(messageHistory.isEmpty());
            }

            @Override
            public void prepareFailDeleteMessageView(String errorMessage, DeleteMessageOutputData deleteMessageOutputData) {
                fail("Delete message fails on success case");
            }
        };
        DeleteMessageInteractor interactor = new DeleteMessageInteractor(dataAccess, presenter);
        interactor.execute(inputData);
    }

    @Test
        // fail case for the delete message use case
    void DeleteMessageInteractorFailTest() {
        chat.addMessage(message);
        when(dataAccess.getActiveGroupChat()).thenReturn(chat);
        when(dataAccess.deleteMessage(message.GetMID().toString(), chat)).thenReturn(false);
        DeleteMessageInputData inputData = new DeleteMessageInputData(message.GetMID().toString());
        DeleteMessageOutputBoundary presenter = new DeleteMessageOutputBoundary() {
            @Override
            public void prepareSuccessDeleteMessageView(DeleteMessageOutputData deleteMessageOutputData) {
                fail("Delete message succeeds on fail case");
            }

            @Override
            public void prepareFailDeleteMessageView(String errorMessage, DeleteMessageOutputData deleteMessageOutputData) {
                assertEquals("Delete Message Failed", errorMessage);
                assertNull(deleteMessageOutputData.getMessageId());
                assertTrue(deleteMessageOutputData.isUseCaseFailed());
                List<Message> messageHistory = chat.getMessageHistory();
                assertTrue(messageHistory.contains(message));
            }
        };
        DeleteMessageInteractor interactor = new DeleteMessageInteractor(dataAccess, presenter);
        interactor.execute(inputData);
    }

    @AfterEach
    void tearDown() {
        dataAccess = null;
        message = null;
        chat = null;

    }

}
