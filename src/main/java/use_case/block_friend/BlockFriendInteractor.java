package use_case.block_friend;

import entity.User;

/**
 * The interactor for the Block Friend use case.
 */
public class BlockFriendInteractor implements BlockFriendInputBoundary {
    private final BlockFriendUserDataAccessInterface userDataAccessObject;
    private final BlockFriendOutputBoundary presenter;

    public BlockFriendInteractor(BlockFriendUserDataAccessInterface userDataAccessObject,
                                 BlockFriendOutputBoundary presenter) {
        this.userDataAccessObject = userDataAccessObject;
        this.presenter = presenter;
    }

    @Override
    public void execute(BlockFriendInputData inputData) {
        User currentUser = userDataAccessObject.getCurrentUser();
        String currentUsername = currentUser.getName();

        String blockedUsername = inputData.getBlockedUsername();
        boolean currentUserExists = userDataAccessObject.existsByName(currentUsername);
        boolean blockedUserExists = userDataAccessObject.existsByName(blockedUsername);
        boolean success = false;
        String blockedUserId = null;
        if (currentUserExists && blockedUserExists) {
            blockedUserId = userDataAccessObject.blockFriend(currentUsername, blockedUsername);
            success = blockedUserId != null;
        }

        BlockFriendOutputData outputData = new BlockFriendOutputData(currentUsername, blockedUsername, success);
        if (success) {
            currentUser.blockUser(blockedUserId);
            presenter.prepareSuccessView(outputData);
        }
        else {
            presenter.prepareFailView("Failed to block friend.", outputData);
        }
    }
}