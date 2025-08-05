package data_access;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import endercrypt.library.jpantry.JPantry;
import endercrypt.library.jpantry.PantryBasket;;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

/**
 * The DAO for accessing user data in Pantry.
 * See <a href="https://github.com/EnderCrypt/JPantry?tab=readme-ov-file">...</a> for documentation of the library being used.
 * See <a href="https://github.com/EnderCrypt/JPantry/blob/master/src/main/java/endercrypt/library/jpantry/JPantry.java">...</a> for examples of usage.
 */
public class PantryUserDataAccessObject {

    private final JPantry pantry = new JPantry.Builder()
            .setToken(API_TOKEN)
            .setCacheTime(500) // in milliseconds (default is 1000, network requests usually take 300+ ms anyway)
            .login();
    private static final String API_TOKEN = "0d4b194b-2f55-4c9e-8dee-a00d3ecf3cc9";

    public void save(JsonObject userData) {
        final String basketName = userData.get("username").getAsString(); // The name of the user's basket in Pantry is the username
        PantryBasket basket = pantry.getBasket(basketName);
        basket.setJson(userData).complete();
    }

    public void changePassword(String username, String password) {
        PantryBasket basket = pantry.getBasket(username);
        JsonObject updateData = new JsonObject();
        updateData.addProperty("password", password);
        basket.mergeJson(updateData).complete();
    }

    public void changeUsername(String username) {
        // unimplemented for now
    }

    public String blockFriend(String currentUsername, String blockedUsername) {
        // This method only allows blocking one-way, not mutual blocking
        String blockedId = getUserDataFromUsername(blockedUsername).get("userId").getAsString();
        JsonArray blockedIDs = getUserDataFromUsername(currentUsername).getAsJsonArray("blockedIDs");
        blockedIDs.add(blockedId);

        PantryBasket basket = pantry.getBasket(currentUsername);
        JsonObject updateData = new JsonObject();
        updateData.add("blockedIDs", blockedIDs);
        basket.mergeJson(updateData).complete();
        return blockedId;
    }

    public JsonObject getUserDataFromUsername(String username) {
        PantryBasket basket = pantry.getBasket(username);
        return basket.getJson().complete();
    }

    public boolean updateBio(String username, String biography) {
        PantryBasket basket = pantry.getBasket(username);
        JsonObject updateData = new JsonObject();
        updateData.addProperty("biography", biography);
        basket.mergeJson(updateData).complete();
        return true;
    }

    public boolean updateDateOfBirth(String username, String dateOfBirth) {
        PantryBasket basket = pantry.getBasket(username);
        JsonObject updateData = new JsonObject();
        updateData.addProperty("dateOfBirth", dateOfBirth);
        basket.mergeJson(updateData).complete();
        return true;
    }

    public boolean addFriend(String currentUsername, String friendUsername) {
        PantryBasket userBasket = pantry.getBasket(currentUsername);
        PantryBasket friendBasket = pantry.getBasket(friendUsername);
        String currentId = userBasket.getJson().complete().get("userId").getAsString();
        String friendId = friendBasket.getJson().complete().get("userId").getAsString();

        // Add friend to current user's friends
        JsonObject currentUpdateData = new JsonObject();
        JsonArray currentUserFriendIDs = getUserDataFromUsername(friendUsername).getAsJsonArray("friendIDs");
        currentUserFriendIDs.add(currentId);
        currentUpdateData.add("friendIDs", currentUserFriendIDs);
        userBasket.mergeJson(currentUpdateData).complete();

        // Add current user to friend's friends
        JsonObject friendUpdateData = new JsonObject();
        JsonArray friendFriendIDs = getUserDataFromUsername(friendUsername).getAsJsonArray("friendIDs");
        friendFriendIDs.add(friendId);
        friendUpdateData.add("friendIDs", friendFriendIDs);
        userBasket.mergeJson(friendUpdateData).complete();

        return true;
    }

    public boolean alreadyFriend(String currentUsername, String friendUsername) {
        JsonArray currentUserFriendsJson = getUserDataFromUsername(currentUsername).get("friendIDs").getAsJsonArray();
        JsonElement friendID = getUserDataFromUsername(friendUsername).get("userId");
        return currentUserFriendsJson.contains(friendID);
    }

    public boolean removeFriend(String currentUsername, String removedUsername) {
        PantryBasket userBasket = pantry.getBasket(currentUsername);
        PantryBasket friendBasket = pantry.getBasket(removedUsername);
        JsonElement currentId = userBasket.getJson().complete().get("userId");
        JsonElement friendId = friendBasket.getJson().complete().get("userId");

        // Remove friend from current user's friends
        JsonObject currentUpdateData = new JsonObject();
        JsonArray currentUserFriendIDs = getUserDataFromUsername(currentUsername).getAsJsonArray("friendIDs");
        currentUserFriendIDs.remove(currentId);
        currentUpdateData.add("friendIDs", currentUserFriendIDs);
        userBasket.mergeJson(currentUpdateData).complete();

        // Remove current user from friend's friends
        JsonObject friendUpdateData = new JsonObject();
        JsonArray friendFriendIDs = getUserDataFromUsername(removedUsername).getAsJsonArray("friendIDs");
        friendFriendIDs.remove(friendId);
        friendUpdateData.add("friendIDs", friendFriendIDs);
        userBasket.mergeJson(friendUpdateData).complete();

        return true;
    }

    public void saveGroupChat(String username, String channelURL) {
        JsonArray groupChats = getUserDataFromUsername(username).getAsJsonArray("groupChannelURLs");
        groupChats.add(channelURL);

        PantryBasket basket = pantry.getBasket(username);
        JsonObject updateData = new JsonObject();
        updateData.add("groupChannelURLs", groupChats);
        basket.mergeJson(updateData).complete();
    }
}
