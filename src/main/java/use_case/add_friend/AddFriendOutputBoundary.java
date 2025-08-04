package use_case.add_friend;

/**
 * The output boundary for the Add Friend Use Case.
 */
public interface AddFriendOutputBoundary {

    /**
     * Prepares the success view for the Add Friend Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(AddFriendOutputData outputData);

    /**
     * Prepares the failure view for the AddFriend Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage, AddFriendOutputData outputData);

    //void prepareFailView(String errorMessage);
}
