package usecase.add_friend;

import java.util.ArrayList;
import java.util.List;

import entity.GroupChat;
import entity.GroupChatFactory;
import entity.User;

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
        if (friendUsername == null || friendID == null) {
            presenter.prepareFailView("Please fill out all fields");
            return;
        }
        else if (friendUsername.trim().isEmpty() || friendID.trim().isEmpty()) {
            presenter.prepareFailView("Please fill out all fields");
            return;
        }

        // check that the friend exists
        else if (friendUser == null) {
            presenter.prepareFailView("User does not exist");
            return;
        }

        // check if friend's username and ID correspond to the same user
        else if (!userDataAccessObject.existsByName(friendUsername) || !(friendUser.getID().equals(friendID))) {
            presenter.prepareFailView("User " + friendUsername + " does not exist");
            return;
        }

        // check if blocked
        else if (currentUser.getBlockedUserIDs().contains(friendID)
                || friendUser.getBlockedUserIDs().contains(currentUser.getID())) {
            presenter.prepareFailView(friendUsername + " is blocked");
            return;
        }

        // check if already friends
        else if (currentUser.getFriendIDs().contains(friendID)) {
            presenter.prepareFailView("You are already friends with " + friendUsername);
            return;
        }

        // checking that user is not adding self using IDS, leave for near the end
        else if (friendID.equals(currentUser.getID())) {
            presenter.prepareFailView("You cannot add yourself as a friend (friend ID must be different from yours)");

        }

        // successful
        else {
            // updates current user in memory
            currentUser.addFriend(friendID);
            friendUser.addFriend(currentUser.getID());

            // create the chat between both friends
            List<String> membersOfChat = new ArrayList<>();
            membersOfChat.add(currentUser.getID());
            membersOfChat.add(friendID);
            GroupChat chat = userDataAccessObject.create(membersOfChat, currentUsername + ", " +
                    friendUsername, new GroupChatFactory());

            // add chat to current user for the in memory object
            currentUser.addPersonalChat(chat);
            friendUser.addPersonalChat(chat);

            // adds friendship in database, which adds chat to both users as well
            userDataAccessObject.addFriend(currentUsername, friendUsername, chat);

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
