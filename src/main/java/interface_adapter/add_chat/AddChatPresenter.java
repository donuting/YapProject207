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
        addChatState.setUsernameError("");  // NEW: Clear username error
        addChatState.setChatName(outputData.getChatName());
        addChatState.setUsername(outputData.getAddedUsername());  // NEW: Set added username
        addChatState.setID(outputData.getUserId());
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

        // NEW: Determine which field has the error based on the message
        if (errorMessage.contains("chat name") || errorMessage.contains("enter a chat name")) {
            addChatState.setChatNameError(errorMessage);
            addChatState.setUsernameError("");  // Clear the other error
        } else if (errorMessage.contains("User") && errorMessage.contains("does not exist")) {
            addChatState.setUsernameError(errorMessage);
            addChatState.setChatNameError("");  // Clear the other error
        } else if (errorMessage.contains("cannot add yourself")) {
            addChatState.setUsernameError(errorMessage);
            addChatState.setChatNameError("");  // Clear the other error
        } else {
            // General error - show in chat name error field
            addChatState.setChatNameError(errorMessage);
            addChatState.setUsernameError("");
        }

        addChatViewModel.firePropertyChanged();
    }
}