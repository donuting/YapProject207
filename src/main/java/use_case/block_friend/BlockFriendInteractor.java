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
        String blockedUserId = inputData.getBlockedId();
        String blockedUsername = userDataAccessObject.getUsernameFromId(blockedUserId);
        boolean currentUserExists = userDataAccessObject.existsByName(currentUsername);
        boolean blockedUserExists = userDataAccessObject.existsByName(blockedUsername);
        boolean success = false;

        if (currentUserExists && blockedUserExists) {
            success = userDataAccessObject.blockFriend(currentUser, blockedUsername, blockedUserId);
        }

        BlockFriendOutputData outputData = new BlockFriendOutputData(currentUsername, blockedUsername, inputData.getBlockedId(), success);
        if (success) {
            currentUser.blockUser(blockedUserId);
            presenter.blockFriendPrepareSuccessView(outputData);
        }
        else {
            presenter.blockFriendPrepareFailView("Failed to block friend.", outputData);
        }
    }
}