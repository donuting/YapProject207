package interface_adapter.add_chat;

import interface_adapter.ViewManagerModel;
import use_case.create_chat.CreateChatOutputBoundary;
import use_case.create_chat.CreateChatOutputData;

/**
 * The Presenter for the Add Chat Use Case.
 */
public class AddChatPresenter implements CreateChatOutputBoundary {
    private final AddChatViewModel addChatViewModel;
    private final ViewManagerModel viewManagerModel;

    public AddChatPresenter(ViewManagerModel viewManagerModel, AddChatViewModel addChatViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.addChatViewModel = addChatViewModel;
    }

    @Override
    public void prepareSuccessView(CreateChatOutputData outputData) {
        // Update the view model to show success
        final AddChatState addChatState = addChatViewModel.getState();
        addChatState.setSuccess(true);
        addChatState.setChatNameError("");
        addChatViewModel.setState(addChatState);
        addChatViewModel.firePropertyChanged();

        // Navigate back to view chats after successful creation
        javax.swing.Timer timer = new javax.swing.Timer(1500, e -> {
            viewManagerModel.setState("view chats");
            viewManagerModel.firePropertyChanged();
        });
        timer.setRepeats(false);
        timer.start();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        final AddChatState addChatState = addChatViewModel.getState();
        addChatState.setSuccess(false);
        addChatState.setChatNameError(errorMessage);

        addChatViewModel.firePropertyChanged();
    }

    @Override
    public void switchToChatView() {

    }
}