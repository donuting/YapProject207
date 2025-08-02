package interface_adapter.remove_friend;

import use_case.remove_friend.RemoveFriendInputBoundary;
import use_case.remove_friend.RemoveFriendInputData;

/**
 * The controller for the Remove Friend Use Case.
 */
public class RemoveFriendController {
    private final RemoveFriendInputBoundary removeFriendUseCaseInteractor;

    public RemoveFriendController(RemoveFriendInputBoundary removeFriendUseCaseInteractor) {
        this.removeFriendUseCaseInteractor = removeFriendUseCaseInteractor;
    }

    public void execute (String removedUsername) {
        final RemoveFriendInputData removeFriendInputData = new RemoveFriendInputData(removedUsername);
        removeFriendUseCaseInteractor.execute(removeFriendInputData);
    }
}
