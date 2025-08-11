package data_access;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openapitools.client.model.*;
import org.sendbird.client.ApiClient;
import org.sendbird.client.ApiException;
import org.sendbird.client.Configuration;
import org.sendbird.client.api.GroupChannelApi;

import entity.GroupChat;
import entity.GroupChatFactory;

/**
 * The DAO for group chat data.
 */
public class GroupChatDataAccessObject {

    private static final String API_TOKEN = "7836d8100957f700df15d54313b455766090ea9f";
    private static final String APPLICATION_ID = "https://api-17448E6A-5733-470D-BCE0-7A4460C94A11.sendbird.com";
    private final MessageDataAccessObject messageDataAccessObject = new MessageDataAccessObject();
    private GroupChat activeGroupChat = null;

    /**
     * Creates a SendBirdGroupChannel, adds the users using their ID,
     * creates a GroupChat and adds the channel as an attribute.
     * @param memberIds the IDs of the members to be added
     * @param chatName the name of the new chat
     * @param groupChatFactory a Group Chat Factory
     * @return the GroupChat object.
     */
    public GroupChat create(List<String> memberIds, String chatName, GroupChatFactory groupChatFactory) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath(APPLICATION_ID);

        GroupChannelApi apiInstance = new GroupChannelApi(defaultClient);
        CreateAGroupChannelRequest createGroupChannelRequest = new CreateAGroupChannelRequest()
                .userIds(memberIds)
                .name(chatName)
                .isPublic(true);

        try {
            SendbirdGroupChannelDetail result = apiInstance.createAGroupChannel()
                    .apiToken(API_TOKEN)
                    .createAGroupChannelRequest(createGroupChannelRequest)
                    .execute();
            System.out.println(result);

            GroupChat groupChat = groupChatFactory.create(memberIds, chatName, new ArrayList<>());
            groupChat.setChannelUrl(result.getChannelUrl());
            return groupChat;

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
     * @param channelUrl  the URL of the channel
     * @return the GroupChat Object, or null if it doesn't exist.
     */
    public GroupChat load(String channelUrl) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath(APPLICATION_ID);

        GroupChannelApi apiInstance = new GroupChannelApi(defaultClient);
        boolean showMembers = true;

        try {
            GetAGroupChannelResponse result = apiInstance.getAGroupChannel(channelUrl)
                    .showMember(showMembers)
                    .apiToken(API_TOKEN)
                    .execute();
            System.out.println(result);

            List<SendbirdMember> members = result.getMembers();
            List<String> memberIds = new ArrayList<>();
            if (members != null) {
                for (SendbirdMember member : members) {
                    memberIds.add(member.getUserId());
                }
            }
            String chatName = result.getName();
            GroupChat groupChat = new GroupChatFactory().create(memberIds, chatName, new ArrayList<>());
            groupChat.setChannelUrl(channelUrl);
            messageDataAccessObject.loadMessages(groupChat);
            return groupChat;
        }
        catch (ApiException e) {
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
     * @param channelUrl the URL of the chat.
     * @param userId the ID of the user.
     */
    public GroupChat addUser(String userId, String channelUrl) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath(APPLICATION_ID);

        GroupChannelApi apiInstance = new GroupChannelApi(defaultClient);
        JoinAChannelRequest joinChannelRequest = new JoinAChannelRequest()
                .userId(userId);
        try {
            SendbirdGroupChannelDetail result = apiInstance.joinAChannel(channelUrl)
                    .apiToken(API_TOKEN)
                    .joinAChannelRequest(joinChannelRequest)
                    .execute();
            System.out.println(result);

            List<SendbirdMember> members = result.getMembers();
            List<String> memberIds = new ArrayList<>();
            if (members != null) {
                for (SendbirdMember member : members) {
                    memberIds.add(member.getUserId());
                }
            }
            String chatName = result.getName();
            GroupChat groupChat = new GroupChatFactory().create(memberIds, chatName, new ArrayList<>());
            groupChat.setChannelUrl(channelUrl);
            messageDataAccessObject.loadMessages(groupChat);
            return groupChat;
        }
        catch (ApiException e) {
            System.err.println("Exception when calling GroupChannelApi#joinAChannel");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
        return null;
    }

    public boolean leaveGroupChat(String channelUrl, String userId) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath(APPLICATION_ID);

        GroupChannelApi apiInstance = new GroupChannelApi(defaultClient);
        List<String> userIds = new ArrayList<>();
        userIds.add(userId);
        LeaveAChannelRequest leaveAChannelRequest = new LeaveAChannelRequest().userIds(userIds);
        try {
            Object result = apiInstance.leaveAChannel(channelUrl)
                    .apiToken(API_TOKEN)
                    .leaveAChannelRequest(leaveAChannelRequest)
                    .execute();
            System.out.println(result);
            return true;
        } catch (ApiException e) {
            System.err.println("Exception when calling GroupChannelApi#leaveAChannel");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Deletes a chat in SendBird.
     * @param channelUrl the URL of the chat.
     */
    public void delete(String channelUrl) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath(APPLICATION_ID);

        GroupChannelApi apiInstance = new GroupChannelApi(defaultClient);
        String apiToken = API_TOKEN;
        try {
            Object result = apiInstance.deleteAGroupChannel(channelUrl)
                    .apiToken(apiToken)
                    .execute();
            System.out.println(result);
        }
        catch (ApiException e) {
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
     * Creates a GroupChat object given a JSONObject.
     * Used when loading GroupChat objects when logging in.
     * @param groupChatJson the JSON object corresponding to a group chat in SendBird.
     * @return the GroupChat Object, or null if it doesn't exist.
     */
    public GroupChat getGroupChat(JSONObject groupChatJson) {
        JSONArray members = groupChatJson.getJSONArray("members");
        List<String> memberIds = new ArrayList<>();
        if (members != null) {
            for (Object member : members) {
                memberIds.add(((JSONObject) member).getString("user_id"));
            }
        }
        String chatName = groupChatJson.getString("name");
        String channelUrl = groupChatJson.getString("channel_url");
        GroupChat groupChat = new GroupChatFactory().create(memberIds, chatName, new ArrayList<>());
        groupChat.setChannelUrl(channelUrl);
        messageDataAccessObject.loadMessages(groupChat);
        return groupChat;
    }
}
