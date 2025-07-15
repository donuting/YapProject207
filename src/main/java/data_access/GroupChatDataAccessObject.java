package data_access;

import entity.GroupChat;
import entity.User;
import org.openapitools.client.model.CreateUserData;
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

    @Override
    public void create(GroupChat newChat) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath("https://api-APP_ID.sendbird.com");

        GroupChannelApi apiInstance = new GroupChannelApi(defaultClient);
        List<User> users = newChat.getMembers();
        List<SendBirdUser> sendBirdUsers = new ArrayList<>();
        for (User user : users) {
            UserApi userApiInstance = new UserApi(defaultClient);

            CreateUserData createUserData = new CreateUserData()
                    .userId(user.getID().toString())
                    .nickname(user.getName());
            // specify user data

            try {
                SendBirdUser result = userApiInstance.createUser()
                        .apiToken(API_TOKEN)
                        .createUserData(createUserData)
                        .execute();
                sendBirdUsers.add(result);
            } catch (ApiException e) {
                System.err.println("Exception when calling UserApi#createUser");
                System.err.println("Status code: " + e.getCode());
                System.err.println("Reason: " + e.getResponseBody());
                System.err.println("Response headers: " + e.getResponseHeaders());
                e.printStackTrace();
            }
        }
        GcCreateChannelData gcCreateChannelData = new GcCreateChannelData()
                .users(sendBirdUsers)
                .name(newChat.getChatName());
        // specify channel data

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