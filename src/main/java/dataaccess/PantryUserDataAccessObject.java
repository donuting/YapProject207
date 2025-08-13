package dataaccess;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import endercrypt.library.jpantry.JPantry;
import endercrypt.library.jpantry.PantryBasket;

import java.util.ArrayList;
import java.util.List;

/**
 * The DAO for accessing user data in Pantry. This is user to store data of users in this app.
 * See <a href="https://github.com/EnderCrypt/JPantry?tab=readme-ov-file">...</a>
 * for documentation of the library being used.
 * See <a href="https://github.com/EnderCrypt/JPantry/blob/master/src/main/java/endercrypt/library/jpantry/JPantry.java">...</a>
 * for examples of usage.
 */
public class PantryUserDataAccessObject {

    private static final int CACHE_TIME = 1000;
    private static final String API_TOKEN = "0d4b194b-2f55-4c9e-8dee-a00d3ecf3cc9";
    private static final String USER_ID = "userId";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String DATE_OF_BIRTH = "dateOfBirth";
    private static final String BIOGRAPHY = "biography";
    private static final String BLOCKED_IDS = "blockedIDs";
    private static final String FRIEND_IDS = "friendIDs";
    private static final String GROUP_CHANNEL_URLS = "groupChannelURLs";
    private static final String PERSONAL_CHANNEL_URLS = "personalChannelURLs";
    private final JPantry pantry = new JPantry.Builder()
            .setToken(API_TOKEN)
            .setCacheTime(CACHE_TIME)
            .login();

    /**
     * Saves a user and their user data to Pantry.
     *
     * @param userData the user data.
     */
    public void save(JsonObject userData) {
        // The name of the user's basket in Pantry is the username
        final String basketName = userData.get(USERNAME).getAsString();
        PantryBasket basket = pantry.getBasket(basketName);
        basket.setJson(userData).queue();
    }

    /**
     * Updates the password of a user in Pantry.
     *
     * @param username the username.
     * @param password the new password.
     */
    public void changePassword(String username, String password) {
        PantryBasket basket = pantry.getBasket(username);
        JsonObject updateData = new JsonObject();
        updateData.addProperty(PASSWORD, password);
        basket.mergeJson(updateData).queue();
    }

    /**
     * Lets a user block another user and updates this information in Pantry.
     *
     * @param currentUsername the username blocking someone.
     * @param blockedUsername the user being blocked.
     * @param removedPersonalChatUrl the URL of the personal chat being removed
     */
    public void blockFriend(String currentUsername, String blockedUsername, String removedPersonalChatUrl) {
        // This method only allows blocking one-way, not mutual blocking
        final PantryBasket userBasket = pantry.getBasket(currentUsername);
        final PantryBasket blockedBasket = pantry.getBasket(blockedUsername);
        final JsonObject currentUserData = getUserDataFromUsername(currentUsername);
        final JsonObject blockedUserData = getUserDataFromUsername(blockedUsername);
        final JsonElement currentId = currentUserData.get(USER_ID);
        final JsonElement blockedId = blockedUserData.get(USER_ID);

        // Current user blocks other user:
        JsonArray currentUserFriendIds = currentUserData.getAsJsonArray(FRIEND_IDS);
        JsonArray currentUserBlockedIds = currentUserData.getAsJsonArray(BLOCKED_IDS);
        JsonArray currentUserPersonalChatUrls = currentUserData.getAsJsonArray(PERSONAL_CHANNEL_URLS);
        JsonArray newCurrentUserPersonalChatUrls = new JsonArray();
        currentUserFriendIds.remove(blockedId);
        currentUserBlockedIds.add(blockedId);
        for (JsonElement personalChatUrl : currentUserPersonalChatUrls) {
            if (!personalChatUrl.getAsString().equals(removedPersonalChatUrl)) {
                newCurrentUserPersonalChatUrls.add(personalChatUrl);
            }
        }
        currentUserData.add(FRIEND_IDS, currentUserFriendIds);
        currentUserData.add(BLOCKED_IDS, currentUserBlockedIds);
        currentUserData.add(PERSONAL_CHANNEL_URLS, newCurrentUserPersonalChatUrls);
        userBasket.setJson(currentUserData).queue();

        // Other user gets blocked
        JsonArray blockedUserFriendIds = blockedUserData.getAsJsonArray(FRIEND_IDS);
        JsonArray blockedUserPersonalChatUrls = blockedUserData.getAsJsonArray(PERSONAL_CHANNEL_URLS);
        JsonArray newBlockedUserPersonalChatUrls = new JsonArray();
        blockedUserFriendIds.remove(currentId);
        for (JsonElement personalChatUrl : blockedUserPersonalChatUrls) {
            if (!personalChatUrl.getAsString().equals(removedPersonalChatUrl)) {
                newBlockedUserPersonalChatUrls.add(personalChatUrl);
            }
        }
        blockedUserData.add(FRIEND_IDS, blockedUserFriendIds);
        blockedUserData.add(PERSONAL_CHANNEL_URLS, newBlockedUserPersonalChatUrls);
        blockedBasket.setJson(blockedUserData).queue();
    }

    /**
     * Gets a user's userdata from their username.
     *
     * @param username the user's username.
     * @return the user's data as a JsonObject.
     */
    public JsonObject getUserDataFromUsername(String username) {
        PantryBasket basket = pantry.getBasket(username);
        return basket.getJson().complete();
    }

    /**
     * Updates a user's biography.
     *
     * @param username the user's username.
     * @param biography the updated biography.
     * @return true if successful.
     */
    public boolean updateBio(String username, String biography) {
        PantryBasket basket = pantry.getBasket(username);
        JsonObject updateData = new JsonObject();
        updateData.addProperty(BIOGRAPHY, biography);
        basket.mergeJson(updateData).queue();
        return true;
    }

    /**
     * Updates a user's date of birth.
     *
     * @param username the user's username.
     * @param dateOfBirth the updated date of birth.
     * @return true if successful.
     */
    public boolean updateDateOfBirth(String username, String dateOfBirth) {
        PantryBasket basket = pantry.getBasket(username);
        JsonObject updateData = new JsonObject();
        updateData.addProperty(DATE_OF_BIRTH, dateOfBirth);
        basket.mergeJson(updateData).queue();
        return true;
    }

    /**
     * Allows two users to become friends with other, and updates this information in Pantry.
     *
     * @param currentUsername the current user's username.
     * @param friendUsername the username of the friend to be added.
     * @param personalChannelUrl the URL of the new personal chat between the two users.
     * @return true if successful.
     */
    public boolean addFriend(String currentUsername, String friendUsername, String personalChannelUrl) {
        final PantryBasket currentUserBasket = pantry.getBasket(currentUsername);
        final PantryBasket friendBasket = pantry.getBasket(friendUsername);
        final String currentId = currentUserBasket.getJson().complete().get(USER_ID).getAsString();
        final String friendId = friendBasket.getJson().complete().get(USER_ID).getAsString();

        // For updating the current user
        JsonObject currentUpdateData = new JsonObject();

        // Add friend to current user's friends
        JsonArray currentUserFriendIds = new JsonArray();
        currentUserFriendIds.add(friendId);
        currentUpdateData.add(FRIEND_IDS, currentUserFriendIds);

        // Add personal chat to current user's personal chats
        JsonArray currentUserPersonalChatUrls = new JsonArray();
        currentUserPersonalChatUrls.add(personalChannelUrl);
        currentUpdateData.add(PERSONAL_CHANNEL_URLS, currentUserPersonalChatUrls);

        currentUserBasket.mergeJson(currentUpdateData).queue();

        // For updating the friend
        JsonObject friendUpdateData = new JsonObject();

        // Add current user to friend's friends
        JsonArray friendFriendIds = new JsonArray();
        friendFriendIds.add(currentId);
        friendUpdateData.add(FRIEND_IDS, friendFriendIds);

        JsonArray friendPersonalChatUrls = new JsonArray();
        friendPersonalChatUrls.add(personalChannelUrl);
        friendUpdateData.add(PERSONAL_CHANNEL_URLS, friendPersonalChatUrls);

        friendBasket.mergeJson(friendUpdateData).queue();

        return true;
    }

    /**
     * Returns whether two users are already friends.
     *
     * @param currentUsername the current user's username.
     * @param friendUsername the username of the other user.
     * @return true if the two users are already friends.
     */
    public boolean alreadyFriend(String currentUsername, String friendUsername) {
        JsonArray currentUserFriendsJson = getUserDataFromUsername(currentUsername).get(FRIEND_IDS).getAsJsonArray();
        JsonElement friendID = getUserDataFromUsername(friendUsername).get(USER_ID);
        return currentUserFriendsJson.contains(friendID);
    }

    /**
     * Allows a user to unfriend another user, and updates this information in Pantry.
     *
     * @param currentUsername the current user's username.
     * @param removedUsername the username of the friend to be removed.
     * @return true if successful.
     */
    public boolean removeFriend(String currentUsername, String removedUsername, String removedPersonalChatUrl) {
        final PantryBasket userBasket = pantry.getBasket(currentUsername);
        final PantryBasket removedBasket = pantry.getBasket(removedUsername);
        final JsonObject currentUserData = getUserDataFromUsername(currentUsername);
        final JsonObject removedUserData = getUserDataFromUsername(removedUsername);
        final JsonElement currentId = currentUserData.get(USER_ID);
        final JsonElement removedId = removedUserData.get(USER_ID);

        // Current user removes other user:
        JsonArray currentUserFriendIds = currentUserData.getAsJsonArray(FRIEND_IDS);
        JsonArray currentUserPersonalChatUrls = currentUserData.getAsJsonArray(PERSONAL_CHANNEL_URLS);
        JsonArray newCurrentUserPersonalChatUrls = new JsonArray();
        currentUserFriendIds.remove(removedId);
        for (JsonElement personalChatUrl : currentUserPersonalChatUrls) {
            if (!personalChatUrl.getAsString().equals(removedPersonalChatUrl)) {
                newCurrentUserPersonalChatUrls.add(personalChatUrl);
            }
        }
        currentUserData.add(FRIEND_IDS, currentUserFriendIds);
        currentUserData.add(PERSONAL_CHANNEL_URLS, newCurrentUserPersonalChatUrls);
        userBasket.setJson(currentUserData).queue();

        // Other user gets removed
        JsonArray removedUserFriendIds = removedUserData.getAsJsonArray(FRIEND_IDS);
        JsonArray removedUserPersonalChatUrls = removedUserData.getAsJsonArray(PERSONAL_CHANNEL_URLS);
        JsonArray newRemovedUserPersonalChatUrls = new JsonArray();
        removedUserFriendIds.remove(currentId);
        for (JsonElement personalChatUrl : removedUserPersonalChatUrls) {
            if (!personalChatUrl.getAsString().equals(removedPersonalChatUrl)) {
                newRemovedUserPersonalChatUrls.add(personalChatUrl);
            }
        }
        removedUserData.add(FRIEND_IDS, removedUserFriendIds);
        removedUserData.add(PERSONAL_CHANNEL_URLS, newRemovedUserPersonalChatUrls);
        removedBasket.setJson(removedUserData).queue();

        return true;
    }

    /**
     * Allows a user to add a group chat to their list of group chats in Pantry.
     *
     * @param username the user's username.
     * @param channelUrl the URL of the group chat to be added
     */
    public void saveGroupChat(String username, String channelUrl) {
        JsonArray groupChats = new JsonArray();
        groupChats.add(channelUrl);

        PantryBasket basket = pantry.getBasket(username);
        JsonObject updateData = new JsonObject();
        updateData.add(GROUP_CHANNEL_URLS, groupChats);
        basket.mergeJson(updateData).queue();
    }

    /**
     * Allows a user to add a personal chat to their list of personal chats in Pantry.
     *
     * @param username the user's username.
     * @param channelUrl the URL of the personal chat to be added
     */
    public void savePersonalChat(String username, String channelUrl) {
        JsonArray personalChats = new JsonArray();
        personalChats.add(channelUrl);

        PantryBasket basket = pantry.getBasket(username);
        JsonObject updateData = new JsonObject();
        updateData.add(PERSONAL_CHANNEL_URLS, personalChats);
        basket.mergeJson(updateData).queue();
    }

    /**
     * Deletes a user in Pantry.
     *
     * @param username the name of the user to be deleted.
     */
    public void deleteUser(String username) {
        PantryBasket basket = pantry.getBasket(username);
        basket.deleteJson().queue();
    }

    /**
     * Allows a user to leave a group chat, and updates that information in Pantry
     *
     * @param username the name of the user
     * @param channelUrl the URL of the group chat
     */
    public void leaveChat(String username, String channelUrl) {
        JsonObject userData = getUserDataFromUsername(username);
        JsonArray groupChatUrls = userData.getAsJsonArray(GROUP_CHANNEL_URLS);
        JsonArray updatedGroupChatUrls = new JsonArray();
        for (JsonElement groupChatUrl : groupChatUrls) {
            if (!groupChatUrl.getAsString().equals(channelUrl)) {
                updatedGroupChatUrls.add(groupChatUrl);
            }
        }
        PantryBasket basket = pantry.getBasket(username);
        userData.add(GROUP_CHANNEL_URLS, updatedGroupChatUrls);
        basket.setJson(userData).queue();
    }

    /**
     * Saves the profile of a user.
     *
     * @param oldUsername the old username of the user
     * @param username the new username of the user
     * @param bio the new bio
     * @param dateOfBirth the new date of birth
     * @return the ID of the updated user
     */
    public String saveProfile(String oldUsername, String username, String bio, String dateOfBirth) {
        JsonObject userData = getUserDataFromUsername(oldUsername);
        userData.addProperty(USERNAME, username);
        userData.addProperty(BIOGRAPHY, bio);
        userData.addProperty(DATE_OF_BIRTH, dateOfBirth);
        save(userData);
        deleteUser(oldUsername);
        return userData.get(USER_ID).getAsString();
    }

    /**
     * Gets user profile data for a user given their username.
     *
     * @param username the user's name
     * @return a list containing the user's ID, bio, and date of birth as strings
     */
    public List<String> loadProfile(String username) {
        JsonObject userData = getUserDataFromUsername(username);
        List<String> profileData = new ArrayList<>();
        profileData.add(userData.get(USER_ID).getAsString());
        profileData.add(userData.get(BIOGRAPHY).getAsString());
        profileData.add(userData.get(DATE_OF_BIRTH).getAsString());
        return profileData;
    }
}
