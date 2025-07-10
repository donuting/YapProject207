package entity;

public class CommonChatFactory implements ChatFactory{

    @Override
    public Chat create(Integer user1id, Integer user2id) {
        return new CommonChat(user1id, user2id);
    }
}
