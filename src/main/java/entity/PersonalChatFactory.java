package entity;

import java.util.List;

public class PersonalChatFactory implements ChatFactory {

    @Override
    public Chat create(String chatName, List<User> members){return new PersonalChat(members);}
}
