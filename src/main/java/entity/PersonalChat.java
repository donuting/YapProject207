package entity;

import java.util.ArrayList;
import java.util.List;

public class PersonalChat implements Chat {
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

    @Override
    public boolean AddMember(User user){
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
    public boolean HasMember(User user){
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
}
