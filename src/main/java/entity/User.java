package entity;

import com.google.gson.JsonObject;

/**
 * The representation of a user in our program.
 */
public interface User {

    /**
     * Returns the username of the user.
     * @return the username of the user.
     */
    String getName();

    /**
     * Returns the password of the user.
     * @return the password of the user.
     */
    String getPassword();

    /**
     * Returns the ID of the user.
     * @return the ID of the user.
     */
    String getID();

    /**
     * Returns a JSONObject containing user data.
     * @return the user data of the user.
     */
    JsonObject getUserData();

    /**
     * Adds a group chat to the user's list of group chats.
     * @param groupChat the group chat.
     */
    void addGroupChat(GroupChat groupChat);


}
