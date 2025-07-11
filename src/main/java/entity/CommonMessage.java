package entity;

public class CommonMessage implements Message {

    private final Integer MID;
    private final User sender;
    boolean messageSeen;
    String text;

    public CommonMessage(User sender, String text) {
        this.MID = GenerateID();
        this.sender = sender;
        this.text = text;
        this.messageSeen = false;
    }

    @Override
    public  User GetSender(){
        //TODO: create execution
        return null;
    }

    @Override
    public String GetText(){
        //TODO: create execution
        return null;
    }

    /**
     * generates an ID for the Message.
     * @return generated ID.
     */
    private Integer GenerateID() {
        //TODO: create execution
        return -1;
    }



}
