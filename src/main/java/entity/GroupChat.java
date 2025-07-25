package entity;

import org.openapitools.client.model.SendBirdGroupChannel;

import java.util.ArrayList;
import java.util.List;

public class GroupChat implements Chat {
    private List<String> memberIDs;
    public String chatName;
    private List<Message> messageHistory;
    private String channelURL = null; // placeholder

    public GroupChat(List<String> memberIDs, String chatName) {
        this.memberIDs = memberIDs;
        this.chatName = chatName; // old implementation: members.get(0).getName()+"-"+members.get(1).getName();
        this.messageHistory = new ArrayList<Message>();
    }

    @Override
    public boolean AddMember(String userID){
        //TODO: create execution
        return false;
    }

    @Override
    public  boolean AddMessage(Message message){
        //TODO: create execution
        return false;
    }

    @Override
    public boolean DeleteMessage(Message message){
        //TODO: create execution
        return false;
    }

    @Override
    public boolean HasMember(String userID){
        //TODO: create execution
        return false;
    }

    /**
     * Removes a user from the chat.
     * @param user The user to be removed.
     * @return true if successful otherwise false
     */
    public boolean removeMember(User user){
        //TODO: create execution
        return false;
    }

    /**
     * sets a name to the chat.
     * @param name The new name of the chat.
     * @return true if successful otherwise false
     */
    public boolean setChatName(String name){
        //TODO: create execution
        return false;
    }

    /**
     * generates an ID for the Chat.
     * @return generated ID.
     */
    private Integer GenerateCID(){
        //TODO: create execution
        return -1;
    }

    public Integer getCID(){
        //TODO: create execution
        return -1;
    }


    public boolean EditMessage(Message oldMessage, Message newMessage) {
        //TODO: create execution
        return false;
    }

    @Override
    public String getChannelURL() {
        return channelURL;
    }

    @Override
    public void setChannelURL(String channelURL) {
        this.channelURL = channelURL;
    }

    @Override
    public List<String> getMemberIDs() {
        return memberIDs;
    }

    public String getChatName() {
        return chatName;
    }
}
