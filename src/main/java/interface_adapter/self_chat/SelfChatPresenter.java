package interface_adapter.self_chat;

import use_case.self_chat.SelfChatOutputBoundary;
import use_case.self_chat.SelfChatOutputData;

/**
 * The Presenter for the Self Chat Use Case.
 */
public class SelfChatPresenter implements SelfChatOutputBoundary {

    private final SelfChatViewModel selfChatViewModel;

    public SelfChatPresenter(SelfChatViewModel selfChatViewModel) {
        this.selfChatViewModel = selfChatViewModel;
    }

    @Override
    public void presentMessage(SelfChatOutputData outputData) {
        if (outputData.isSuccess()) {
            // Message was successfully added
            selfChatViewModel.addMessage(outputData.getMessage());
        } else {
            // There was an error
            selfChatViewModel.setErrorMessage(outputData.getErrorMessage());
        }
    }

    @Override
    public void presentClearResult(boolean success, String errorMessage) {
        if (success) {
            selfChatViewModel.clearMessages();
        } else {
            selfChatViewModel.setErrorMessage(errorMessage);
        }
    }

    @Override
    public void presentLoadedMessages(SelfChatOutputData outputData) {
        if (outputData.isSuccess()) {
            // Update the view model with loaded messages
            selfChatViewModel.setState(new SelfChatState());
            for (int i = 0; i < outputData.getAllMessages().size(); i++) {
                String message = outputData.getAllMessages().get(i);
                selfChatViewModel.addMessage(message);
            }
        } else {
            selfChatViewModel.setErrorMessage(outputData.getErrorMessage());
        }
    }

    @Override
    public void presentError(String errorMessage) {
        selfChatViewModel.setErrorMessage(errorMessage);
    }
}