package entity;

public class CommonMessageFactory implements MessageFactory {

    @Override
    public Message create(String senderId, String text, Integer messageId, long timestamp) {
        return new CommonMessage(senderId, text, messageId, timestamp);
    }

    @Override
    public Message create(String senderId, String text) {
        return new CommonMessage(senderId, text);
    }
}
