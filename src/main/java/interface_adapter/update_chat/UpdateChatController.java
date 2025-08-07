package interface_adapter.update_chat;

import use_case.update_chat.UpdateChatInputBoundary;
import use_case.update_chat.UpdateChatInputData;

public class UpdateChatController {
    private final UpdateChatInputBoundary updateChatUseCaseInteractor;

    public UpdateChatController(UpdateChatInputBoundary updateChatUseCaseInteractor) {
        this.updateChatUseCaseInteractor = updateChatUseCaseInteractor;
    }

    /**
     * Execute the Update Chat use case.
     * @param channelUrl The Url of the channel to update.
     */
    void execute(String channelUrl) {
        UpdateChatInputData updateChatInputData = new UpdateChatInputData(channelUrl);
        updateChatUseCaseInteractor.execute(updateChatInputData);
    }


    /**
     * Switches the view so that the chat is shown.
     */
    void switchToChatView() {
        updateChatUseCaseInteractor.switchToChatView();
    }

    /**
     * Switches the view to return to the list of chats
     */
    void leaveChatView() {
        updateChatUseCaseInteractor.leaveChatView();
    }
}
