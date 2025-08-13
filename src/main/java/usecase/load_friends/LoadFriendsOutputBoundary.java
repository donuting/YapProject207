package usecase.load_friends;

public interface LoadFriendsOutputBoundary {
    /**
     * Prepare the fail view for the Load Friends use case.
     * @param errorMessage Explanation of failure.
     */
    void loadFriendsPrepareFailView(String errorMessage);

    /**
     * Prepare the success view for the Load Group Chats use case.
     * @param outputData Output data.
     */
    void loadFriendsPrepareSuccessView(LoadFriendsOutputData outputData);
}
