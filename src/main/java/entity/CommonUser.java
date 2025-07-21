package entity;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple implementation of the User interface.
 */
public class CommonUser implements User {

    private static final List<Integer> createdIDs = new ArrayList<>();
    private final String name;
    private String password;
    private final Integer ID;
    private String biography;
    private Integer DateOfBirth;
    private List<User> friends;
    private List<User> blocked;
    private List<PersonalChat> personalChats;


    public CommonUser(String name, String password) {
        this.name = name;
        this.password = password;
        this.ID = GenerateID();
        this.biography = null;
        this.DateOfBirth = null;
        this.friends = new ArrayList<User>();
        this.blocked = new ArrayList<User>();
        this.personalChats = new ArrayList<PersonalChat>();
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
    private Integer GenerateID(){
        int id = new java.util.Random().nextInt(90000) + 10000;
        while (createdIDs.contains(id)) {
            id = new java.util.Random().nextInt(90000) + 10000;
        }
        createdIDs.add(id);
        return id;
    }

    @Override
    public Integer getID()
    {
        return ID;
    }


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
            this.DateOfBirth = DOB;
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
        friends.add(user);
        return CreateChat(user);
    }

    /**
     * Creates a personal chat between two people.
     * The person with whom the personal chat is created must be a friend
     * @param user The friend with whom we have to create a chat.
     * @return true if successful otherwise false
     */
    public boolean CreateChat(User user) {
        if (!friends.contains(user)) {
            return false;
        }
        else{
            List<User> members = Arrays.asList(this, user);
            PersonalChat chat = new PersonalChat(members);
            personalChats.add(chat);
            return true;
        }

    }

    /**
     * Removes a friend from the user.
     * Also removes the personal chats with the friend
     * @param user The friend to be removed.
     * @return true if successful otherwise false
     */
    public boolean RemoveFriend(User user) {
        if (friends.contains(user)) {
            friends.remove(user);
            for (PersonalChat chat : personalChats) {
                if (chat.HasMember(user)){
                    personalChats.remove(chat);
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Blocks a user.
     * Also removes the user from the friends list if present.
     * @param user The user to be blocked.
     * @return true if successful otherwise false
     */
    public boolean blockUser(User user) {
        if (!blocked.contains(user) && friends.contains(user)) {
            blocked.add(user);
            // Remove them from friends list if they are friends
            friends.remove(user);
            return true;
        }
        return false;
    }

    /**
     * Unblocks a user.
     * Gives the option to add the user to their friends list again.
     * @param user The user to be unblocked.
     * @return true if successful otherwise false
     */
    public boolean unblockUser(User user, boolean addAsFriend) {
        boolean unblocked = blocked.remove(user);
        if (unblocked && addAsFriend) {
            AddFriend(user);
        }
        return unblocked;
    }

    /**
     * Returns a list of blocked users.
     * @return List of blocked users.
     */
    @Override
    public List<User> getBlockedUsers() {
        return blocked;
    }

    /**
     * fetchs a personal chat of the user.
     * @param CID The CID of the chat needed.
     * @return the chat with the CID provided, null if the chat does not exist
     */
    public Chat getChat(Integer CID) {
        //TODO: need to take care of the case when chat not in list
        for (PersonalChat chat : personalChats) {
            if (chat.getCID()==CID){
                return chat;
            }
        }
        return null;
    }

    /**
     * Sends a message in a chat.
     * Checks if the recipient has blocked the sender before sending the message.
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
    public boolean EditMessage(PersonalChat chat, Message oldMessage, Message newMessage) {
        return chat.EditMessage(oldMessage,newMessage);
    }

    /**
     * Removes a message in a chat.
     * @param message the message that has to be removed.
     * @param chat The chat in which the message has to be removed.
     * @return true if successful otherwise false
     */
    public boolean RemoveMessage(PersonalChat chat, Message message) {
        return chat.DeleteMessage(message);
    }


    

}
