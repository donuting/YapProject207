package use_case.add_friend;

/**
 * Output Data for the Signup Use Case.
 */
public class AddFriendOutputData {
        private final String currentUserID;
        private final String friendUsername;
        private final String friendID;
        // private final boolean success;

        public AddFriendOutputData(String currentUserID,
                                   String friendUsername,
                                   String friendID) {
                                   // boolean success) {
            this.currentUserID = currentUserID;
            this.friendUsername = friendUsername;
            this.friendID = friendID;
            // this.success = success;
        }

        public String getCurrentUserID() {
            return currentUserID;
        }

        public String getFriendUsername() {
            return friendUsername;
        }

        public String getFriendID() {
            return friendID;
        }

//        public boolean isSuccess() {
//            return success;
//        }
}
