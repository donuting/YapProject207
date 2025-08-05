package use_case.add_friend;

/**
 * Output Data for the Add Friend Use Case.
 */
public class AddFriendOutputData {
        private final String friendUsername;
        private final boolean isSuccess;
        private final String successMessage;

        public AddFriendOutputData(String friendUsername,
                                   boolean isSuccess,
                                   String successMessage) {
            this.friendUsername = friendUsername;
            this.successMessage = successMessage;
            this.isSuccess = isSuccess;
        }

        public String getFriendUsername() {
            return friendUsername;
        }

        public String getSuccessMessage() {
            return successMessage;
        }

        public boolean isSuccess() {
            return isSuccess;
        }

}
