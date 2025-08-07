package entity;

public class CommonMessage implements Message {

    private Integer MID;
    private final String senderId;
    boolean messageSeen;
    String text;
    String timestamp;

    public CommonMessage(String senderId, String text, Integer MID, String timestamp) {
        this.MID = MID;
        this.senderId = senderId;
        this.text = text;
        this.messageSeen = false;
        this.timestamp = timestamp;
    }

    public CommonMessage(String senderId, String text) {
        this.MID = GenerateID();
        this.senderId = senderId;
        this.text = text;
        this.messageSeen = false;
        this.timestamp = "0000"; // default
    }

    @Override
    public String GetSenderId(){
        return this.senderId;
    }

    @Override
    public String GetText(){
        return this.text;
    }

    @Override
    public void SetMID(Integer MID) {
        this.MID = MID;
    }

    public Integer GetMID() {
        return MID;
    }

    /**
     * returns the timestamp of the message.
     *
     * @return timestamp of the message
     */
    @Override
    public String getTimestamp() {
        return timestamp;
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
