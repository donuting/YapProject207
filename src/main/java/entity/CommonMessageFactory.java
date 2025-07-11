package entity;

public class CommonMessageFactory implements MessageFactory {

    @Override
    public Message create(User sender, String text){return new CommonMessage(sender,text);}
}
