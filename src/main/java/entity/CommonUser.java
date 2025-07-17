package entity;


import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple implementation of the User interface.
 */
public class CommonUser implements User {

    private final String name;
    private String password;
    private final String ID;
    private String biography;
    private String dateOfBirth;
    private List<String> friendIDs;
    private List<String> blockedIDs;
    private List<GroupChat> groupChats;
    private List<PersonalChat> personalChats;

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
                      List<PersonalChat> personalChats) {
        this.name = name;
        this.password = password;
        this.ID = ID;
        this.biography = biography;
        this.dateOfBirth = dateOfBirth;
        this.friendIDs = friendIDs;
        this.blockedIDs = blockedIDs;
        this.groupChats = groupChats;
        this.personalChats = personalChats;
    }

    // Necessary methods

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public String setPassword(String password) {
        //TODO: need to test conditions on password (is it long enough? so on)
        this.password = password;
        return "success";
    }

    @Override
    public String getID() {
        return ID;
    }

    /**
     * Returns a JSONObject containing user data. This object can only hold 5 items, and each item is a string of length at most 190.
     * @return the user data of the user.
     */
    @Override
    public JSONObject getMetadata() {
        JSONObject metadata = new JSONObject();
        metadata.put("password", password);
        metadata.put("biography", biography);
        metadata.put("dateOfBirth", dateOfBirth);
        String blockedStr = blockedIDs.toString().replace("[", "").replace("]", "");
        metadata.put("blockedIDs", blockedStr.toString());
        String friendsStr = friendIDs.toString().replace("[", "").replace("]", "");
        metadata.put("friendIDs", friendsStr);
        return metadata;
    }

    /**
     * generates an ID for the User.
     * @return generated ID.
     */
    private String GenerateID(){
        //TODO: need to add randomiser and makes ure the ID is unique
        return name;
    }

    /**
     * Adds a group chat to the user's list of group chats.
     * @param groupChat the group chat.
     */
    public void addGroupChat(GroupChat groupChat){
        groupChats.add(groupChat);
    }

    /**
     * fetchs a personal chat of the user.
     * @param CID The CID of the chat needed.
     * @return the chat with the CID provided, null if the chat does not exist
     */
    public Chat getChat(Integer CID) {
        //TODO: need to take care of the case when chat not in list
        for (GroupChat chat : groupChats) {
            if (chat.getCID()==CID){
                return chat;
            }
        }
        return null;
    }

    // Todo: These methods will likely be replaced by use cases in the future, and for now use an older implementation. The logic should be updated while moving these to their own use cases:

    /**
     * Adds a bio to the user.
     * @param bio The bio to be added.
     * @return true if successful otherwise false
     */
    public boolean EditBiography(String bio) {
        this.biography = bio;
        return true;
    }

    /**
     * Adds a DOB to the chat.
     * @param DOB The DOB to be added.
     * @return true if successful otherwise false
     */
    public boolean EditDOB(Integer DOB) {
        //TODO: need to verify that the provided DOB is in the correct format
        if (Integer.toString(DOB).length() == 8) {
            this.dateOfBirth = DOB;
            return true;
        }
        return false;
    }

    /**
     * Adds a friend to the user.
     * @param user The friend to be added.
     * @return true if successful otherwise false
     */
    public boolean AddFriend(User user) {
        friendIDs.add(user);
        return CreateChat(user);
    }

    /**
     * Creates a personal chat between two people.
     * The person with whom the personal chat is created must be a friend
     * @param user The friend with whom we have to create a chat.
     * @return true if successful otherwise false
     */
    public boolean CreateGroupChat(User user) {
        if (!friendIDs.contains(user)) {
            return false;
        }
        else{
            List<User> members = Arrays.asList(this, user);
            GroupChat chat = new GroupChat(members);
            groupChats.add(chat);
            return true;
        }

    }

    /**
     * removes a friend from the user.
     * also removes the personal chats with the friend
     * @param user The friend to be removed.
     * @return true if successful otherwise false
     */
    public boolean RemoveFriend(User user) {
        if (friends.contains(user)) {
            friends.remove(user);
            for (GroupChat chat : groupChats) {
                if (chat.HasMember(user)){
                    groupChats.remove(chat);
                }
            }
        }
        return true;
    }

    /**
     * Sends a message in a chat.
     * @param message the message that has to be sent.
     * @param chat The chat in which the message has to be sent.
     * @return true if successful otherwise false
     */
    public boolean SendMessage(Chat chat, Message message) {
        return chat.AddMessage(message);
    }

    /**
     * replaces an old message in a chat with a new message.
     * @param oldMessage the message that has been sent.
     * @param newMessage the new message that is to replace the old message.
     * @param chat The chat in which the message has to be replaced.
     * @return true if successful otherwise false
     */
    public boolean EditMessage(GroupChat chat, Message oldMessage, Message newMessage) {
        return chat.EditMessage(oldMessage,newMessage);
    }

    /**
     * removes a message in a chat.
     * @param message the message that has to be removed.
     * @param chat The chat in which the message has to be removed.
     * @return true if successful otherwise false
     */
    public boolean RemoveMessage(GroupChat chat, Message message) {
        return chat.DeleteMessage(message);
    }
}
