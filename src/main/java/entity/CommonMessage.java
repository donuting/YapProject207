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
    public User GetSender(){
        return this.sender;
    }

    @Override
    public String GetText(){
        return this.text;
    }

    /**
     * generates an ID for the Message.
     * @return generated ID.
     */
    private Integer GenerateID() {
        java.util.Random rng = new java.util.Random();
        return rng.nextInt(1000000);
    }



}
