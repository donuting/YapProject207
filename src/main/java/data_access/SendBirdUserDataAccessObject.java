package data_access;

import entity.*;
import org.json.JSONObject;
import org.openapitools.client.model.*;
import org.sendbird.client.ApiClient;
import org.sendbird.client.ApiException;
import org.sendbird.client.Configuration;
import org.sendbird.client.api.GroupChannelApi;
import org.sendbird.client.api.MetadataApi;
import org.sendbird.client.api.UserApi;
import use_case.change_password.ChangePasswordUserDataAccessInterface;
import use_case.login.LoginUserDataAccessInterface;
import use_case.logout.LogoutUserDataAccessInterface;
import use_case.signup.SignupUserDataAccessInterface;

import java.util.ArrayList;
import java.util.Arrays;
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

    public SendBirdUserDataAccessObject(UserFactory userFactory, GroupChatDataAccessObject groupChatDataAccessObject) {
        this.userFactory = userFactory;
        this.groupChatDataAccessObject = groupChatDataAccessObject;
    }

    /**
     * Updates the system to record this user's password.
     *
     * @param user the user whose password is to be updated
     */
    @Override
    public void changePassword(User user) {

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

            // Get User Data
            SendBirdUser sendBirdUser = UserResponse.getUsers().getFirst();
            String name = sendBirdUser.getNickname();
            String userId = sendBirdUser.getUserId();
            JSONObject jsonData = ((JSONObject) sendBirdUser.getMetadata());
            String password = jsonData.getString("password");
            String biography = jsonData.getString("password");
            String dateOfBirth = jsonData.getString("dateOfBirth");
            List<String> friendIDs= Arrays.stream(jsonData.getString("friendIDs").split(",")).toList();
            List<String> blockedIDs= Arrays.stream(jsonData.getString("blockedIDs").split(",")).toList();

            // Get channel URLs
            GroupChannelApi groupApiInstance = new GroupChannelApi(defaultClient);
            try {
                GcListChannelsResponse GcResponse = groupApiInstance.gcListChannels()
                        .apiToken(apiToken)
                        .userId(userId)
                        .execute();

                List<GroupChat> groupChats = new ArrayList<GroupChat>();
                List<PersonalChat> personalChats = new ArrayList<PersonalChat>(); // Todo: Separate PersonalChat objects from GroupChat objects
                for (SendBirdGroupChannel sendBirdGroupChannel : GcResponse.getChannels()) {
                    String channelURL = sendBirdGroupChannel.getChannelUrl();
                    groupChats.add(groupChatDataAccessObject.get(channelURL)); // Todo: implement this method
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

    /**
     * Returns the username of the curren user of the application.
     *
     * @return the username of the current user; null indicates that no one is logged into the application.
     */
    @Override
    public String getCurrentUsername() {
        return "";
    }

    /**
     * Sets the username indicating who is the current user of the application.
     *
     * @param username the new current username; null to indicate that no one is currently logged into the application.
     */
    @Override
    public void setCurrentUsername(String username) {

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

    public boolean existsByID(String ID) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath(APPLICATION_ID);

        UserApi apiInstance = new UserApi(defaultClient);
        String apiToken = API_TOKEN;

        try {
            ListUsersResponse result = apiInstance.listUsers()
                    .apiToken(apiToken)
                    .userIds(ID)
                    .execute();

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
        // If the user is already in the database
        if (existsByID(user.getID())) {
            ApiClient defaultClient = Configuration.getDefaultApiClient();
            defaultClient.setBasePath(APPLICATION_ID);

            UserApi userApiInstance = new UserApi(defaultClient);
            MetadataApi metadataApiInstance = new MetadataApi(defaultClient);

            String userId = user.getID(); // String |
            String apiToken = API_TOKEN; // String |
            JSONObject metadata = user.getMetadata();

            UpdateUserByIdData updateUserByIdData = new UpdateUserByIdData()
                    .nickname(user.getName());
            UpdateUserMetadataData updateUserMetadataData = new UpdateUserMetadataData()
                    .metadata(metadata);
            try {
                SendBirdUser result = userApiInstance.updateUserById(userId)
                        .apiToken(apiToken)
                        .updateUserByIdData(updateUserByIdData)
                        .execute();
                UpdateUserMetadataResponse response = metadataApiInstance.updateUserMetadata(userId)
                        .apiToken(apiToken)
                        .updateUserMetadataData(updateUserMetadataData)
                        .execute();

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
            ApiClient defaultClient = Configuration.getDefaultApiClient();
            defaultClient.setBasePath(APPLICATION_ID);

            UserApi apiInstance = new UserApi(defaultClient);
            String apiToken = API_TOKEN; // String |

            CreateUserData createUserData = new CreateUserData()
                    .userId(user.getID())
                    .nickname(user.getName())
                    .metadata(user.getMetadata()); // CreateUserData |
            try {
                SendBirdUser result = apiInstance.createUser()
                        .apiToken(apiToken)
                        .createUserData(createUserData)
                        .execute();

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
