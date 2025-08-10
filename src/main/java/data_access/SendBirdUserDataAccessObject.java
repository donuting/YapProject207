package data_access;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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
import use_case.delete_message.DeleteMessageDataAccessInterface;
import use_case.join_chat.JoinChatDataAccessInterface;
import use_case.leave_chat.LeaveChatDataAccessInterface;
import use_case.load_group_chats.LoadGroupChatsDataAccessInterface;
import use_case.login.LoginUserDataAccessInterface;
import use_case.logout.LogoutUserDataAccessInterface;
import use_case.profile.UserProfileDataAccessInterface;
import use_case.remove_friend.RemoveFriendDataAccessInterface;
import use_case.send_message.SendMessageDataAccessInterface;
import use_case.signup.SignupUserDataAccessInterface;
import use_case.update_chat.UpdateChatDataAccessInterface;

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
        DeleteAccountDataAccessInterface,
        JoinChatDataAccessInterface,
        LeaveChatDataAccessInterface,
        LoadGroupChatsDataAccessInterface,
        UserProfileDataAccessInterface,
        SendMessageDataAccessInterface,
        DeleteMessageDataAccessInterface,
        UpdateChatDataAccessInterface {

    private static final String API_TOKEN = "7836d8100957f700df15d54313b455766090ea9f";
    private static final String APPLICATION_ID = "https://api-17448E6A-5733-470D-BCE0-7A4460C94A11.sendbird.com";
    private final UserFactory userFactory;
    private final GroupChatDataAccessObject groupChatDataAccessObject;
    private final MessageDataAccessObject messageDataAccessObject;
    private User currentUser = null;
    private GroupChat currentSelfChat = null;
    private final PantryUserDataAccessObject pantryUserDataAccessObject = new PantryUserDataAccessObject();

    public SendBirdUserDataAccessObject() {
        this.userFactory = new CommonUserFactory();
        this.groupChatDataAccessObject = new GroupChatDataAccessObject();
        this.messageDataAccessObject = new MessageDataAccessObject();
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
            List<String> friendIds = convertJsonArrayToList(jsonData.getAsJsonArray("friendIDs"));
            List<String> blockedIds = convertJsonArrayToList(jsonData.getAsJsonArray("blockedIDs"));
            List<String> groupChannelUrls = convertJsonArrayToList(jsonData.getAsJsonArray("groupChannelURLs"));
            List<String> personalChannelUrls = convertJsonArrayToList(jsonData.getAsJsonArray("personalChannelURLs"));
            String selfChatUrl = jsonData.get("selfChatURL").getAsString();

            // Get channel URLs
            ApiClient defaultClient = Configuration.getDefaultApiClient();
            defaultClient.setBasePath(APPLICATION_ID);
            UserApi apiInstance = new UserApi(defaultClient);

            // The below code is a different implementation of this method,  the
            // library we are using is bugged and prevents us from loading group chat objects.
            // This implementation instead uses OkHttp.

            final OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();

            final Request request = new Request.Builder()
                    .url(APPLICATION_ID + "/v3/users/" + userId + "/my_group_channels"
                            + "?user_id=" + userId
                            + "&show_empty=true"
                            + "&show_member=true")
                    .get()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Api-Token", API_TOKEN)
                    .build();
            try {
                final Response response = client.newCall(request).execute();
                final JSONObject responseBody = new JSONObject(response.body().string());
                if (response.code() == 200) {
                    // Create GroupChat and PersonalChat objects
                    List<GroupChat> groupChats = new ArrayList<>();
                    List<GroupChat> personalChats = new ArrayList<>();
                    GroupChat selfChat = null;
                    JSONArray channels = (JSONArray) responseBody.get("channels");
                    if (channels != null) {
                        for (Object channel : channels) {
                            String channelUrl = ((JSONObject) channel).get("channel_url").toString();
                            if (groupChannelUrls.contains(channelUrl)) {
                                groupChats.add(groupChatDataAccessObject.getGroupChat(((JSONObject) channel)));
                            }
                            else if (personalChannelUrls.contains(channelUrl)) {
                                personalChats.add(groupChatDataAccessObject.getGroupChat(((JSONObject) channel)));
                            }
                            else if (selfChatUrl.equals(channelUrl)) {
                                selfChat = groupChatDataAccessObject.getGroupChat(((JSONObject) channel));
                            }
                        }
                    }
                    // Initialize the user
                    return userFactory.create(username, password, userId, biography,
                            dateOfBirth, friendIds, blockedIds, groupChats, personalChats, selfChat);
                }
                else {
                    System.out.println(response.code());
                    System.out.println(response.body().string());
                    throw new RuntimeException(responseBody.getString("message"));
                }

            } catch (IOException | JSONException ex) {
                return null;
            }



//            try {
//                ListMyGroupChannelsResponse result = apiInstance.listMyGroupChannels(userId)
//                        .apiToken(API_TOKEN)
//                        .showEmpty(true)
//                        .showMember(true)
//                        .execute();
//
//                System.out.println(result);
//
//                // Create GroupChat and PersonalChat objects
//                List<GroupChat> groupChats = new ArrayList<>();
//                List<GroupChat> personalChats = new ArrayList<>();
//                GroupChat selfChat = null;
//                List<SendbirdGroupChannel> channels = result.getChannels();
//                if (channels != null) {
//                    for (SendbirdGroupChannel sendBirdGroupChannel : channels) {
//                        String channelUrl = sendBirdGroupChannel.getChannelUrl();
//                        if (groupChannelUrls.contains(channelUrl)) {
//                            groupChats.add(groupChatDataAccessObject.getGroupChat(sendBirdGroupChannel));
//                        }
//                        else if (personalChannelUrls.contains(channelUrl)) {
//                            personalChats.add(groupChatDataAccessObject.getGroupChat(sendBirdGroupChannel));
//                        }
//                        else if (selfChatUrl.equals(channelUrl)) {
//                            selfChat = groupChatDataAccessObject.getGroupChat(sendBirdGroupChannel);
//                        }
//                    }
//                }
//
//                // Initialize the user
//                return userFactory.create(username, password, userId, biography,
//                        dateOfBirth, friendIds, blockedIds, groupChats, personalChats, selfChat);
//            }
//            catch (ApiException e) {
//                System.err.println("Exception when calling GroupChannelApi#gcListChannels");
//                System.err.println("Status code: " + e.getCode());
//                System.err.println("Reason: " + e.getResponseBody());
//                System.err.println("Response headers: " + e.getResponseHeaders());
//                e.printStackTrace();

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

    /**
     * sends a message in a chat.
     *
     * @param message the message to be sent
     * @param chat    the chat in which the message will be sent
     */
    @Override
    public Message sendMessage(Message message, Chat chat) {
        return messageDataAccessObject.sendMessage(message, chat);
    }

    @Override
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Updates the system to delete a message from a chat.
     *
     * @param MID  the ID of the message to be deleted.
     * @param chat the chat in which the message was sent.
     */
    @Override
    public boolean deleteMessage(String MID, Chat chat) {
        return messageDataAccessObject.deleteMessage(MID, chat);
    }

    @Override
    public boolean leaveGroupChat(String channelUrl, String userId, String username) {
        if (groupChatDataAccessObject.leaveGroupChat(channelUrl, userId)) {
            pantryUserDataAccessObject.leaveChat(username, channelUrl);
            return true;
        }
        return false;
    }

    @Override
    public GroupChat getActiveGroupChat() {
        return groupChatDataAccessObject.getActiveGroupChat();
    }

    /**
     * Loads a GroupChat object given its channel URL
     *
     * @param channelUrl the channel URL.
     * @return the retrieved group chat.
     */
    @Override
    public GroupChat load(String channelUrl) {
        return groupChatDataAccessObject.load(channelUrl);
    }

    @Override
    public void setActiveGroupChat(GroupChat newChat) {
        groupChatDataAccessObject.setActiveGroupChat(newChat);
    }

    /**
     * Adds a user to a group chat given its channel URL, and returns the updated group chat.
     *
     * @param userId     the user's ID.
     * @param channelUrl the channel URL.
     * @return the updated group chat.
     */
    @Override
    public GroupChat addUser(String userId, String channelUrl) {
        return groupChatDataAccessObject.addUser(userId, channelUrl);
    }

    /**
     * Save a group chat to a user.
     *
     * @param newGroupChat the group chat.
     * @param username the user's name
     */
    @Override
    public void saveGroupChat(GroupChat newGroupChat, String username) {
        pantryUserDataAccessObject.saveGroupChat(username, newGroupChat.getChannelUrl());
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

    public void  deleteGroupChat(GroupChat groupChat) {
        groupChatDataAccessObject.delete(groupChat.getChannelUrl());
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

    /**
     * Updates the username, bio and date of birth for the given user ID.
     *
     * @param username    the new username.
     * @param bio         the new bio.
     * @param dateOfBirth the new date of birth.
     * @return the saved user's ID.
     */
    @Override
    public String saveProfile(String oldUsername, String username, String bio, String dateOfBirth) {
        if (existsByName(oldUsername)) {
            // Updates the user in Pantry
            String userId = pantryUserDataAccessObject.saveProfile(oldUsername, username, bio, dateOfBirth);

            // Updates the user in SendBird
            ApiClient defaultClient = Configuration.getDefaultApiClient();
            defaultClient.setBasePath(APPLICATION_ID);

            UserApi apiInstance = new UserApi(defaultClient);
            UpdateAUserRequest updateAUserRequest = new UpdateAUserRequest().nickname(username);
            try {
                SendbirdUser result = apiInstance.updateAUser(userId)
                        .apiToken(API_TOKEN)
                        .updateAUserRequest(updateAUserRequest)
                        .execute();
                System.out.println(result);
                return userId;
            } catch (ApiException e) {
                System.err.println("Exception when calling UserApi#updateAUser");
                System.err.println("Status code: " + e.getCode());
                System.err.println("Reason: " + e.getResponseBody());
                System.err.println("Response headers: " + e.getResponseHeaders());
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Gets user profile data for a user given their username.
     *
     * @param username the user's name
     * @return a list containing the user's ID, bio, and date of birth as strings
     */
    @Override
    public List<String> loadProfile(String username) {
        if (existsByName(username)) {
            return pantryUserDataAccessObject.loadProfile(username);
        }
        return null;
    }
}
