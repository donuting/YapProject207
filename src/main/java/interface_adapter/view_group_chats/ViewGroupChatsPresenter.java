package interface_adapter.view_group_chats;

import entity.GroupChat;
import use_case.join_chat.JoinChatOutputBoundary;
import use_case.join_chat.JoinChatOutputData;
import use_case.leave_chat.LeaveChatOutputBoundary;
import use_case.leave_chat.LeaveChatOutputData;
import use_case.load_group_chats.LoadGroupChatsOutputBoundary;
import use_case.load_group_chats.LoadGroupChatsOutputData;

import java.util.Map;

public class ViewGroupChatsPresenter implements JoinChatOutputBoundary,
        LeaveChatOutputBoundary, LoadGroupChatsOutputBoundary {
    private final ViewGroupChatsViewModel viewGroupChatsViewModel;

    public ViewGroupChatsPresenter(ViewGroupChatsViewModel viewGroupChatsViewModel) {
        this.viewGroupChatsViewModel = viewGroupChatsViewModel;
    }

    /**
     * Prepare the success view for the Join Chat use case.
     *
     * @param outputData Output data.
     */
    @Override
    public void joinChatPrepareSuccessView(JoinChatOutputData outputData) {
        // Updated the state object with the newly joined chat
        GroupChat groupChat = outputData.getGroupChat();
        final ViewGroupChatsState viewGroupChatsState = viewGroupChatsViewModel.getState();
        Map<String, String> channelInfo = viewGroupChatsState.getChannelInfo();
        channelInfo.put(groupChat.getChannelUrl(), groupChat.getChatName());
        viewGroupChatsState.setChannelInfo(channelInfo);

        // Update the view model
        viewGroupChatsViewModel.setState(viewGroupChatsState);
        viewGroupChatsViewModel.firePropertyChanged();
    }

    /**
     * Prepare the fail view for the Update Chat use case.
     *
     * @param errorMessage Explanation of failure.
     * @param outputData   Output data.
     */
    @Override
    public void joinChatPrepareFailView(String errorMessage, JoinChatOutputData outputData) {
        final ViewGroupChatsState viewGroupChatsState = viewGroupChatsViewModel.getState();
        viewGroupChatsState.setErrorMessage(errorMessage);
        viewGroupChatsViewModel.setState(viewGroupChatsState);
        viewGroupChatsViewModel.firePropertyChanged();
    }

    /**
     * Prepare the fail view for the Leave Chat use case.
     *
     * @param errorMessage Explanation of failure.
     * @param outputData   Output data.
     */
    @Override
    public void leaveChatPrepareFailView(String errorMessage, LeaveChatOutputData outputData) {
        final ViewGroupChatsState viewGroupChatsState = viewGroupChatsViewModel.getState();
        viewGroupChatsState.setErrorMessage(errorMessage);
        viewGroupChatsViewModel.setState(viewGroupChatsState);
        viewGroupChatsViewModel.firePropertyChanged();
    }

    /**
     * Prepare the success view for the Leave Chat use case.
     *
     * @param outputData Output data.
     */
    @Override
    public void leaveChatPrepareSuccessView(LeaveChatOutputData outputData) {
        // Remove the chat from the state object
        final ViewGroupChatsState viewGroupChatsState = viewGroupChatsViewModel.getState();
        Map<String, String> channelInfo = viewGroupChatsState.getChannelInfo();
        channelInfo.remove(outputData.getChannelUrl());
        viewGroupChatsState.setChannelInfo(channelInfo);

        // Update the view model
        viewGroupChatsViewModel.setState(viewGroupChatsState);
        viewGroupChatsViewModel.firePropertyChanged();
    }

    /**
     * Prepare the fail view for the Update Chat use case.
     *
     * @param errorMessage Explanation of failure.
     */
    @Override
    public void loadGroupChatsPrepareFailView(String errorMessage) {
        final ViewGroupChatsState viewGroupChatsState = viewGroupChatsViewModel.getState();
        viewGroupChatsState.setErrorMessage(errorMessage);
        // We just updated the group chat info, so we no longer need to update it.
        viewGroupChatsState.setNeedsGroupChatInfo(false);
        viewGroupChatsViewModel.setState(viewGroupChatsState);
        viewGroupChatsViewModel.firePropertyChanged();
    }

    /**
     * Prepare the success view for the Leave Chat use case.
     *
     * @param outputData Output data.
     */
    @Override
    public void loadGroupChatsPrepareSuccessView(LoadGroupChatsOutputData outputData) {
        final ViewGroupChatsState viewGroupChatsState = viewGroupChatsViewModel.getState();
        viewGroupChatsState.setChannelInfo(outputData.getChannelInfo());
        // We just updated the group chat info, so we no longer need to update it.
        viewGroupChatsState.setNeedsGroupChatInfo(false);
        viewGroupChatsViewModel.setState(viewGroupChatsState);
        viewGroupChatsViewModel.firePropertyChanged();
    }
}
