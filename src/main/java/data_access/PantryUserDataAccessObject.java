package data_access;

import endercrypt.library.jpantry.JPantry;
import endercrypt.library.jpantry.PantryBasket;;
import com.google.gson.JsonObject;

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

    public JsonObject getUserData(String userId) {
        PantryBasket basket = pantry.getBasket(userId);
        return basket.getJson().complete();
    }

    public void save(JsonObject userData) {
        final String basketName = userData.get("userID").getAsString(); // The name of the user's basket in Pantry is the User ID
        PantryBasket basket = pantry.getBasket(basketName);
        basket.setJson(userData);
    }

    public void changePassword(String userId, String password) {
        PantryBasket basket = pantry.getBasket(userId);
        JsonObject updateData = new JsonObject();
        updateData.addProperty("password", userId);
        basket.mergeJson(updateData);
    }
}
