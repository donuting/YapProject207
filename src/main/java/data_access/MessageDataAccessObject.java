package data_access;

import entity.*;
import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.openapitools.client.model.ListMessagesResponse;
import org.openapitools.client.model.SendbirdMessageResponse;
import org.sendbird.client.ApiClient;
import org.sendbird.client.ApiException;
import org.sendbird.client.Configuration;
import org.sendbird.client.api.MessageApi;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class MessageDataAccessObject {

    private static final String API_TOKEN = "7836d8100957f700df15d54313b455766090ea9f";
    private static final String APPLICATION_ID = "https://api-17448E6A-5733-470D-BCE0-7A4460C94A11.sendbird.com";
    private final MessageFactory messageFactory = new CommonMessageFactory();

    /**
     * Loads preexisting messages in a channel in SendBird to the corresponding Chat object.
     *
     * @param groupChat the chat to load messages into
     */
    public void loadMessages(GroupChat groupChat) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath(APPLICATION_ID);

        MessageApi apiInstance = new MessageApi(defaultClient);
        String channelType = "group_channels";
        String channelUrl = groupChat.getChannelUrl();
        Long messageTs = System.currentTimeMillis();
        Integer prevLimit = 50; // Loads 50 most recent messages from oldest to newest.

        try {
            ListMessagesResponse result = apiInstance.listMessages(channelType, channelUrl)
                    .messageTs(messageTs)
                    .messageId(0L)
                    .prevLimit(prevLimit)
                    .apiToken(API_TOKEN)
                    .execute();
            System.out.println(result);

            List<SendbirdMessageResponse> sendBirdMessages = result.getMessages();
            List<Message> messageHistory = new ArrayList<>();
            if (sendBirdMessages != null) {
                for (SendbirdMessageResponse sendBirdMessage : sendBirdMessages) {

                    String userId = sendBirdMessage.getUser().getUserId();
                    String messageBody = sendBirdMessage.getMessage();
                    Integer messageId = sendBirdMessage.getMessageId();
                    long updatedAt = sendBirdMessage.getCreatedAt();
                    Date date = new Date(updatedAt);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
                    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));


                    Message message = messageFactory.create(userId, messageBody, messageId, sdf.format(date));
                    messageHistory.add(message);
                }
            }

            groupChat.setMessageHistory(messageHistory);

        } catch (ApiException e) {
            System.err.println("Exception when calling MessageApi#listMessages");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }

    /**
     * Updates the system to delete a message from a chat.
     *
     * @param MID  the ID of the message to be deleted.
     * @param chat the chat in which the message was sent.
     */
    public boolean deleteMessage(String MID, Chat chat) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        defaultClient.setBasePath(APPLICATION_ID);

        MessageApi apiInstance = new MessageApi(defaultClient);
        String channelType = "group_channels";
        try {
            Object result = apiInstance.deleteAMessage(channelType, chat.getChannelUrl(), MID)
                    .apiToken(API_TOKEN)
                    .execute();
            System.out.println(result);
            return true;
        } catch (ApiException e) {
            System.err.println("Exception when calling MessageApi#deleteAMessage");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Sends a message in a chat.
     *
     * @param message the message to be sent
     * @param chat the chat in which the message will be sent
     */
    public Message sendMessage(Message message, Chat chat) {

        // The below code is a different implementation of this method,  the
        // library we are using doesn't allow us to specify the userID sending
        // the message (ie it is bugged, this should not be the case),
        // so we are just using a different implementation (using OkHttp).

        final OkHttpClient client = new OkHttpClient().newBuilder()
                .build();

        final MediaType mediaType = MediaType.parse("Content-Type");

        final JSONObject requestBody = new JSONObject();
        requestBody.put("message_type", "MESG");
        requestBody.put("user_id", message.GetSenderId());
        requestBody.put("message", message.GetText());

        long createdAt = System.currentTimeMillis();
        requestBody.put("created_at", createdAt);
        requestBody.put("updated_at", createdAt);

        final RequestBody body = RequestBody.create(requestBody.toString(), mediaType);

        final Request request = new Request.Builder()
                .url(APPLICATION_ID + "/v3/group_channels/" + chat.getChannelUrl() + "/messages")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Api-Token", API_TOKEN)
                .build();
        try {
            final Response response = client.newCall(request).execute();

            final JSONObject responseBody = new JSONObject(response.body().string());

            if (response.code() == 200) {
                Integer messageId = (int) responseBody.getLong("message_id");
                message.SetMID(messageId);

                Date date = new Date(createdAt);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
                sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
                message.setTimestamp(sdf.format(date));

                return message;
            }
            else {
                System.out.println(response.code());
                System.out.println(response.body().string());
                throw new RuntimeException(responseBody.getString("message"));
            }
        } catch (IOException | JSONException ex) {
            return null;
        }
    }
}
