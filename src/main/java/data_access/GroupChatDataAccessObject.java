package data_access;

import entity.GroupChat;
import entity.User;
import org.openapitools.client.model.GcCreateChannelData;
import org.openapitools.client.model.SendBirdGroupChannel;
import org.openapitools.client.model.SendBirdUser;
import org.sendbird.client.ApiClient;
import org.sendbird.client.ApiException;
import org.sendbird.client.Configuration;
import org.sendbird.client.api.GroupChannelApi;
import org.sendbird.client.api.UserApi;
import use_case.create_chat.CreateChatUserDataAccessInterface;

import java.util.ArrayList;
import java.util.List;

public class GroupChatDataAccessObject implements CreateChatUserDataAccessInterface {

    static String API_TOKEN = ""; // Todo: get a api token for SendBird

    /**
     * Creates a SendBirdGroupChannel, adds the users using their ID, then adds the channel as an attribute in the GroupChat object.
     * @return generated ID.
     */
    @Override
    public void create(GroupChat newChat) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api-APP_ID.sendbird.com");

        GroupChannelApi apiInstance = new GroupChannelApi(defaultClient);
        List<String> memberIDs = newChat.getMemberIDs();

        // specify channel data
        GcCreateChannelData gcCreateChannelData = new GcCreateChannelData()
                .userIds(memberIDs)
                .name(newChat.getChatName());

        try {
            SendBirdGroupChannel result = apiInstance.gcCreateChannel()
                    .apiToken(API_TOKEN)
                    .gcCreateChannelData(gcCreateChannelData)
                    .execute();

            newChat.setChannel(result);

        } catch (ApiException e) {
            System.err.println("Exception when calling GroupChannelApi#gcAcceptInvitation");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }
}