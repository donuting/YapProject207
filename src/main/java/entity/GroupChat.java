package entity;

import java.util.ArrayList;
import java.util.List;

public class GroupChat implements Chat {
    private String chatName;
    private List<String> memberIds;
    private List<Message> messageHistory;
    private String channelUrl;

    public GroupChat(List<String> memberIds, String chatName, List<Message> messageHistory) {
        this.memberIds = memberIds;
        this.chatName = chatName;
        this.messageHistory = messageHistory;
        this.channelUrl = "";
    }

    public GroupChat(List<String> memberIds, String chatName, List<Message> messageHistory, String channelUrl) {
        this.memberIds = memberIds;
        this.chatName = chatName;
        this.messageHistory = messageHistory;
        this.channelUrl = channelUrl;
    }

    // Todo: move this logic into an interactor so that we can call a DAO. Also avoid creating user objects if we don't need to.
    @Override
    public boolean addMember(String userID) {
        for (String memberID : memberIds) {
            User member = getUserByID(memberID);
            if (member != null) {
                List<String> blockedIds = member.getBlockedUserIDs();
                if (blockedIds != null && blockedIds.contains(userID)) {
                    return false;
                }
            }
        }
        if (!memberIds.contains(userID)) {
            memberIds.add(userID);
            return true;
        }
        return false;
    }

    // Todo: rewrite this method
    @Override
    public boolean addMessage(Message message) {
        if (message == null) {
            return false;
        }
        String senderID = message.GetSenderId();
        if (senderID == null) {
            return false;
        }

        for (String memberID : memberIds) {
            if (memberID.equals(senderID)) {
                continue;
            }
            // Todo: getUserByID doesn't work!
            User member = getUserByID(memberID);
            if (member != null) {
                List<String> blockedIds = member.getBlockedUserIDs();
                if (blockedIds != null && blockedIds.contains(senderID)) {
                    System.out.println("Sender is blocked.");
                    return false;
                }
            }
        }
        messageHistory.add(message);
        return true;
    }

    @Override
    public boolean deleteMessage(String messageId) {
        List<Message> newMessageHistory = new ArrayList<Message>();
        for (Message message : messageHistory) {
            if (!message.GetMID().toString().equals(messageId)) {
                newMessageHistory.add(message);
            }
        }
        messageHistory = newMessageHistory;
        return true;
    }

    @Override
    public boolean hasMember(String userID) {
        return memberIds.contains(userID);
    }

    /**
     * Removes a user from the chat.
     * @param user The user to be removed.
     * @return true if successful otherwise false
     */
    public boolean removeMember(User user) {
        if (memberIds.contains(user.getID())){
            memberIds.remove(user.getID());
            return true;
        }
        return false;
    }

    /**
     * Sets a name to the chat.
     *
     * @param name The new name of the chat.
     * @return true if successful otherwise false
     */
    public boolean setChatName(String name) {
        this.chatName = name;
        return true;
    }

    // TODO: implement method (Note from Herman, it is impossible to implement this without a DAO)
    private User getUserByID(String userId) {
        return null;
    }

    @Override
    public String getChannelUrl() {
        return channelUrl;
    }

    @Override
    public void setMessageHistory(List<Message> messageHistory) {
        this.messageHistory = messageHistory;
    }

    @Override
    public List<Message> getMessageHistory() {
        return messageHistory;
    }

    @Override
    public void setChannelUrl(String channelUrl) {
        this.channelUrl = channelUrl;
    }

    @Override
    public List<String> getMemberIds() {
        return memberIds;
    }

    /**
     * Sets the member IDs of the chat's members.
     *
     * @param memberIds the member IDs.
     */
    @Override
    public void setMemberIds(List<String> memberIds) {
        this.memberIds = memberIds;
    }

    public String getChatName() {
        return chatName;
    }
}
