package data_access;

import java.util.ArrayList;
import java.util.List;

import org.openapitools.client.model.*;
import org.sendbird.client.ApiClient;
import org.sendbird.client.ApiException;
import org.sendbird.client.Configuration;
import org.sendbird.client.api.UserApi;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import entity.*;
import use_case.add_Bio.AddBioUserDataAccessInterface;
import use_case.add_DOB.AddDOBUserDataAccessInterface;
import use_case.add_friend.AddFriendUserDataAccessInterface;
import use_case.block_friend.BlockFriendUserDataAccessInterface;
import use_case.change_password.ChangePasswordUserDataAccessInterface;
import use_case.create_chat.CreateChatUserDataAccessInterface;
import use_case.delete_account.DeleteAccountDataAccessInterface;
import use_case.login.LoginUserDataAccessInterface;
import use_case.logout.LogoutUserDataAccessInterface;
import use_case.remove_friend.RemoveFriendDataAccessInterface;
import use_case.signup.SignupUserDataAccessInterface;

/**
 * The DAO for user data.
 */
public class SendBirdUserDataAccessObject implements SignupUserDataAccessInterface,
        LoginUserDataAccessInterface,
        ChangePasswordUserDataAccessInterface,
        LogoutUserDataAccessInterface,
        BlockFriendUserDataAccessInterface,
        AddBioUserDataAccessInterface,
        AddDOBUserDataAccessInterface,
        AddFriendUserDataAccessInterface,
        RemoveFriendDataAccessInterface,
        CreateChatUserDataAccessInterface,
        DeleteAccountDataAccessInterface {

    private static final String API_TOKEN = "7836d8100957f700df15d54313b455766090ea9f";
    private static final String APPLICATION_ID = "https://api-17448E6A-5733-470D-BCE0-7A4460C94A11.sendbird.com";
    private final UserFactory userFactory;
    private final GroupChatDataAccessObject groupChatDataAccessObject;
    private User currentUser = null;
    private GroupChat currentSelfChat = null;
    private final PantryUserDataAccessObject pantryUserDataAccessObject = new PantryUserDataAccessObject();

    public SendBirdUserDataAccessObject() {
        this.userFactory = new CommonUserFactory();
        this.groupChatDataAccessObject = new GroupChatDataAccessObject();
    }

    /**
     * Updates the system to record this user's password.
     *
     * @param username the user whose password is to be updated
     * @param password the new password
     */
    @Override
    public void changePassword(String username, String password) {
        // The user object already contains the new password
        pantryUserDataAccessObject.changePassword(username, password);
    }

    /**
     * Returns the user with the given username.
     * @param username the username to look up
     * @return the user with the given username or null if the user does not exist
     */
    @Override
    public User get(String username) {
        // check the user exists
        if (existsByName(username)) {

            // Get User Data from Pantry
            JsonObject jsonData = pantryUserDataAccessObject.getUserDataFromUsername(username);
            String userId = jsonData.get("userId").getAsString();
            String password = jsonData.get("password").getAsString();
            String biography = jsonData.get("biography").getAsString();
            String dateOfBirth = jsonData.get("dateOfBirth").getAsString();
            List<String> friendIds = convertJsonArrayToList(jsonData.getAsJsonArray("friendIds"));
            List<String> blockedIds = convertJsonArrayToList(jsonData.getAsJsonArray("blockedIds"));
            List<String> groupChannelUrls = convertJsonArrayToList(jsonData.getAsJsonArray("groupChannelUrls"));
            List<String> personalChannelUrls = convertJsonArrayToList(jsonData.getAsJsonArray("personalChannelUrls"));
            String selfChatUrl = jsonData.get("selfChatURL").getAsString();

            // Get channel URLs
            ApiClient defaultClient = Configuration.getDefaultApiClient();
            defaultClient.setBasePath(APPLICATION_ID);
            UserApi apiInstance = new UserApi(defaultClient);

            try {
                ListMyGroupChannelsResponse result = apiInstance.listMyGroupChannels(userId)
                        .apiToken(API_TOKEN)
                        .showMember(true)
                        .execute();

                System.out.println(result);

                // Create GroupChat and PersonalChat objects
                List<GroupChat> groupChats = new ArrayList<>();
                List<GroupChat> personalChats = new ArrayList<>();
                GroupChat selfChat = null;
                List<SendbirdGroupChannel> channels = result.getChannels();
                if (channels != null) {
                    for (SendbirdGroupChannel sendBirdGroupChannel : channels) {
                        String channelUrl = sendBirdGroupChannel.getChannelUrl();
                        if (groupChannelUrls.contains(channelUrl)) {
                            groupChats.add(groupChatDataAccessObject.getGroupChat(sendBirdGroupChannel));
                        }
                        else if (personalChannelUrls.contains(channelUrl)) {
                            personalChats.add(groupChatDataAccessObject.getGroupChat(sendBirdGroupChannel));
                        }
                        else if (selfChatUrl.equals(channelUrl)) {
                            selfChat = groupChatDataAccessObject.getGroupChat(sendBirdGroupChannel);
                        }
                    }
                }

                // Initialize the user
                return userFactory.create(username, password, userId, biography,
                        dateOfBirth, friendIds, blockedIds, groupChats, personalChats, selfChat);
            }
            catch (ApiException e) {
                System.err.println("Exception when calling GroupChannelApi#gcListChannels");
                System.err.println("Status code: " + e.getCode());
                System.err.println("Reason: " + e.getResponseBody());
                System.err.println("Response headers: " + e.getResponseHeaders());
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Add friendship.
     *
     * @param currentUsername the current user.
     * @param friendUsername the username of the friend to be added.
     * @return true if friendship was successful
     */
    @Override
    public boolean addFriend(String currentUsername, String friendUsername) {
        return pantryUserDataAccessObject.addFriend(currentUsername, friendUsername);
    }

    /**
     * Block the target user for the current user.
     * @param currentUsername The user performing the block.
     * @param blockedUsername The user to be blocked.
     * @return the blocked user's ID.
     */
    @Override
    public String blockFriend(String currentUsername, String blockedUsername) {
        return pantryUserDataAccessObject.blockFriend(currentUsername, blockedUsername);
    }

    private List<String> convertJsonArrayToList(JsonArray jsonArray) {
        List<String> newList = new ArrayList<>();
        if (jsonArray != null) {
            for (JsonElement element : jsonArray) {
                newList.add(element.getAsString());
            }
        }
        return newList;
    }

    /**
     * Returns the username of the current user of the application.
     * @return the username of the current user; null indicates that no one is logged into the application.
     */
    @Override
    public String getCurrentUsername() {
        return currentUser.getName();
    }

    /**
     * Sets the username indicating who is the current user of the application.
     * @param username the new current username; null to indicate that no one is currently logged into the application.
     */
    @Override
    public void setCurrentUsername(String username) {
        currentUser.setName(username);
    }

    /**
     * Checks if the given username exists.
     *
     * @param username the username to look for
     * @return true if a user with the given username exists; false otherwise
     */
    @Override
    public boolean existsByName(String username) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath(APPLICATION_ID);

        UserApi apiInstance = new UserApi(defaultClient);
        String apiToken = API_TOKEN;

        try {
            ListUsersResponse result = apiInstance.listUsers()
                    .apiToken(apiToken)
                    .nickname(username)
                    .execute();
            System.out.println(result);

            if (result.getUsers() != null) {
                return !(result.getUsers().isEmpty());
            }

        }
        catch (ApiException e) {
            System.err.println("Exception when calling UserApi#listUsers");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean removeFriend(String currentUsername, String removedUsername) {
        return pantryUserDataAccessObject.removeFriend(currentUsername, removedUsername);
    }

    /**
     * Creates a SendBirdGroupChannel, adds the users using their ID,
     * creates a GroupChat and adds the channel as an attribute.
     *
     * @param memberIds        the member IDs of the users in the chat
     * @param chatName         the name of the chat
     * @param groupChatFactory the factory for the GroupChat
     * @return the newly created GroupChat
     */
    @Override
    public GroupChat create(List<String> memberIds, String chatName, GroupChatFactory groupChatFactory) {
        return groupChatDataAccessObject.create(memberIds, chatName, groupChatFactory);
    }

    @Override
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Save a group chat to a user.
     *
     * @param newGroupChat the group chat.
     * @param username the user's name
     */
    @Override
    public void saveGroupChat(GroupChat newGroupChat, String username) {
        pantryUserDataAccessObject.saveGroupChat(username, newGroupChat.getChannelURL());
    }

    @Override
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    /**
     * Checks if user already has friend added.
     *
     * @param currentUsername user performing friendship
     * @param friendUsername user receiving friendship
     * @return true if the two users have each other added already
     */
    @Override
    public boolean alreadyFriend(String currentUsername, String friendUsername) {
        return pantryUserDataAccessObject.alreadyFriend(currentUsername, friendUsername);
    }

    /**
     * Checks if the given username exists.
     *
     * @param userId the user ID to look for
     * @return true if a user with the given username exists; false otherwise
     */
    public boolean existsByID(String userId) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath(APPLICATION_ID);

        UserApi apiInstance = new UserApi(defaultClient);
        String apiToken = API_TOKEN;

        try {
            ListUsersResponse result = apiInstance.listUsers()
                    .apiToken(apiToken)
                    .userIds(userId)
                    .execute();
            System.out.println(result);

            if (result.getUsers() != null) {
                return !result.getUsers().isEmpty();
            }

        }
        catch (ApiException e) {
            System.err.println("Exception when calling UserApi#listUsers");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Saves the user. Updates username, password, biography, DOB, friend list, blocked list.
     *
     * @param user the user to save
     */
    @Override
    public void save(User user) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath(APPLICATION_ID);

        UserApi userApiInstance = new UserApi(defaultClient);

        String userId = user.getID();
        String apiToken = API_TOKEN;
        JsonObject userData = user.getUserData();

        // If the user is already in the database
        if (existsByID(user.getID())) {

            UpdateAUserRequest updateUserRequest = new UpdateAUserRequest()
                    .nickname(user.getName());

            try {
                // Update username on SendBird
                SendbirdUser result = userApiInstance.updateAUser(userId)
                        .apiToken(apiToken)
                        .updateAUserRequest(updateUserRequest)
                        .execute();
                System.out.println(result);

                // Update other user data on Pantry
                pantryUserDataAccessObject.save(userData);

            }
            catch (ApiException e) {
                System.err.println("Exception when calling UserApi#updateUserById");
                System.err.println("Status code: " + e.getCode());
                System.err.println("Reason: " + e.getResponseBody());
                System.err.println("Response headers: " + e.getResponseHeaders());
                e.printStackTrace();
            }
        }
        // If the user still needs to be added to the database
        else {

            CreateAUserRequest createUserRequest = new CreateAUserRequest()
                    .nickname(user.getName())
                    .userId(userId);

            try {
                // Update username on SendBird
                SendbirdUser result = userApiInstance.createAUser()
                        .apiToken(apiToken)
                        .createAUserRequest(createUserRequest)
                        .execute();

                // Update other user data on Pantry
                pantryUserDataAccessObject.save(userData);

            }
            catch (ApiException e) {
                System.err.println("Exception when calling UserApi#createUser");
                System.err.println("Status code: " + e.getCode());
                System.err.println("Reason: " + e.getResponseBody());
                System.err.println("Response headers: " + e.getResponseHeaders());
                e.printStackTrace();
            }
        }
    }

    /**
     * Creates a new self chat for the user.
     *
     * @param memberIds the list of members of the self chat (just the user)
     * @param chatName  the name of the self chat
     */
    @Override
    public GroupChat createSelfChat(List<String> memberIds, String chatName) {
        return groupChatDataAccessObject.create(memberIds, chatName, new GroupChatFactory());
    }

    /**
     * Updates the system to record this user's bio.
     *
     * @param username the name of the user whose bio is to be updated
     * @param bio the biography
     */
    @Override
    public boolean addBio(String username, String bio) {
        if (existsByName(username)) {
            return pantryUserDataAccessObject.updateBio(username, bio);
        }
        return false;
    }

    /**
     * Updates the system to record this user's DOB.
     *
     * @param username the name of the user whose DOB is to be updated\
     * @param dob the date of birth
     */
    @Override
    public boolean addDOB(String username, String dob) {
        if (existsByName(username)) {
            return pantryUserDataAccessObject.updateDateOfBirth(username, dob);
        }
        return false;
    }

    /**
     * Returns the self chat of the current user of the application.
     * @return the self chat of the current user; null indicates that no one is logged into the application.
     */
    public GroupChat getCurrentSelfChat() {
        return currentSelfChat;
    }

    /**
     * Sets the self chat of the current user of the application.
     * @param currentSelfChat the new current self chat; null to indicate that no one is
     *                        currently logged into the application.
     */
    public void setCurrentSelfChat(GroupChat currentSelfChat) {
        this.currentSelfChat = currentSelfChat;
    }

    /**
     * Deletes a user using their ID.
     * @param userId the ID of the user.
     * @param username the name of the user.
     * @return true if successful.               .
     */
    public boolean deleteUserById(String userId, String username) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath(APPLICATION_ID);

        UserApi apiInstance = new UserApi(defaultClient);
        String apiToken = API_TOKEN;
        try {
            Object result = apiInstance.deleteAUser(userId)
                    .apiToken(apiToken)
                    .execute();
            System.out.println(result);

            pantryUserDataAccessObject.deleteUser(username);

            return true;
        }
        catch (ApiException e) {
            System.err.println("Exception when calling UserApi#deleteAUser");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
            return false;
        }
    }
}
