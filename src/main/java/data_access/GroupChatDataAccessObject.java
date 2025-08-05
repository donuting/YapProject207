package data_access;

import entity.GroupChat;
import entity.GroupChatFactory;
import org.openapitools.client.model.*;
import org.sendbird.client.ApiClient;
import org.sendbird.client.ApiException;
import org.sendbird.client.Configuration;
import org.sendbird.client.api.GroupChannelApi;
import use_case.create_chat.CreateChatUserDataAccessInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * The DAO for group chat data.
 */
public class GroupChatDataAccessObject {

    private static final String API_TOKEN = "7836d8100957f700df15d54313b455766090ea9f";
    private static final String APPLICATION_ID = "https://api-17448E6A-5733-470D-BCE0-7A4460C94A11.sendbird.com";
    private final MessageDataAccessObject messageDataAccessObject = new MessageDataAccessObject();
    public GroupChat activeGroupChat = null;

    /**
     * Creates a SendBirdGroupChannel, adds the users using their ID, creates a GroupChat and adds the channel as an attribute.
     * @return the GroupChat object.
     */
    public GroupChat create(List<String> memberIDs, String chatName, GroupChatFactory groupChatFactory) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath(APPLICATION_ID);

        GroupChannelApi apiInstance = new GroupChannelApi(defaultClient);
        CreateAGroupChannelRequest createAGroupChannelRequest = new CreateAGroupChannelRequest()
                .userIds(memberIDs)
                .name(chatName)
                .isPublic(true);

        try {
            SendbirdGroupChannelDetail result = apiInstance.createAGroupChannel()
                    .apiToken(API_TOKEN)
                    .createAGroupChannelRequest(createAGroupChannelRequest)
                    .execute();
            System.out.println(result);

            return groupChatFactory.create(memberIDs, chatName, new ArrayList<>(), result.getChannelUrl());

        }
        catch (ApiException e) {
            System.err.println("Exception when calling GroupChannelApi#gcAcceptInvitation");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Loads an existing GroupChat object from a channel URL.
     * @return the GroupChat Object, or null if it doesn't exist.
     */
    public GroupChat load(String channelURL) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath(APPLICATION_ID);

        GroupChannelApi apiInstance = new GroupChannelApi(defaultClient);
        boolean showMembers = true;

        try {
            GetAGroupChannelResponse result = apiInstance.getAGroupChannel(channelURL)
                    .showMember(showMembers)
                    .apiToken(API_TOKEN)
                    .execute();
            System.out.println(result);

            List<SendbirdMember> members = result.getMembers();
            List<String> memberIDs = new ArrayList<>();
            if (members != null) {
                for (SendbirdMember member : members) {
                    memberIDs.add(member.getUserId());
                }
            }
            String chatName = result.getName();
            GroupChat groupChat = new GroupChatFactory().create(memberIDs, chatName, new ArrayList<>(), channelURL);
            messageDataAccessObject.loadMessages(groupChat);
            return groupChat;

        } catch (ApiException e) {
            System.err.println("Exception when calling GroupChannelApi#getAGroupChannel");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Adds a user to a group chat in SendBird.
     */
    public void addUser(String channelURL, String userID) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath(APPLICATION_ID);

        GroupChannelApi apiInstance = new GroupChannelApi(defaultClient);
        JoinAChannelRequest joinAChannelRequest = new JoinAChannelRequest()
                .userId(userID);
        try {
             SendbirdGroupChannelDetail result = apiInstance.joinAChannel(channelURL)
                    .apiToken(API_TOKEN)
                    .joinAChannelRequest(joinAChannelRequest)
                    .execute();
            System.out.println(result);

        } catch (ApiException e) {
            System.err.println("Exception when calling GroupChannelApi#joinAChannel");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }

    /**
     * Removes a user from a group chat in SendBird.
     */
    public void removeUser(String channelURL, String userID) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath(APPLICATION_ID);

        GroupChannelApi apiInstance = new GroupChannelApi(defaultClient);
        List<String> userIds = new ArrayList<>();
        userIds.add(userID);
        LeaveAChannelRequest leaveAChannelRequest = new LeaveAChannelRequest()
                .userIds(userIds);
        try {
            Object result = apiInstance.leaveAChannel(channelURL)
                    .apiToken(API_TOKEN)
                    .leaveAChannelRequest(leaveAChannelRequest)
                    .execute();
            System.out.println(result);

        } catch (ApiException e) {
            System.err.println("Exception when calling GroupChannelApi#leaveAChannel");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }

    /**
     * Deletes a chat in SendBird.
     */
    public void delete(String channelURL) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath(APPLICATION_ID);

        GroupChannelApi apiInstance = new GroupChannelApi(defaultClient);
        String apiToken = API_TOKEN; // String |
        try {
            Object result = apiInstance.deleteAGroupChannel(channelURL)
                    .apiToken(apiToken)
                    .execute();
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling GroupChannelApi#deleteAGroupChannel");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }

    /**
     * Returns the GroupChat object the user is viewing.
     * @return the GroupChat object the user is viewing, or null if no chat is being viewed.
     */
    public GroupChat getActiveGroupChat() {
        return activeGroupChat;
    }

    /**
     * Sets the GroupChat object corresponding to the chat the user is currently viewing.
     * @param activeGroupChat the active group chat, or null if none are being viewed.
     */
    public void setActiveGroupChat(GroupChat activeGroupChat) {
        this.activeGroupChat = activeGroupChat;
    }

    /**
     * Creates a GroupChat object given a SendBirdGroupChannel object. Used when loading GroupChat objects when logging in.
     * @return the GroupChat Object, or null if it doesn't exist.
     */
    public GroupChat getGroupChat(SendbirdGroupChannel sendBirdGroupChannel) {
        List<SendbirdMember> members = sendBirdGroupChannel.getMembers();
        List<String> memberIDs = new ArrayList<>();
        if (members != null) {
            for (SendbirdMember member : members) {
                memberIDs.add(member.getUserId());
            }
        }
        String chatName = sendBirdGroupChannel.getName();
        String channelURL = sendBirdGroupChannel.getChannelUrl();
        GroupChat groupChat = new GroupChatFactory().create(memberIDs, chatName, new ArrayList<>(), channelURL);
        messageDataAccessObject.loadMessages(groupChat);
        return groupChat;
    }
}