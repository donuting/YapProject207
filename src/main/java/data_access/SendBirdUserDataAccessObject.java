package data_access;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import entity.*;
import org.openapitools.client.model.*;
import org.sendbird.client.ApiClient;
import org.sendbird.client.ApiException;
import org.sendbird.client.Configuration;
import org.sendbird.client.api.UserApi;
import use_case.block_friend.BlockFriendUserDataAccessInterface;
import use_case.change_password.ChangePasswordUserDataAccessInterface;
import use_case.login.LoginUserDataAccessInterface;
import use_case.logout.LogoutUserDataAccessInterface;
import use_case.signup.SignupUserDataAccessInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * The DAO for user data.
 */
public class SendBirdUserDataAccessObject implements SignupUserDataAccessInterface,
        LoginUserDataAccessInterface,
        ChangePasswordUserDataAccessInterface,
        LogoutUserDataAccessInterface,
        BlockFriendUserDataAccessInterface {

    private static final String API_TOKEN = "7836d8100957f700df15d54313b455766090ea9f";
    private static final String APPLICATION_ID = "https://api-17448E6A-5733-470D-BCE0-7A4460C94A11.sendbird.com";
    private final UserFactory userFactory;
    private final GroupChatDataAccessObject groupChatDataAccessObject;
    private String currentUsername;
    private PantryUserDataAccessObject pantryUserDataAccessObject = new PantryUserDataAccessObject();

    public SendBirdUserDataAccessObject(UserFactory userFactory, GroupChatDataAccessObject groupChatDataAccessObject) {
        this.userFactory = userFactory;
        this.groupChatDataAccessObject = groupChatDataAccessObject;
        this.currentUsername = null; // default
    }

    /**
     * Updates the system to record this user's password.
     *
     * @param user the user whose password is to be updated
     */
    @Override
    public void changePassword(User user) {
        pantryUserDataAccessObject.changePassword(user.getName(), user.getPassword()); // The user object already contains the new password
    }

    /**
     * Returns the user with the given username.
     *
     * @param username the username to look up
     * @return the user with the given username or null if the user does not exist
     */
    @Override
    public User get(String username) {
        // check the user exists
        if (!existsByName(username)) {
            return null;
        }
        else {

            // Get User Data from Pantry
            JsonObject jsonData = pantryUserDataAccessObject.getUserDataFromUsername(username);
            String userId = jsonData.get("userId").getAsString();
            String password = jsonData.get("password").getAsString();
            String biography = jsonData.get("biography").getAsString();
            String dateOfBirth = jsonData.get("dateOfBirth").getAsString();
            List<String> friendIDs = convertJsonArrayToList(jsonData.getAsJsonArray("friendIDs"));
            List<String> blockedIDs = convertJsonArrayToList(jsonData.getAsJsonArray("blockedIDs"));
            List<String> groupChannelURLs = convertJsonArrayToList(jsonData.getAsJsonArray("groupChannelURLs"));
            List<String> personalChannelURls = convertJsonArrayToList(jsonData.getAsJsonArray("personalChannelURls"));

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
                List<SendbirdGroupChannel> channels = result.getChannels();
                if (channels != null) {
                    for (SendbirdGroupChannel sendBirdGroupChannel : channels) {
                        String channelURL = sendBirdGroupChannel.getChannelUrl();
                        if (groupChannelURLs.contains(channelURL)) {
                            groupChats.add(groupChatDataAccessObject.getGroupChat(sendBirdGroupChannel)); // Todo: implement this method
                        } else if (personalChannelURls.contains(channelURL)) {
                            personalChats.add(groupChatDataAccessObject.getGroupChat(sendBirdGroupChannel)); // Todo: implement this method
                        }
                    }
                }

                // Initialize the user
                return userFactory.create(username, password, userId, biography, dateOfBirth, friendIDs, blockedIDs, groupChats, personalChats);

            } catch (ApiException e) {
                System.err.println("Exception when calling GroupChannelApi#gcListChannels");
                System.err.println("Status code: " + e.getCode());
                System.err.println("Reason: " + e.getResponseBody());
                System.err.println("Response headers: " + e.getResponseHeaders());
                e.printStackTrace();
            }

            return null;
        }
    }

    /**
     * Block the target user for the current user.
     *
     * @param currentUsername The user performing the block.
     * @param blockedUsername The user to be blocked.
     * @return true if blocking successful, false otherwise.
     */
    @Override
    public boolean blockFriend(String currentUsername, String blockedUsername) {
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
     * Returns the username of the curren user of the application.
     *
     * @return the username of the current user; null indicates that no one is logged into the application.
     */
    @Override
    public String getCurrentUsername() {
        return currentUsername;
    }

    /**
     * Sets the username indicating who is the current user of the application.
     *
     * @param username the new current username; null to indicate that no one is currently logged into the application.
     */
    @Override
    public void setCurrentUsername(String username) {
        this.currentUsername = username;
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

            if (result.getUsers() == null) {
                return false;
            }
            return !(result.getUsers().isEmpty());

        } catch (ApiException e) {
            System.err.println("Exception when calling UserApi#listUsers");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
        return true;
    }

    public boolean existsByID(String userID) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath(APPLICATION_ID);

        UserApi apiInstance = new UserApi(defaultClient);
        String apiToken = API_TOKEN;

        try {
            ListUsersResponse result = apiInstance.listUsers()
                    .apiToken(apiToken)
                    .userIds(userID)
                    .execute();
            System.out.println(result);

            if (result.getUsers() == null) {
                return false;
            }
            return !result.getUsers().isEmpty();

        } catch (ApiException e) {
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

        String userId = user.getID(); // String |
        String apiToken = API_TOKEN; // String |
        JsonObject userData = user.getUserData();

        // If the user is already in the database
        if (existsByID(user.getID())) {

            UpdateAUserRequest updateAUserRequest = new UpdateAUserRequest()
                    .nickname(user.getName());

            try {
                // Update username on SendBird
                SendbirdUser result = userApiInstance.updateAUser(userId)
                        .apiToken(apiToken)
                        .updateAUserRequest(updateAUserRequest)
                        .execute();
                System.out.println(result);

                // Update other user data on Pantry
                pantryUserDataAccessObject.save(userData);

            } catch (ApiException e) {
                System.err.println("Exception when calling UserApi#updateUserById");
                System.err.println("Status code: " + e.getCode());
                System.err.println("Reason: " + e.getResponseBody());
                System.err.println("Response headers: " + e.getResponseHeaders());
                e.printStackTrace();
            }
        }
        // If the user still needs to be added to the database
        else {

            CreateAUserRequest createAUserRequest = new CreateAUserRequest()
                    .nickname(user.getName())
                    .userId(userId);

            try {
                // Update username on SendBird
                SendbirdUser result = userApiInstance.createAUser()
                        .apiToken(apiToken)
                        .createAUserRequest(createAUserRequest)
                        .execute();

                // Update other user data on Pantry
                pantryUserDataAccessObject.save(userData);

            } catch (ApiException e) {
                System.err.println("Exception when calling UserApi#createUser");
                System.err.println("Status code: " + e.getCode());
                System.err.println("Reason: " + e.getResponseBody());
                System.err.println("Response headers: " + e.getResponseHeaders());
                e.printStackTrace();
            }
        }
    }
}
