package use_case.block_friend;

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
        String currentUsername = inputData.getCurrentUsername();
        String blockedUsername = inputData.getBlockedUsername();
        boolean currentUserExists = userDataAccessObject.existsByName(currentUsername);
        boolean blockedUserExists = userDataAccessObject.existsByName(blockedUsername);
        boolean success = false;
        if (currentUserExists && blockedUserExists) {
            success = userDataAccessObject.blockFriend(currentUsername, blockedUsername);
        }

        BlockFriendOutputData outputData = new BlockFriendOutputData(currentUsername, blockedUsername, success);
        if (success) {
            presenter.prepareSuccessView(outputData);
        }
        else {
            presenter.prepareFailView("Failed to block friend.", outputData);
        }
    }
}