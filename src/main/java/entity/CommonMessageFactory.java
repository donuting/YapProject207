package entity;

public class CommonMessageFactory implements MessageFactory {

    @Override
    public Message create(String senderId, String text, Integer messageId) {
        return new CommonMessage(senderId, text, messageId);
    }

    @Override
    public Message create(String senderId, String text) {
        return new CommonMessage(senderId, text);
    }
}
