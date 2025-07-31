package data_access;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import entity.*;
import org.openapitools.client.model.*;
import org.sendbird.client.ApiClient;
import org.sendbird.client.ApiException;
import org.sendbird.client.Configuration;
import org.sendbird.client.api.GroupChannelApi;
import org.sendbird.client.api.UserApi;
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
        LogoutUserDataAccessInterface {

    static String API_TOKEN = ""; // Todo: get a api token for SendBird
    static String APPLICATION_ID = "https://api-APP_ID.sendbird.com"; // Todo: figure out the application ID
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
        pantryUserDataAccessObject.changePassword(user.getID(), user.getPassword()); // The user object already contains the new password
    }

    /**
     * Returns the user with the given username.
     *
     * @param username the username to look up
     * @return the user with the given username
     */
    @Override
    public User get(String username) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath(APPLICATION_ID);

        UserApi userApiInstance = new UserApi(defaultClient);
        String apiToken = API_TOKEN;

        try {
            ListUsersResponse UserResponse = userApiInstance.listUsers()
                    .apiToken(apiToken)
                    .nickname(username)
                    .execute();

            if (UserResponse.getUsers().size() != 1) {
                throw new ApiException("The user could not be found");
            }

            // Get User Data from SendBird
            SendBirdUser sendBirdUser = UserResponse.getUsers().get(0);
            String name = sendBirdUser.getNickname();
            String userId = sendBirdUser.getUserId();

            // Get User Data from Pantry
            JsonObject jsonData = pantryUserDataAccessObject.getUserData(userId);
            String password = jsonData.get("password").getAsString();
            String biography = jsonData.get("biography").getAsString();
            String dateOfBirth = jsonData.get("dateOfBirth").getAsString();
            List<String> friendIDs = convertJsonArrayToList(jsonData.getAsJsonArray("friendIDs"));
            List<String> blockedIDs = convertJsonArrayToList(jsonData.getAsJsonArray("blockedIDs"));
            List<String> groupChannelURLs = convertJsonArrayToList(jsonData.getAsJsonArray("groupChannelURLs"));
            List<String> personalChannelURls = convertJsonArrayToList(jsonData.getAsJsonArray("personalChannelURls"));

            // Get channel URLs
            GroupChannelApi groupApiInstance = new GroupChannelApi(defaultClient);
            try {
                GcListChannelsResponse gcResponse = groupApiInstance.gcListChannels()
                        .apiToken(apiToken)
                        .userId(userId)
                        .execute();

                // Create GroupChat and PersonalChat objects
                List<GroupChat> groupChats = new ArrayList<>();
                List<GroupChat> personalChats = new ArrayList<>();
                List<SendBirdGroupChannel> channels = gcResponse.getChannels();
                if (channels != null) {
                    for (SendBirdGroupChannel sendBirdGroupChannel : channels) {
                        String channelURL = sendBirdGroupChannel.getChannelUrl();
                        if (groupChannelURLs.contains(channelURL)) {
                            groupChats.add(groupChatDataAccessObject.getGroupChat(sendBirdGroupChannel)); // Todo: implement this method
                        } else if (personalChannelURls.contains(channelURL)) {
                            personalChats.add(groupChatDataAccessObject.getPersonalChat(sendBirdGroupChannel)); // Todo: implement this method
                        }
                    }
                }

                // Initialize the user
                return userFactory.create(name, password, userId, biography, dateOfBirth, friendIDs, blockedIDs, groupChats, personalChats);

            } catch (ApiException e) {
                System.err.println("Exception when calling GroupChannelApi#gcListChannels");
                System.err.println("Status code: " + e.getCode());
                System.err.println("Reason: " + e.getResponseBody());
                System.err.println("Response headers: " + e.getResponseHeaders());
                e.printStackTrace();
            }
        } catch (ApiException e) {
            System.err.println("Exception when calling UserApi#listUsers");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
        return null;
    }

    private List<String> convertJsonArrayToList(JsonArray jsonArray) {
        List<String> newList = new ArrayList<>();
        for (JsonElement element : jsonArray) {
            newList.add(element.getAsString());
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

            UpdateUserByIdData updateUserByIdData = new UpdateUserByIdData()
                    .nickname(user.getName());

            try {
                // Update username on SendBird
                SendBirdUser result = userApiInstance.updateUserById(userId)
                        .apiToken(apiToken)
                        .updateUserByIdData(updateUserByIdData)
                        .execute();

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

            CreateUserData createUserData = new CreateUserData()
                    .userId(user.getID())
                    .nickname(user.getName()); // CreateUserData |
            try {
                // Update username on SendBird
                SendBirdUser result = userApiInstance.createUser()
                        .apiToken(apiToken)
                        .createUserData(createUserData)
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
