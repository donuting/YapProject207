package interfaceadapter.view_group_chats;

import java.util.Map;

import entity.GroupChat;
import usecase.join_chat.JoinChatOutputBoundary;
import usecase.join_chat.JoinChatOutputData;
import usecase.leave_chat.LeaveChatOutputBoundary;
import usecase.leave_chat.LeaveChatOutputData;
import usecase.load_group_chats.LoadGroupChatsOutputBoundary;
import usecase.load_group_chats.LoadGroupChatsOutputData;

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
        final GroupChat groupChat = outputData.getGroupChat();
        final ViewGroupChatsState viewGroupChatsState = viewGroupChatsViewModel.getState();
        final Map<String, String> channelInfo = viewGroupChatsState.getChannelInfo();
        channelInfo.put(groupChat.getChannelUrl(), groupChat.getChatName());
        viewGroupChatsState.setChannelInfo(channelInfo);
        viewGroupChatsState.setNeedsGroupChatInfo(true);

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
        viewGroupChatsState.setNeedsGroupChatInfo(false);
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
    public void leaveChatPrepareSuccessView(LeaveChatOutputData outputData) {
        // Remove the chat from the state object
        final ViewGroupChatsState viewGroupChatsState = viewGroupChatsViewModel.getState();
        final Map<String, String> channelInfo = viewGroupChatsState.getChannelInfo();
        channelInfo.remove(outputData.getChannelUrl());
        viewGroupChatsState.setChannelInfo(channelInfo);
        viewGroupChatsState.setNeedsGroupChatInfo(true);

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
