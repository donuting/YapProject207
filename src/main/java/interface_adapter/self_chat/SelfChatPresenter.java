package interface_adapter.self_chat;

import com.google.gson.JsonObject;
import entity.Message;
import use_case.self_chat.SelfChatOutputBoundary;
import use_case.self_chat.SelfChatOutputData;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * The Presenter for the Self Chat Use Case.
 */
public class SelfChatPresenter implements SelfChatOutputBoundary {

    private final SelfChatViewModel selfChatViewModel;

    public SelfChatPresenter(SelfChatViewModel selfChatViewModel) {
        this.selfChatViewModel = selfChatViewModel;
    }

    @Override
    public void presentMessage(SelfChatOutputData outputData) {
        if (outputData.isSuccess()) {
            // Messages were successfully added
            selfChatViewModel.setUsername(outputData.getUsername());

            Map<Integer, JsonObject> messageData = new HashMap<>();
            for (Message message : outputData.getMessages()) {

                // convert timestamp to LocalTimeDate
                Instant instant = Instant.ofEpochMilli(message.getTimestamp());
                ZoneId zoneId = ZoneId.systemDefault();
                LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zoneId);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm");
                String formattedDate = localDateTime.format(formatter);

                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("timestamp", formattedDate);
                jsonObject.addProperty("message_body", message.GetText());
                jsonObject.addProperty("message_ID", message.GetMID());

                messageData.put(message.GetMID(), jsonObject);
            }
            selfChatViewModel.addMessages(messageData);
        } else {
            // There was an error
            selfChatViewModel.setErrorMessage(outputData.getErrorMessage());
        }
    }

    @Override
    public void presentClearResult(boolean success, String errorMessage) {
        if (success) {
            selfChatViewModel.clearMessages();
        } else {
            selfChatViewModel.setErrorMessage(errorMessage);
        }
    }

    @Override
    public void presentError(String errorMessage) {
        selfChatViewModel.setErrorMessage(errorMessage);
    }
}