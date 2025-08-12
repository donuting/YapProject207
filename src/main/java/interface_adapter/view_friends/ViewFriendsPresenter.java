package interface_adapter.view_friends;

import java.util.HashMap;
import java.util.Map;

import use_case.block_friend.BlockFriendOutputBoundary;
import use_case.block_friend.BlockFriendOutputData;
import use_case.load_friends.LoadFriendsOutputBoundary;
import use_case.load_friends.LoadFriendsOutputData;
import use_case.remove_friend.RemoveFriendOutputBoundary;
import use_case.remove_friend.RemoveFriendOutputData;

public class ViewFriendsPresenter implements RemoveFriendOutputBoundary,
        BlockFriendOutputBoundary, LoadFriendsOutputBoundary {
    private final ViewFriendsViewModel viewFriendsViewModel;

    public ViewFriendsPresenter(ViewFriendsViewModel viewFriendsViewModel) {
        this.viewFriendsViewModel = viewFriendsViewModel;
    }

    /**
     * Prepare the success view for the Block Friend use case.
     *
     * @param outputData Output data.
     */
    @Override
    public void blockFriendPrepareSuccessView(BlockFriendOutputData outputData) {
        // Remove the friend from the state object
        final ViewFriendsState viewFriendsState = viewFriendsViewModel.getState();
        final Map<String, String> channelToUserIdData = viewFriendsState.getChannelToUserIdData();
        final Map<String, String> newChannelToUserIdData = new HashMap<>();
        final String blockedId = outputData.getBlockedId();
        for (Map.Entry<String, String> entry : channelToUserIdData.entrySet()) {
            if (!entry.getValue().equals(blockedId)) {
                newChannelToUserIdData.put(entry.getKey(), entry.getValue());
            }
        }
        viewFriendsState.setChannelToUserIdData(newChannelToUserIdData);
        viewFriendsState.setNeedsFriendInfo(true);
        viewFriendsViewModel.setState(viewFriendsState);
        viewFriendsViewModel.firePropertyChanged();
    }

    /**
     * Prepare the fail view for the Block Friend use case.
     *
     * @param errorMessage Explanation of failure.
     * @param outputData   Output data.
     */
    @Override
    public void blockFriendPrepareFailView(String errorMessage, BlockFriendOutputData outputData) {
        final ViewFriendsState viewFriendsState = viewFriendsViewModel.getState();
        viewFriendsState.setErrorMessage(errorMessage);
        viewFriendsState.setNeedsFriendInfo(false);
        viewFriendsViewModel.setState(viewFriendsState);
        viewFriendsViewModel.firePropertyChanged();
    }

    /**
     * Prepare the fail view for the Load Friends use case.
     *
     * @param errorMessage Explanation of failure.
     */
    @Override
    public void loadFriendsPrepareFailView(String errorMessage) {
        final ViewFriendsState viewFriendsState = viewFriendsViewModel.getState();
        viewFriendsState.setErrorMessage(errorMessage);
        viewFriendsState.setNeedsFriendInfo(false);
        viewFriendsViewModel.setState(viewFriendsState);
        viewFriendsViewModel.firePropertyChanged();
    }

    /**
     * Prepare the success view for the Load Group Chats use case.
     *
     * @param outputData Output data.
     */
    @Override
    public void loadFriendsPrepareSuccessView(LoadFriendsOutputData outputData) {
        final ViewFriendsState viewFriendsState = viewFriendsViewModel.getState();
        viewFriendsState.setChannelToUserIdData(outputData.getChannelToUserIdData());
        // We just updated the friend info, so we no longer need to update it.
        viewFriendsState.setNeedsFriendInfo(false);
        viewFriendsViewModel.setState(viewFriendsState);
        viewFriendsViewModel.firePropertyChanged();
    }

    /**
     * Prepare the success view for the Block Friend use case.
     *
     * @param outputData Output data.
     */
    @Override
    public void removeFriendPrepareSuccessView(RemoveFriendOutputData outputData) {
        // Remove the friend from the state object
        final ViewFriendsState viewFriendsState = viewFriendsViewModel.getState();
        viewFriendsState.setSuccessMessage(outputData.getRemovedUsername() + "removed successfully");

        final Map<String, String> channelToUserIdData = viewFriendsState.getChannelToUserIdData();
        final Map<String, String> newChannelToUserIdData = new HashMap<>();
        final String removedUserId = outputData.getRemovedId();
        for (Map.Entry<String, String> entry : channelToUserIdData.entrySet()) {
            if (!entry.getValue().equals(removedUserId)) {
                newChannelToUserIdData.put(entry.getKey(), entry.getValue());
            }
        }
        viewFriendsState.setChannelToUserIdData(newChannelToUserIdData);
        viewFriendsState.setNeedsFriendInfo(true);
        viewFriendsViewModel.setState(viewFriendsState);
        viewFriendsViewModel.firePropertyChanged();
    }

    /**
     * Prepare the fail view for the Block Friend use case.
     * @param errorMessage Explanation of failure.
     */
    @Override
    public void removeFriendPrepareFailView(String errorMessage) {
        final ViewFriendsState viewFriendsState = viewFriendsViewModel.getState();
        viewFriendsState.setErrorMessage(errorMessage);
        viewFriendsState.setNeedsFriendInfo(false);
        viewFriendsViewModel.setState(viewFriendsState);
        viewFriendsViewModel.firePropertyChanged();
        // assume it cant fail, not used anyway
    }
}
