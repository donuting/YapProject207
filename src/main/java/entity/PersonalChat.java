package entity;

import java.util.ArrayList;
import java.util.List;

public class PersonalChat implements Chat {
    private static final List<Integer> createdCIDs = new ArrayList<>();
    private final Integer CID;
    private List<User> members;
    public String chatName;
    private List<Message> messageHistory;


    public PersonalChat(List<User> members) {
        this.CID = GenerateCID();
        this.members = members;
        this.chatName = members.get(0).getName()+"-"+members.get(1).getName();
        this.messageHistory = new ArrayList<Message>();
    }

    /**
     * Adds a user to the chat.
     * Checks if any existing member has blocked the user before adding.
     * @param user The user to be added.
     * @return true if the user was added successfully, false if blocked or already a member.
     */
    @Override
    public boolean AddMember(User user){
        // Check if user is blocked
        for (User member : members) {
            if (member.getBlockedUsers().contains(user)) {
                System.out.println("Cannot add " + user.getName() + ": blocked by " + member.getName());
                return false;
            }
        }
        if (!members.contains(user)) {
            members.add(user);
            return true;
        }
        return false;
    }

    /**
     * Adds a message to the chat.
     * @param message The message to add.
     * @return true if message added, false if failed.
     */
    @Override
    public boolean AddMessage(Message message) {
        if (message == null) {
            return false;
        }
        User sender = message.GetSender();
        for (User member : members) {
            if (member.equals(sender)) {
                continue;
            }
            if (member.getBlockedUsers().contains(sender)) {
                System.out.println("Sender is blocked.");
                return false;
            }
        }
        messageHistory.add(message);
        return true;
    }



    @Override
    public boolean DeleteMessage(Message message){
        return messageHistory.remove(message);
    }

    @Override
    public boolean HasMember(User user){
        return members.contains(user);
    }

    /**
     * Removes a user from the chat.
     * @param user The user to be removed.
     * @return true if successful otherwise false
     */
    public boolean removeMember(User user){
        return members.remove(user);
    }

    /**
     * sets a name to the chat.
     * @param name The new name of the chat.
     * @return true if successful otherwise false
     */
    public boolean setChatName(String name){
        if (name != null && !name.isEmpty()) {
            chatName = name;
            return true;
        }
        return false;
    }

    /**
     * generates an ID for the Chat.
     * @return generated ID.
     */
    private Integer GenerateCID(){
        int cid = new java.util.Random().nextInt(90000) + 10000;
        // Check that the CID is unique
        while (createdCIDs.contains(cid)) {
            cid = new java.util.Random().nextInt(90000) + 10000;
        }
        createdCIDs.add(cid);
        return cid;
    }

    public Integer getCID(){
        return this.CID;
    }


    public boolean EditMessage(Message oldMessage, Message newMessage) {
        int idx = messageHistory.indexOf(oldMessage);
        if (idx != -1 && newMessage != null) {
            messageHistory.set(idx, newMessage);
            return true;
        }
        return false;
    }
}
