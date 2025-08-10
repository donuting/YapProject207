package entity;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple implementation of the User interface.
 */
public class CommonUser implements User {

    private String name;
    private String password;
    private final String ID;
    private String biography;
    private String dateOfBirth;
    private List<String> friendIDs;
    private List<String> blockedIDs;
    private List<GroupChat> groupChats;
    private List<GroupChat> personalChats;
    private GroupChat selfChat;

    /**
     * A constructor for the CommonUser class. This should only be used in the signup and login use cases.
     */
    public CommonUser(String name,
                      String password,
                      String ID,
                      String biography,
                      String dateOfBirth,
                      List<String> friendIDs,
                      List<String> blockedIDs,
                      List<GroupChat> groupChats,
                      List<GroupChat> personalChats) {
        this.name = name;
        this.password = password;
        this.ID = Objects.requireNonNullElseGet(ID, this::generateID);
        this.biography = biography;
        this.dateOfBirth = dateOfBirth;
        this.friendIDs = friendIDs;
        this.blockedIDs = blockedIDs;
        this.groupChats = groupChats;
        this.personalChats = personalChats;
        this.selfChat = null;
    }

    public CommonUser(String name,
                      String password,
                      String ID,
                      String biography,
                      String dateOfBirth,
                      List<String> friendIDs,
                      List<String> blockedIDs,
                      List<GroupChat> groupChats,
                      List<GroupChat> personalChats,
                      GroupChat selfChat) {
        this.name = name;
        this.password = password;
        this.ID = Objects.requireNonNullElseGet(ID, this::generateID);
        this.biography = biography;
        this.dateOfBirth = dateOfBirth;
        this.friendIDs = friendIDs;
        this.blockedIDs = blockedIDs;
        this.groupChats = groupChats;
        this.personalChats = personalChats;
        this.selfChat = selfChat;
    }

    // Necessary methods

    private String generateID() {
        return this.name;
        // Alternative implementation
        // java.util.Random rng = new java.util.Random();
        // return Integer.toString(rng.nextInt(1000000));
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public String setPassword(String password) {
        //TO DO: need to test conditions on password (is it long enough? so on)
        if (password == null || password.length() < 8) {
            return "Password must be at least 8 characters long.";
        }

        boolean hasDigit = false;
        boolean hasUpper = false;
        boolean hasLower = false;

        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (Character.isDigit(c)) {
                hasDigit = true;
            }
            else if (Character.isUpperCase(c)) {
                hasUpper = true;
            }
            else if (Character.isLowerCase(c)) {
                hasLower = true;
            }
            if (hasDigit && hasUpper && hasLower) {
                // End early if all conditions met
                break;
            }
        }

        if (!hasDigit || !hasUpper || !hasLower) {
            return "Password must contain at least one digit, one uppercase letter, and one lowercase letter.";
        }

        this.password = password;
        return "Successfully set password.";
    }

    /**
     * generates an ID for the User.
     * @return generated ID.
     */

    @Override
    public String getID() {
        return ID;
    }

    /**
     * Returns a JSONObject containing all user data.
     * @return the user data of the user.
     */
    @Override
    public JsonObject getUserData() {
        JsonObject userData = new JsonObject();
        userData.addProperty("username", name);
        userData.addProperty("userId", ID);
        userData.addProperty("password", password);
        userData.addProperty("biography", biography);
        userData.addProperty("dateOfBirth", dateOfBirth);

        JsonArray friendIDsJson = new JsonArray();
        JsonArray blockedIDsJson = new JsonArray();
        JsonArray groupChannelURLsJson = new JsonArray();
        JsonArray personalChannelURLsJson = new JsonArray();
        friendIDs.forEach(friendIDsJson::add);
        blockedIDs.forEach(blockedIDsJson::add);
        for (GroupChat groupChat : groupChats) {
            groupChannelURLsJson.add(groupChat.getChannelUrl());
        }
        for (GroupChat personalChat : personalChats) {
            personalChannelURLsJson.add(personalChat.getChannelUrl());
        }
        userData.add("friendIDs", friendIDsJson);
        userData.add ("blockedIDs", blockedIDsJson);
        userData.add("groupChannelURLs", groupChannelURLsJson);
        userData.add("personalChannelURLs", personalChannelURLsJson);
        if (selfChat != null) {
            userData.addProperty("selfChatURL", selfChat.getChannelUrl());
        } else {
            userData.addProperty("selfChatURL", "");
        }
        return userData;
    }

    @Override
    public boolean EditBiography(String bio) {
        this.biography = bio;
        return true;
    }

    @Override
    public boolean EditDOB(String DOB) {
        //TODO: need to verify that the provided DOB is in the correct format
        if (DOB.length() == 8) {
            this.dateOfBirth = DOB;
            return true;
        }
        return false;
    }

    @Override
    public String getDOB() {
        return this.dateOfBirth;
    }

    @Override
    public String getBio() {
        return this.biography;
    }

    /**
     * Adds a friend to the user.
     * @param userId The friend to be added.
     * @return true if successful otherwise false
     */
    public boolean addFriend(String userId) {
        friendIDs.add(userId);
        return true;
    }

    /**
     * Adds a group chat to the user's list of group chats.
     * @param groupChat the group chat.
     */
    public void addGroupChat(GroupChat groupChat){
        groupChats.add(groupChat);
    }

    /**
     * Removes a group chat from the user's list of group chats.
     *
     * @param channelUrl the URL of the group chat.
     */
    @Override
    public void removeGroupChat(String channelUrl) {
        groupChats.removeIf(groupChat -> groupChat.getChannelUrl().equals(channelUrl));
    }

    /**
     * fetchs a personal chat of the user.
     * @param channelURL The URL of the chat needed.
     * @return the chat with the URL provided, null if the chat does not exist
     */
    public Chat getChat(String channelURL) {
        //TODO: need to take care of the case when chat not in list
        for (GroupChat chat : groupChats) {
            if (chat.getChannelUrl().equals(channelURL)){
                return chat;
            }
        }
        return null;
    }

    /**
     * Blocks a user.
     * Also removes the user from the friends list if present.
     * @param userId The user to be blocked.
     * @return true if successful otherwise false
     */
    public boolean blockUser(String userId) {
        blockedIDs.add(userId);
        // Remove them from friends list if they are friends
        friendIDs.remove(userId);
        return true;
    }

    @Override
    public GroupChat getSelfChat() {
        return selfChat;
    }

    @Override
    public void setSelfChat(GroupChat selfChat) {
        this.selfChat = selfChat;
    }

    /**
     * Unblocks a user.
     * Gives the option to add the user to their friends list again.
     * @param userId The user to be unblocked.
     * @return true if successful otherwise false
     */
    public boolean unblockUser(String userId, boolean addAsFriend) {
        boolean unblocked = blockedIDs.remove(userId);
        if (unblocked && addAsFriend) {
            addFriend(userId);
        }
        return unblocked;
    }

    /**
     * Returns a list of blocked users.
     * @return List of blocked users.
     */
    @Override
    public List<String> getBlockedUserIDs() {
        return blockedIDs;
    }

    public boolean isBlocked(String userID) {
        return blockedIDs.contains(userID);
    }

    //VisibleForTesting
    public List<String> getFriendIDs() {return friendIDs;}

    //VisibleForTesting
    public List<GroupChat> getGroupChats() {return groupChats;}

    /**
     * Adds a personal chat to the user's list of personal chats.
     *
     * @param personalChat the group chat.
     */
    @Override
    public void addPersonalChat(GroupChat personalChat) {
        personalChats.add(personalChat);
    }

    //VisibleForTesting
    public List<GroupChat> getPersonalChats() {return personalChats;}

    /**
     * Sets a list of channel URLs corresponding to personal chats the user is in.
     *
     * @param personalChats
     */
    @Override
    public void setPersonalChats(List<GroupChat> personalChats) {
        this.personalChats = personalChats;
    }

    /**
     * Removes a user from this user's friend list.
     * @param userId The user to be removed.
     * @return true if successful otherwise false
     */
    public boolean removeFriend(String userId) {
        if (friendIDs.contains(userId)) {
            friendIDs.remove(userId);

            // removes personal chat between the user and the friend
            List<GroupChat> newPersonalChats = new ArrayList<>();
            for (GroupChat personalChat : personalChats) {
                if (!personalChat.hasMember(userId)) {
                    newPersonalChats.add(personalChat);
                }
            }
            setPersonalChats(newPersonalChats);
            return true;
        }
        return false;
    }

    /**
     * Sets the username of a user.
     *
     * @param username The new name.
     */
    @Override
    public void setName(String username) {
        this.name = username;
    }


    // Todo: These methods will likely be replaced by use cases in the future, and for now use an older implementation. The logic should be updated while moving these to their own use cases:

//    /**
//     * Adds a bio to the user.
//     * @param bio The bio to be added.
//     * @return true if successful otherwise false
//     */
//    public boolean EditBiography(String bio) {
//        this.biography = bio;
//        return true;
//    }
//
//    /**
//     * Adds a DOB to the chat.
//     * @param DOB The DOB to be added.
//     * @return true if successful otherwise false
//     */
//    public boolean EditDOB(Integer DOB) {
//        //TODO: need to verify that the provided DOB is in the correct format
//        if (Integer.toString(DOB).length() == 8) {
//            this.dateOfBirth = DOB;
//            return true;
//        }
//        return false;
//    }
//
//    /**
//     * Adds a friend to the user.
//     * @param user The friend to be added.
//     * @return true if successful otherwise false
//     */
//    public boolean AddFriend(User user) {
//        friendIDs.add(user);
//        return CreateChat(user);
//    }
//
//    /**
//     * Creates a personal chat between two people.
//     * The person with whom the personal chat is created must be a friend
//     * @param user The friend with whom we have to create a chat.
//     * @return true if successful otherwise false
//     */
//    public boolean CreateGroupChat(User user) {
//        if (!friendIDs.contains(user)) {
//            return false;
//        }
//        else{
//            List<User> members = Arrays.asList(this, user);
//            GroupChat chat = new GroupChat(members);
//            groupChats.add(chat);
//            return true;
//        }
//
//    }
//
//    /**
//     * removes a friend from the user.
//     * also removes the personal chats with the friend
//     * @param user The friend to be removed.
//     * @return true if successful otherwise false
//     */
//    public boolean RemoveFriend(User user) {
//        if (friends.contains(user)) {
//            friends.remove(user);
//            for (GroupChat chat : groupChats) {
//                if (chat.HasMember(user)){
//                    groupChats.remove(chat);
//                }
//            }
//        }
//        return true;
//    }
//
//    /**
//     * Sends a message in a chat.
//     * @param message the message that has to be sent.
//     * @param chat The chat in which the message has to be sent.
//     * @return true if successful otherwise false
//     */
//    public boolean SendMessage(Chat chat, Message message) {
//        return chat.AddMessage(message);
//    }
//
//    /**
//     * replaces an old message in a chat with a new message.
//     * @param oldMessage the message that has been sent.
//     * @param newMessage the new message that is to replace the old message.
//     * @param chat The chat in which the message has to be replaced.
//     * @return true if successful otherwise false
//     */
//    public boolean EditMessage(GroupChat chat, Message oldMessage, Message newMessage) {
//        return chat.EditMessage(oldMessage,newMessage);
//    }
//
//    /**
//     * removes a message in a chat.
//     * @param message the message that has to be removed.
//     * @param chat The chat in which the message has to be removed.
//     * @return true if successful otherwise false
//     */
//    public boolean RemoveMessage(GroupChat chat, Message message) {
//        return chat.DeleteMessage(message);

}
