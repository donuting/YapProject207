package entity;

import java.util.ArrayList;
import java.util.List;

public class PersonalChat implements Chat {
    private List<String> memberIDs;
    public String chatName;
    private List<Message> messageHistory;
    private String channelURL = null; // placeholder


    public PersonalChat(List<String> memberIDs, String chatName) {
        this.memberIDs = memberIDs;
        this.chatName = chatName; // chatName will be specified when the group chat is created - we will access user data from the database, then pass that into the data access object to create a chat.
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
    public boolean HasMember(String userID){
        return memberIDs.contains(userID);
    }

    /**
     * Removes a user from the chat.
     * @param userID The user to be removed.
     * @return true if successful otherwise false
     */
    public boolean removeMember(String userID){
        return memberIDs.remove(userID);
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

    public String getChannelURL() {
        return this.channelURL;
    }

    public boolean EditMessage(Message oldMessage, Message newMessage) {
        int idx = messageHistory.indexOf(oldMessage);
        if (idx != -1 && newMessage != null) {
            messageHistory.set(idx, newMessage);
            return true;
        }
        return false;
    }

    @Override
    public List<String> getMemberIDs(){
        return this.memberIDs;
    }

    @Override
    public void setChannelURL(String channelURL) {
        this.channelURL = channelURL;
    }

}
