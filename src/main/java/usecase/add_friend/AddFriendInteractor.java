package usecase.add_friend;

import java.util.ArrayList;
import java.util.List;

import entity.GroupChat;
import entity.GroupChatFactory;
import entity.User;

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

        if (checkNotEmpty(currentUsername, friendUsername, friendID)) {
            presenter.prepareFailView("Please fill out all fields");
        }

        // check that the friend exists
        else if (friendUser == null) {
            presenter.prepareFailView("User does not exist");
        }

        // check if friend's username and ID correspond to the same user
        else if (!userDataAccessObject.existsByName(friendUsername) || !(friendUser.getID().equals(friendID))) {
            presenter.prepareFailView("User " + friendUsername + " does not exist");
        }

        // check if blocked
        else if (currentUser.getBlockedUserIDs().contains(friendID)
                || friendUser.getBlockedUserIDs().contains(currentUser.getID())) {
            presenter.prepareFailView(friendUsername + " is blocked");
        }

        // check if already friends
        else if (currentUser.getFriendIDs().contains(friendID)) {
            presenter.prepareFailView("You are already friends with " + friendUsername);
        }

        // checking that user is not adding self using IDS, leave for near the end
        else if (friendID.equals(currentUser.getID())) {
            presenter.prepareFailView("You cannot add yourself as a friend (friend ID must be different from yours)");

        }

        // successful
        else {
            // updates current user in memory
            currentUser.addFriend(friendID);

            // create the chat between both friends
            List<String> membersOfChat = new ArrayList<>();
            membersOfChat.add(currentUser.getID());
            membersOfChat.add(friendID);
            GroupChat chat = userDataAccessObject.create(membersOfChat, currentUsername + ", "
                    + friendUsername, new GroupChatFactory());

            // add chat to current user for the in memory object
            currentUser.addPersonalChat(chat);

            // adds friendship in database, which adds chat to both users as well
            userDataAccessObject.addFriend(currentUsername, friendUsername, chat);

            // add chat to both users in database

            AddFriendOutputData outputData = new AddFriendOutputData(friendUsername,
                    true, friendUsername + " has been added!");
            presenter.prepareSuccessView(outputData);
        }
    }

    private boolean checkNotEmpty(String currentUsername, String friendUsername, String friendID) {
        // check that nothing is empty
        return currentUsername != null && friendUsername != null && friendID != null
                && !currentUsername.trim().isEmpty() && !friendUsername.trim().isEmpty() && !friendID.trim().isEmpty();
    }

    @Override
    public void switchToMainMenuView() {
        presenter.switchToMainMenuView();
    }
}
