package data_access;

import entity.GroupChat;
import entity.GroupChatFactory;
import org.openapitools.client.model.GcCreateChannelData;
import org.openapitools.client.model.SendBirdGroupChannel;
import org.sendbird.client.ApiClient;
import org.sendbird.client.ApiException;
import org.sendbird.client.Configuration;
import org.sendbird.client.api.GroupChannelApi;
import use_case.create_chat.CreateChatUserDataAccessInterface;

import java.util.List;

/**
 * The DAO for group chat data.
 */
public class GroupChatDataAccessObject implements CreateChatUserDataAccessInterface {

    static String API_TOKEN = ""; // Todo: get a api token for SendBird
    static String APPLICATION_ID = "https://api-APP_ID.sendbird.com"; // Todo: figure out the application ID

    /**
     * Creates a SendBirdGroupChannel, adds the users using their ID, creates a GroupChat and adds the channel as an attribute.
     * @return generated ID.
     */
    @Override
    public GroupChat create(List<String> memberIDs, String chatName, GroupChatFactory groupChatFactory) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath(APPLICATION_ID);

        GroupChannelApi apiInstance = new GroupChannelApi(defaultClient);

        // specify channel data
        GcCreateChannelData gcCreateChannelData = new GcCreateChannelData()
                .userIds(memberIDs)
                .name(chatName);

        try {
            SendBirdGroupChannel result = apiInstance.gcCreateChannel()
                    .apiToken(API_TOKEN)
                    .gcCreateChannelData(gcCreateChannelData)
                    .execute();

            GroupChat newChat = groupChatFactory.create(memberIDs, chatName);
            newChat.setChannelURL(result.getChannelUrl());
            return newChat;

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

    // Todo: implement
    public GroupChat get(String channelURL) {
        return null;
    }
}