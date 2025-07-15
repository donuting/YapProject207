package data_access;

import entity.GroupChat;
import org.openapitools.client.model.GcCreateChannelData;
import org.openapitools.client.model.SendBirdGroupChannel;
import org.sendbird.client.ApiClient;
import org.sendbird.client.ApiException;
import org.sendbird.client.Configuration;
import org.sendbird.client.api.GroupChannelApi;
import use_case.create_chat.CreateChatUserDataAccessInterface;

public class GroupChatDataAccessObject implements CreateChatUserDataAccessInterface {

    static String API_TOKEN = ""; // Todo: get a api token for SendBird

    @Override
    public void create(GroupChat newChat) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api-APP_ID.sendbird.com");

        GroupChannelApi apiInstance = new GroupChannelApi(defaultClient);
        String apiToken = API_TOKEN;
        GcCreateChannelData gcCreateChannelData = new GcCreateChannelData();
        try {
            SendBirdGroupChannel result = apiInstance.gcCreateChannel()
                    .apiToken(apiToken)
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

