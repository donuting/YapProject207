package data_access;

import com.google.gson.JsonArray;
import endercrypt.library.jpantry.JPantry;
import endercrypt.library.jpantry.PantryBasket;;
import com.google.gson.JsonObject;

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

    public boolean blockFriend(String currentUsername, String blockedUsername) {
        // This method only allows blocking one-way, not mutual blocking
        String blockedId = getUserDataFromUsername(blockedUsername).get("userId").getAsString();
        JsonArray blockedIDs = getUserDataFromUsername(currentUsername).getAsJsonArray("blockedIDs");
        blockedIDs.add(blockedId);

        PantryBasket basket = pantry.getBasket(currentUsername);
        JsonObject updateData = new JsonObject();
        updateData.add("blockedIDs", blockedIDs);
        basket.mergeJson(updateData).complete();
        return true;
    }

    public JsonObject getUserDataFromUsername(String username) {
        PantryBasket basket = pantry.getBasket(username);
        return basket.getJson().complete();
    }
}
