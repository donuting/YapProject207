package interface_adapter.create_chat;

import use_case.create_chat.CreateChatOutputBoundary;
import use_case.create_chat.CreateChatOutputData;

public class CreateChatPresenter implements CreateChatOutputBoundary {

    // Todo: Add View instance variables

    public CreateChatPresenter() {
        // Todo: make constructor
    }

    @Override
    public void prepareSuccessView(CreateChatOutputData createChatOutputData) {
        // Todo: implement
    }

    @Override
    public void prepareFailView(String errorMessage) {
        // Todo: implement
    }

    /**
     * Switches the view to the newly created chat.
     */
    @Override
    public void switchToChatView() {
        // Todo: implement
    }
}
