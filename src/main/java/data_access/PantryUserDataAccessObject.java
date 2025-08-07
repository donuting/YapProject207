package data_access;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import endercrypt.library.jpantry.JPantry;
import endercrypt.library.jpantry.PantryBasket;

/**
 * The DAO for accessing user data in Pantry. This is user to store data of users in this app.
 * See <a href="https://github.com/EnderCrypt/JPantry?tab=readme-ov-file">...</a>
 * for documentation of the library being used.
 * See <a href="https://github.com/EnderCrypt/JPantry/blob/master/src/main/java/endercrypt/library/jpantry/JPantry.java">...</a>
 * for examples of usage.
 */
public class PantryUserDataAccessObject {

    private static final int CACHE_TIME = 500;
    private static final String API_TOKEN = "0d4b194b-2f55-4c9e-8dee-a00d3ecf3cc9";
    private static final String USER_ID = "userId";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String DATE_OF_BIRTH = "dateOfBirth";
    private static final String BIOGRAPHY = "biography";
    private static final String BLOCKED_IDS = "blockedIDs";
    private static final String FRIEND_IDS = "friendIDs";
    private static final String GROUP_CHANNEL_URLS = "groupChannelURLs";
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
        basket.setJson(userData).complete();
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
        basket.mergeJson(updateData).complete();
    }

    /**
     * Lets a user block another user and updates this information in Pantry.
     *
     * @param currentUsername the username blocking someone.
     * @param blockedUsername the user being blocked.
     * @return the ID of the blocked user.
     */
    public String blockFriend(String currentUsername, String blockedUsername) {
        // This method only allows blocking one-way, not mutual blocking
        String blockedId = getUserDataFromUsername(blockedUsername).get(USER_ID).getAsString();
        JsonArray blockedIds = getUserDataFromUsername(currentUsername).getAsJsonArray(BLOCKED_IDS);
        blockedIds.add(blockedId);

        PantryBasket basket = pantry.getBasket(currentUsername);
        JsonObject updateData = new JsonObject();
        updateData.add(BLOCKED_IDS, blockedIds);
        basket.mergeJson(updateData).complete();
        return blockedId;
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
        basket.mergeJson(updateData).complete();
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
        basket.mergeJson(updateData).complete();
        return true;
    }

    /**
     * Allows two users to become friends with other, and updates this information in Pantry.
     *
     * @param currentUsername the current user's username.
     * @param friendUsername the username of the friend to be added.
     * @return true if successful.
     */
    public boolean addFriend(String currentUsername, String friendUsername) {
        final PantryBasket userBasket = pantry.getBasket(currentUsername);
        final PantryBasket friendBasket = pantry.getBasket(friendUsername);
        final String currentId = userBasket.getJson().complete().get(USER_ID).getAsString();
        final String friendId = friendBasket.getJson().complete().get(USER_ID).getAsString();

        // Add friend to current user's friends
        JsonObject currentUpdateData = new JsonObject();
        JsonArray currentUserFriendIds = getUserDataFromUsername(friendUsername).getAsJsonArray(FRIEND_IDS);
        currentUserFriendIds.add(currentId);
        currentUpdateData.add(FRIEND_IDS, currentUserFriendIds);
        userBasket.mergeJson(currentUpdateData).complete();

        // Add current user to friend's friends
        JsonObject friendUpdateData = new JsonObject();
        JsonArray friendFriendIds = getUserDataFromUsername(friendUsername).getAsJsonArray(FRIEND_IDS);
        friendFriendIds.add(friendId);
        friendUpdateData.add(FRIEND_IDS, friendFriendIds);
        userBasket.mergeJson(friendUpdateData).complete();

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
    public boolean removeFriend(String currentUsername, String removedUsername) {
        final PantryBasket userBasket = pantry.getBasket(currentUsername);
        final PantryBasket friendBasket = pantry.getBasket(removedUsername);
        final JsonElement currentId = userBasket.getJson().complete().get(USER_ID);
        final JsonElement friendId = friendBasket.getJson().complete().get(USER_ID);

        // Remove friend from current user's friends
        JsonObject currentUpdateData = new JsonObject();
        JsonArray currentUserFriendIds = getUserDataFromUsername(currentUsername).getAsJsonArray(FRIEND_IDS);
        currentUserFriendIds.remove(currentId);
        currentUpdateData.add(FRIEND_IDS, currentUserFriendIds);
        userBasket.mergeJson(currentUpdateData).complete();

        // Remove current user from friend's friends
        JsonObject friendUpdateData = new JsonObject();
        JsonArray friendFriendIds = getUserDataFromUsername(removedUsername).getAsJsonArray(FRIEND_IDS);
        friendFriendIds.remove(friendId);
        friendUpdateData.add(FRIEND_IDS, friendFriendIds);
        userBasket.mergeJson(friendUpdateData).complete();

        return true;
    }

    /**
     * Allows a user to add a group chat to their list of group chats in Pantry.
     *
     * @param username the user's username.
     * @param channelUrl the URL of the group chat to be added
     */
    public void saveGroupChat(String username, String channelUrl) {
        JsonArray groupChats = getUserDataFromUsername(username).getAsJsonArray(GROUP_CHANNEL_URLS);
        groupChats.add(channelUrl);

        PantryBasket basket = pantry.getBasket(username);
        JsonObject updateData = new JsonObject();
        updateData.add(GROUP_CHANNEL_URLS, groupChats);
        basket.mergeJson(updateData).complete();
    }

    /**
     * Deletes a user in Pantry.
     *
     * @param username the name of the user to be deleted.
     */
    public void deleteUser(String username) {
        pantry.setInformation(username, null);
    }
}
