package interface_adapter.remove_friend;

import use_case.remove_friend.RemoveFriendOutputBoundary;
import use_case.remove_friend.RemoveFriendOutputData;

/**
 * The Presenter for the Remove Friend Use Case.
 */
public class RemoveFriendPresenter implements RemoveFriendOutputBoundary {
    private final RemoveFriendViewModel removeFriendViewModel;

    public RemoveFriendPresenter(RemoveFriendViewModel removeFriendViewModel) {
        this.removeFriendViewModel = removeFriendViewModel;
    }

    /**
     * Prepare the success view for the Block Friend use case.
     *
     * @param outputData Output data.
     */
    @Override
    public void prepareSuccessView(RemoveFriendOutputData outputData) {
        // Todo: Finish this when the view friends view model has been created
    }

    /**
     * Prepare the fail view for the Block Friend use case.
     *
     * @param errorMessage Explanation of failure.
     * @param outputData   Output data.
     */
    @Override
    public void prepareFailView(String errorMessage, RemoveFriendOutputData outputData) {
        // Todo: Finish this when the view friends view model has been created

    }
}
