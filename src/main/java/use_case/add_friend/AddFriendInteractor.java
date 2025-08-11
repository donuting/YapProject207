package use_case.add_friend;

import data_access.InMemoryUserDataAccessObject;
import entity.GroupChat;
import entity.GroupChatFactory;
import entity.User;
import entity.UserFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * The AddFriend Interactor.
 */
public class AddFriendInteractor implements AddFriendInputBoundary {
    private final AddFriendUserDataAccessInterface userDataAccessObject;
    private final AddFriendOutputBoundary presenter;


    public AddFriendInteractor(AddFriendUserDataAccessInterface userDataAccessInterface,
                               AddFriendOutputBoundary addFriendOutputBoundary) {

        this.userDataAccessObject = userDataAccessInterface;
        this.presenter = addFriendOutputBoundary;

    }

    @Override
    public void execute(AddFriendInputData addFriendInputData) {
        User currentUser = userDataAccessObject.getCurrentUser();
        final String currentUsername = currentUser.getName();
        final String friendUsername = addFriendInputData.getFriendUsername();
        final String friendID = addFriendInputData.getFriendID();
        User friendUser = userDataAccessObject.get(friendUsername);


        // check that nothing is empty
        if (currentUsername == null || friendUsername == null || friendID == null) {
            presenter.prepareFailView("Please fill out all fields");
            return;
        }

        if (currentUsername.trim().isEmpty() || friendUsername.trim().isEmpty() || friendID.trim().isEmpty()) {
            presenter.prepareFailView("Please fill out all fields");
            return;
        }

        // check that the friend exists
        if (friendUser == null) {
            presenter.prepareFailView("User does not exist");
            return;
        }


        // check if friend's username and ID correspond to the same user
        if (!userDataAccessObject.existsByName(friendUsername) || !(friendUser.getID().equals(friendID))) {
            presenter.prepareFailView("User " + friendUsername + " does not exist");
            return;
        }

        // check if blocked
        if (currentUser.getBlockedUserIDs().contains(friendID)
                || friendUser.getBlockedUserIDs().contains(currentUser.getID())) {
            presenter.prepareFailView(friendUsername + " is blocked");
            return;
        }

        // check if already friends
        if (currentUser.getFriendIDs().contains(friendID)) {
            presenter.prepareFailView("You are already friends with " + friendUsername);
            return;
        }

        // checking that user is not adding self using IDS, leave for near the end
        if (friendID.equals(currentUser.getID())) {
            presenter.prepareFailView("You cannot add yourself as a friend (friend ID must be different from yours)");
            return;
        }

        // successful
        else {
            // adds friend for in memory object
            currentUser.addFriend(friendID);

            // adds friendship in database
            userDataAccessObject.addFriend(currentUsername, friendUsername);

            // create the chat between both friends
            List<String> membersOfChat = new ArrayList<>();
            membersOfChat.add(currentUser.getID());
            membersOfChat.add(friendID);
            GroupChat chat = userDataAccessObject.create(membersOfChat, currentUsername + ", " +
                    friendUsername, new GroupChatFactory());

            // add chat to current user for the in memory object
            currentUser.addPersonalChat(chat);

            // add chat to both users in database
            userDataAccessObject.savePersonalChat(chat, currentUsername);
            userDataAccessObject.savePersonalChat(chat, friendUsername);

            AddFriendOutputData outputData = new AddFriendOutputData(friendUsername,
                    true, friendUsername + " has been added!");
            presenter.prepareSuccessView(outputData);
        }
    }

    @Override
    public void switchToMainMenuView() {
        presenter.switchToMainMenuView();
    }
}
