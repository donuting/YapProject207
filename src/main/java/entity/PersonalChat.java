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

    @Override
    public boolean AddMember(String userID){
        if (!memberIDs.contains(userID)) {
            memberIDs.add(userID);
            return true;
        }
        return false;
    }

    @Override
    public  boolean AddMessage(Message message){
        if (message != null) {
            messageHistory.add(message);
            return true;
        }
        return false;
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

    @Override
    public String getChannelURL() {
        return this.channelURL;
    }

    @Override
    public void setChannelURL(String channelURL) {
        this.channelURL = channelURL;
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



}
