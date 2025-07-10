package entity;

public class CommonChat implements Chat{
    private final Integer user1id;
    private final Integer user2id;

    public CommonChat(Integer user1id, Integer user2id) {
        this.user1id = user1id;
        this.user2id = user2id;
    }

    @Override
    public Integer getuser1id() {
        return user1id;
    }

    @Override
    public Integer getuser2id() {
        return user2id;
    }

}
