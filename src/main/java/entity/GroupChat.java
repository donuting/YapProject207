package entity;

import java.util.List;

public class GroupChat implements Chat {
    private List<String> memberIDs;
    public String chatName;
    private List<Message> messageHistory;
    private String channelURL = null; // placeholder

    public GroupChat(List<String> memberIDs, String chatName, List<Message> messageHistory, String channelURL) {
        this.memberIDs = memberIDs;
        this.chatName = chatName;
        this.messageHistory = messageHistory;
        this.channelURL = channelURL;
    }

    // Todo: move this logic into an interactor so that we can call a DAO. Also avoid creating user objects if we don't need to.
    @Override
    public boolean AddMember(String userID) {
        for (String memberID : memberIDs) {
            User member = getUserByID(memberID);
            if (member != null) {
                List<String> blockedIDs = member.getBlockedUserIDs();
                if (blockedIDs != null && blockedIDs.contains(userID)) {
                    return false;
                }
            }
        }
        if (!memberIDs.contains(userID)) {
            memberIDs.add(userID);
            return true;
        }
        return false;
    }

    @Override
    public boolean AddMessage(Message message){
            if (message == null) {
                return false;
            }
        String senderID = message.GetSenderId();
        if (senderID == null) return false;

        for (String memberID : memberIDs) {
            if (memberID.equals(senderID)) continue;
            User member = getUserByID(memberID);
            if (member != null) {
                List<String> blockedIDs = member.getBlockedUserIDs();
                if (blockedIDs != null && blockedIDs.contains(senderID)) {
                    System.out.println("Sender is blocked.");
                    return false;
                }
            }
        }
        messageHistory.add(message);
        return true;
    }

    @Override
    public boolean DeleteMessage(Message message){
        //TODO: create execution
        return false;
    }

    @Override
    public boolean HasMember(String userID){
        return memberIDs.contains(userID);
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

    private User getUserByID(String userID) {
        return null;
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
    public void setMessageHistory(List<Message> messageHistory) {
        this.messageHistory = messageHistory;
    }

    @Override
    public List<Message> getMessageHistory() {
        return messageHistory;
    }

    /**
     * Returns the name of this chat.
     *
     * @return the chat name.
     */
    @Override
    public String getName() {
        return this.chatName;
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
