package interfaceadapter.self_chat;

import dataaccess.InMemorySelfChatUserDataAccessObject;
import interfaceadapter.ViewManagerModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import usecase.self_chat.SelfChatInputBoundary;
import usecase.self_chat.SelfChatInputData;
import usecase.self_chat.SelfChatInteractor;
import usecase.self_chat.SelfChatOutputBoundary;

class SelfChatControllerTest {
    final ViewManagerModel viewManagerModel = new ViewManagerModel();
    final SelfChatViewModel selfChatViewModel = new SelfChatViewModel();
    final InMemorySelfChatUserDataAccessObject selfChatDataAccessObject = new InMemorySelfChatUserDataAccessObject();
    final SelfChatOutputBoundary selfChatOutputBoundary = new SelfChatPresenter(selfChatViewModel);
    final SelfChatInputBoundary selfChatInteractor = new SelfChatInteractor(
            selfChatDataAccessObject, selfChatOutputBoundary);

    @Test
    void backToViewChatsTest() {
        final ViewManagerModel viewManagerModelTest = new ViewManagerModel() {
            @Override
            public void firePropertyChanged() {
                Assertions.assertEquals("view chats", getState());
            }
        };

        final SelfChatController selfChatController = new SelfChatController(
                selfChatInteractor, viewManagerModelTest);
        selfChatController.backToViewChats();
    }

    @Test
    void sendMessageTest() {
        final SelfChatInputBoundary selfChatInteractorTest = new SelfChatInteractor(
                selfChatDataAccessObject, selfChatOutputBoundary) {
            @Override
            public void execute(SelfChatInputData selfChatInputData) {
                Assertions.assertEquals("message", selfChatInputData.getMessage());
            }
        };
        final SelfChatController selfChatController = new SelfChatController(
                selfChatInteractorTest, viewManagerModel);
        selfChatController.sendMessage("message");
    }

    @Test
    void clearChatTest() {
        final SelfChatInputBoundary selfChatInteractorTest = new SelfChatInteractor(
                selfChatDataAccessObject, selfChatOutputBoundary) {
            @Override
            public void clearMessages() {
                Assertions.assertTrue(true);
            }
        };
        final SelfChatController selfChatController = new SelfChatController(
                selfChatInteractorTest, viewManagerModel);
        selfChatController.clearChat();
    }

    @Test
    void loadMessagesTest() {
        final SelfChatInputBoundary selfChatInteractorTest = new SelfChatInteractor(
                selfChatDataAccessObject, selfChatOutputBoundary) {
            @Override
            public void loadMessages() {
                Assertions.assertTrue(true);
            }
        };
        final SelfChatController selfChatController = new SelfChatController(
                selfChatInteractorTest, viewManagerModel);
        selfChatController.loadMessages();
    }

    @Test
    void saveBirthdayTest() {
        final SelfChatInputBoundary selfChatInteractorTest = new SelfChatInteractor(
                selfChatDataAccessObject, selfChatOutputBoundary) {
            @Override
            public void saveBirthday(String name, String date) {
                Assertions.assertEquals("name", name);
                Assertions.assertEquals("date", date);
            }
        };
        String name = "name";
        String date = "date";
        final SelfChatController selfChatController = new SelfChatController(
                selfChatInteractorTest, viewManagerModel);
        selfChatController.saveBirthday(name, date);
    }
}
