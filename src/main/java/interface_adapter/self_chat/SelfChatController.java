package interface_adapter.self_chat;

import interface_adapter.ViewManagerModel;
import use_case.self_chat.SelfChatInputBoundary;
import use_case.self_chat.SelfChatInputData;

/**
 * The controller for the Self Chat Use Case.
 */
public class SelfChatController {

    private final SelfChatInputBoundary selfChatUseCaseInteractor;
    private final ViewManagerModel viewManagerModel;

    public SelfChatController(SelfChatInputBoundary selfChatUseCaseInteractor,
                              ViewManagerModel viewManagerModel) {
        this.selfChatUseCaseInteractor = selfChatUseCaseInteractor;
        this.viewManagerModel = viewManagerModel;
    }

    /**
     * Executes the Self Chat Use Case to send a message.
     * @param message the message to send to self
     */
    public void sendMessage(String message) {
        final SelfChatInputData selfChatInputData = new SelfChatInputData(message);
        selfChatUseCaseInteractor.execute(selfChatInputData);
    }

    /**
     * Clears all messages in the self chat.
     */
    public void clearChat() {
        selfChatUseCaseInteractor.clearMessages();
    }

    /**
     * Navigates back to the View Chats screen.
     */
    public void backToViewChats() {
        viewManagerModel.setState("view chats");
        viewManagerModel.firePropertyChanged();
    }

    /**
     * Loads existing messages when the view is opened.
     */
    public void loadMessages() {
        selfChatUseCaseInteractor.loadMessages();
    }

    /**
     * Saves a birthday entry to the self chat.
     * @param name the name of the person
     * @param date the birthday in YYYYMMDD format
     */
    public void saveBirthday(String name, String date) {
        selfChatUseCaseInteractor.saveBirthday(name, date);
    }
}
