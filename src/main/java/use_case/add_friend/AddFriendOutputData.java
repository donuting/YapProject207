package use_case.add_friend;

/**
 * Output Data for the Add Friend Use Case.
 */
public class AddFriendOutputData {
        //private final String currentUserID;
        private final String friendUsername;
        //private final String friendID;
        private final boolean useCaseFailed;
        private final String successMessage;

        public AddFriendOutputData(String friendUsername,
                                   boolean useCaseFailed,
                                   String successMessage) {
            //this.currentUserID = currentUserID;
            this.friendUsername = friendUsername;
            //this.friendID = friendID;
            this.successMessage = successMessage;
            this.useCaseFailed = useCaseFailed;
        }

//        public String getCurrentUserID() {
//            return currentUserID;
//        }

        public String getFriendUsername() {
            return friendUsername;
        }

//        public String getFriendID() {
//            return friendID;
//        }

        public String getSuccessMessage() {
            return successMessage;
        }

        public boolean isUseCaseFailed() {
            return useCaseFailed;
        }

}
