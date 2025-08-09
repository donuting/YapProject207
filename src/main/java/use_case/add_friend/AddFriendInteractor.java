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
        final String currentUsername = addFriendInputData.getCurrentUsername();
        final String friendUsername = addFriendInputData.getFriendUsername();
        final String friendID = addFriendInputData.getFriendID();
        User currentUser = userDataAccessObject.get(currentUsername);
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

        // check if current user spelled own username correctly, not sure if this worked (maybe DAO issue)
        if (currentUser == null || !currentUser.getName().equals(currentUsername)) {
            presenter.prepareFailView("Your account name is incorrect");
            return;
        }

        if (!userDataAccessObject.existsByName(friendUsername) || !(friendUser.getID().equals(friendID))) {
            presenter.prepareFailView("User " + friendUsername + " does not exist");
            return;
        }

        if (currentUser.getBlockedUserIDs().contains(friendID)) {
            presenter.prepareFailView(friendUsername + " is blocked");
            return;
        }

        // check if already friends
        if (userDataAccessObject.alreadyFriend(currentUsername, friendUsername)) {
            presenter.prepareFailView("You are already friends with " + friendUsername);
            return;
        }

        // checking that user is not adding self using IDS, leave for near the end
        if (friendID.equals(currentUser.getID())) {
            presenter.prepareFailView("You cannot add yourself as a friend (friend ID must be different from yours)");
        }

        // successful
        else {
            // adds friend for both users
            currentUser.addFriend(friendID);
            friendUser.addFriend(currentUser.getID());

            // adds friendship in database
            userDataAccessObject.addFriend(currentUsername, friendUsername);

            // create the chat between both friends
            List<String> membersOfChat = new ArrayList<>();
            membersOfChat.add(currentUsername);
            membersOfChat.add(friendUsername);
            GroupChat chat = userDataAccessObject.create(membersOfChat, currentUsername + ", " +
                    friendUsername, new GroupChatFactory());

            // add chat to both users
            currentUser.addGroupChat(chat);
            friendUser.addGroupChat(chat);

            // add chat to both users in database
            userDataAccessObject.saveGroupChat(chat, currentUsername);
            userDataAccessObject.saveGroupChat(chat, friendUsername);

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
