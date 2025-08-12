package interface_adapter.self_chat;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonObject;
import entity.Message;
import use_case.self_chat.SelfChatOutputBoundary;
import use_case.self_chat.SelfChatOutputData;

/**
 * The Presenter for the Self Chat Use Case.
 */
public class SelfChatPresenter implements SelfChatOutputBoundary {

    private static final String FORMAT = "MMM dd, yyyy HH:mm";
    private static final String TIMESTAMP = "timestamp";
    private static final String MESSAGE = "message";
    private static final String MESSAGE_ID = "message_ID";
    private final SelfChatViewModel selfChatViewModel;

    public SelfChatPresenter(SelfChatViewModel selfChatViewModel) {
        this.selfChatViewModel = selfChatViewModel;
    }

    @Override
    public void presentMessage(SelfChatOutputData outputData) {
        if (outputData.isSuccess()) {
            // Messages were successfully added
            selfChatViewModel.setUsername(outputData.getUsername());

            final Map<Integer, JsonObject> messageData = new HashMap<>();
            for (Message message : outputData.getMessages()) {
                try {
                    // Fix the timestamp parsing issue - the original had Long.getLong which was incorrect
                    final long timestampLong = Long.parseLong(message.getTimestamp());
                    final Instant instant = Instant.ofEpochMilli(timestampLong);
                    final ZoneId zoneId = ZoneId.systemDefault();
                    final LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zoneId);
                    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT);
                    final String formattedDate = localDateTime.format(formatter);

                    final JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty(TIMESTAMP, formattedDate);
                    // Changed from "message_body" to "message"
                    jsonObject.addProperty(MESSAGE, message.GetText());
                    // Ensure it's a string
                    jsonObject.addProperty(MESSAGE_ID, message.GetMID().toString());

                    // Use the message ID as integer key
                    messageData.put(Integer.parseInt(message.GetMID().toString()), jsonObject);
                }
                catch (NumberFormatException evt) {
                    // Handle timestamp parsing error - use current time as fallback
                    final LocalDateTime now = LocalDateTime.now();
                    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT);
                    final String formattedDate = now.format(formatter);

                    final JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty(TIMESTAMP, formattedDate);
                    jsonObject.addProperty(MESSAGE, message.GetText());
                    jsonObject.addProperty(MESSAGE_ID, message.GetMID().toString());

                    // Use hash code as fallback for ID
                    messageData.put(message.GetMID().toString().hashCode(), jsonObject);
                }
            }
            selfChatViewModel.addMessages(messageData);
        }
        else {
            // There was an error
            selfChatViewModel.setErrorMessage(outputData.getErrorMessage());
        }
    }

    @Override
    public void presentClearResult(boolean success, String errorMessage) {
        if (success) {
            selfChatViewModel.clearMessages();
        }
        else {
            selfChatViewModel.setErrorMessage(errorMessage);
        }
    }

    @Override
    public void presentError(String errorMessage) {
        selfChatViewModel.setErrorMessage(errorMessage);
    }

    /**
     * Presents the result of saving birthday data.
     * @param success successful or not
     * @param message message
     */
    public void presentBirthdaySaveResult(boolean success, String message) {
        if (success) {
            // Create a system message to show in chat
            final JsonObject jsonObject = new JsonObject();
            final LocalDateTime now = LocalDateTime.now();
            final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT);
            final String formattedDate = now.format(formatter);

            jsonObject.addProperty(TIMESTAMP, formattedDate);
            jsonObject.addProperty(MESSAGE, "\u2713" + message);
            jsonObject.addProperty("message_ID", "birthday_" + System.currentTimeMillis());

            final Map<Integer, JsonObject> messageData = new HashMap<>();
            messageData.put((int) System.currentTimeMillis(), jsonObject);
            selfChatViewModel.addMessages(messageData);
        }
        else {
            selfChatViewModel.setErrorMessage(message);
        }
    }
}
