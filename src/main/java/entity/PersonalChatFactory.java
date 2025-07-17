package entity;

import java.util.List;

public class PersonalChatFactory implements ChatFactory {

    /**
     * Creates a new Chat.
     *
     * @param memberIDs the list of the IDs of the members of the new chat
     * @param chatName
     * @return the new user
     */
    @Override
    public Chat create(List<String> memberIDs, String chatName) {
        return null;
    }
}
