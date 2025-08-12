package interface_adapter.view_friends;

import interface_adapter.ViewManagerModel;
import interface_adapter.chat.ChatState;
import interface_adapter.chat.ChatViewModel;
import use_case.block_friend.BlockFriendInputBoundary;
import use_case.block_friend.BlockFriendInputData;
import use_case.load_friends.LoadFriendsInputBoundary;
import use_case.remove_friend.RemoveFriendInputBoundary;
import use_case.remove_friend.RemoveFriendInputData;

public class ViewFriendsController {
    private final RemoveFriendInputBoundary removeFriendUseCaseInteractor;
    private final BlockFriendInputBoundary blockFriendUseCaseInteractor;
    private final LoadFriendsInputBoundary loadFriendsUseCaseInteractor;
    private final ChatViewModel chatViewModel;
    private final ViewManagerModel viewManagerModel;

    public ViewFriendsController(RemoveFriendInputBoundary removeFriendUseCaseInteractor, 
                                 BlockFriendInputBoundary blockFriendUseCaseInteractor, 
                                 LoadFriendsInputBoundary loadFriendsUseCaseInteractor, 
                                 ChatViewModel chatViewModel, 
                                 ViewManagerModel viewManagerModel) {
        this.removeFriendUseCaseInteractor = removeFriendUseCaseInteractor;
        this.blockFriendUseCaseInteractor = blockFriendUseCaseInteractor;
        this.loadFriendsUseCaseInteractor = loadFriendsUseCaseInteractor;
        this.chatViewModel = chatViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    /**
     * Switches to the main menu. 
     */
    public void switchToMainMenu() {
        viewManagerModel.setState("main menu");
        viewManagerModel.firePropertyChanged();
    }
    
    /**
     * Switches to the add friend view. 
     */ 
    public void switchToAddFriend() {
        viewManagerModel.setState("add friend");
        viewManagerModel.firePropertyChanged();
    }

    /**
     * Executes the Block Friend Use Case.
     * @param blockedId the ID of the user to be blocked.
     */
    public void block(String blockedId) {
        final BlockFriendInputData blockFriendInputData = new BlockFriendInputData(blockedId);
        blockFriendUseCaseInteractor.execute(blockFriendInputData);
    }

    /**
     * Executes the Remove Friend Use Case.
     * @param removedId the ID of the user to be unfriended.
     */
    public void removeFriend(String removedId) {
        final RemoveFriendInputData removeFriendInputData = new RemoveFriendInputData(removedId);
        removeFriendUseCaseInteractor.execute(removeFriendInputData);
    }

    /**
     * Executes the View Chat Use Case.
     * @param channelUrl the URL of the channel to be viewed.
     * @param chatName the name of the chat to be viewed.                   
     */
    public void switchToViewChat(String channelUrl, String chatName) {
        // Update the active chat's channel URL and name
        final ChatState chatState = chatViewModel.getState();
        chatState.setCurrentChannelUrl(channelUrl);
        chatState.setChatName(chatName);
        chatState.setGroupChat(false);

        // Updates the active chat's messages
        chatState.setNeedsUpdate(true);
        chatViewModel.setState(chatState);
        chatViewModel.firePropertyChanged();

        // Navigate to the chat view
        viewManagerModel.setState("chat");
        viewManagerModel.firePropertyChanged();
    }

    /**
     * Executes the Load Friends Use Case.
     */
    public void loadFriends() {
        loadFriendsUseCaseInteractor.execute();
    }
}
