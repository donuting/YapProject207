package use_case.block_friend;

import entity.User;
import entity.UserFactory;

/**
 * The interactor for the Block Friend use case.
 */
public class BlockFriendInteractor implements BlockFriendInputBoundary {
    private final BlockFriendUserDataAccessInterface userDataAccessObject;
    private final BlockFriendOutputBoundary presenter;
    private final UserFactory userFactory;

    public BlockFriendInteractor(BlockFriendUserDataAccessInterface userDataAccessObject,
                                 BlockFriendOutputBoundary presenter,
                                 UserFactory userFactory) {
        this.userDataAccessObject = userDataAccessObject;
        this.presenter = presenter;
        this.userFactory = userFactory;
    }

    @Override
    public void execute(BlockFriendInputData inputData) {
        User currentUser = userDataAccessObject.getUser(inputData.getCurrentUsername());
        User blockedUser = userDataAccessObject.getUser(inputData.getBlockedUsername());
        boolean success = false;
        if (currentUser != null && blockedUser != null) {
            success = userDataAccessObject.blockFriend(currentUser, blockedUser);
        }

        BlockFriendOutputData outputData = new BlockFriendOutputData(inputData.getCurrentUsername(),
                inputData.getBlockedUsername(), success);
        if (success) {
            presenter.prepareSuccessView(outputData);
        }
        else {
            presenter.prepareFailView("Failed to block friend.", outputData);
        }
    }
}