package use_case.delete_message;

import data_access.SendBirdUserDataAccessObject;
import entity.CommonMessage;
import entity.GroupChat;
import entity.GroupChatFactory;
import entity.Message;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DeleteMessageInteractorTest {


    private GroupChat chat;
    private CommonMessage message;
    private SendBirdUserDataAccessObject dataAccess;

    @BeforeEach
    void setUp() {
        List<String> memberIds = new ArrayList<String>();
        memberIds.add("100");
        message = new CommonMessage("100", "Message", 10, "2316");
        dataAccess = new SendBirdUserDataAccessObject();
        chat = dataAccess.create(memberIds, "Group Chat", new GroupChatFactory());

    }

    @Test
    // success case for the delete message use case
    void DeleteMessageInteractorSuccessTest() {
        dataAccess.sendMessage(message, chat);
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

    @AfterEach
    void tearDown() {
        if (!dataAccess.load(chat.getChannelUrl()).getMessageHistory().isEmpty()){
            dataAccess.deleteMessage(message.GetMID().toString(), chat);
        }
        dataAccess.deleteGroupChat(chat);
        dataAccess = null;
        message = null;
        chat = null;

    }

}
