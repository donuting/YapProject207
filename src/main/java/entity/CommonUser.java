package entity;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple implementation of the User interface.
 */
public class CommonUser implements User {

    private final String name;
    private String password;
    private final Integer ID;
    private String biography;
    private Integer DateOfBirth;
    private List<User> friends;
    private List<User> blocked;
    private List<GroupChat> personalChats;


    public CommonUser(String name, String password) {
        this.name = name;
        this.password = password;
        this.ID = GenerateID();
        this.biography = null;
        this.DateOfBirth = null;
        this.friends = new ArrayList<User>();
        this.blocked = new ArrayList<User>();
        this.personalChats = new ArrayList<GroupChat>();
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
        //TODO: need to test conditions on password (is it long enough? so on)
        this.password = password;
        return "success";
    }

    /**
     * generates an ID for the User.
     * @return generated ID.
     */
    private Integer GenerateID(){
        //TODO: need to add randomiser and makes ure the ID is unique
        return 100;
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
            GroupChat chat = new GroupChat(members);
            personalChats.add(chat);
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
            for (GroupChat chat : personalChats) {
                if (chat.HasMember(user)){
                    personalChats.remove(chat);
                }
            }
        }
        return true;
    }

    /**
     * fetchs a personal chat of the user.
     * @param CID The CID of the chat needed.
     * @return the chat with the CID provided, null if the chat does not exist
     */
    public Chat getChat(Integer CID) {
        //TODO: need to take care of the case when chat not in list
        for (GroupChat chat : personalChats) {
            if (chat.getCID()==CID){
                return chat;
            }
        }
        return null;
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
