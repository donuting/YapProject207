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
                try {
                    // Fix the timestamp parsing issue - the original had Long.getLong which was incorrect
                    long timestampLong = Long.parseLong(message.getTimestamp());
                    Instant instant = Instant.ofEpochMilli(timestampLong);
                    ZoneId zoneId = ZoneId.systemDefault();
                    LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zoneId);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm");
                    String formattedDate = localDateTime.format(formatter);

                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("timestamp", formattedDate);
                    jsonObject.addProperty("message", message.GetText()); // Changed from "message_body" to "message"
                    jsonObject.addProperty("message_ID", message.GetMID().toString()); // Ensure it's a string

                    // Use the message ID as integer key
                    messageData.put(Integer.parseInt(message.GetMID().toString()), jsonObject);
                } catch (NumberFormatException e) {
                    // Handle timestamp parsing error - use current time as fallback
                    LocalDateTime now = LocalDateTime.now();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm");
                    String formattedDate = now.format(formatter);

                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("timestamp", formattedDate);
                    jsonObject.addProperty("message", message.GetText());
                    jsonObject.addProperty("message_ID", message.GetMID().toString());

                    // Use hash code as fallback for ID
                    messageData.put(message.GetMID().toString().hashCode(), jsonObject);
                }
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

    /**
     * Presents the result of saving birthday data.
     */
    public void presentBirthdaySaveResult(boolean success, String message) {
        if (success) {
            // Create a system message to show in chat
            JsonObject jsonObject = new JsonObject();
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm");
            String formattedDate = now.format(formatter);

            jsonObject.addProperty("timestamp", formattedDate);
            jsonObject.addProperty("message", "✓ " + message);
            jsonObject.addProperty("message_ID", "birthday_" + System.currentTimeMillis());

            Map<Integer, JsonObject> messageData = new HashMap<>();
            messageData.put((int)System.currentTimeMillis(), jsonObject);
            selfChatViewModel.addMessages(messageData);
        } else {
            selfChatViewModel.setErrorMessage(message);
        }
    }
}