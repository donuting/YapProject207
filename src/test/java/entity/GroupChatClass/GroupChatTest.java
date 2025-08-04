package entity.GroupChatClass;

import data_access.GroupChatDataAccessObject;
import entity.CommonUser;
import entity.GroupChat;
import entity.GroupChatFactory;
import entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GroupChatTest {

    private CommonUser user;
    private GroupChat group;

    @BeforeEach
    public void setUp() {
        user = new CommonUser("User", "Password1", "100",
                "Bio", "20250823", new ArrayList<String>(),
                new ArrayList<String>(), new ArrayList<GroupChat>(),
                new ArrayList<GroupChat>());
        List<String> memberIDs = new ArrayList<>();
        memberIDs.add("100");
        String chatName = "Test Chat";
        group = new GroupChat(memberIDs, chatName, new ArrayList<>(), "chat.com");
    }

    @Test
    void getChannelURLTest(){
        assertEquals("chat.com", group.getChannelURL());
    }

    @Test
    void getMessageHistoryTest(){
        assert group.getMessageHistory().isEmpty();
    }

    @Test
    void getMembersTest(){
        List<String> memberIDs = group.getMemberIDs();
        assert memberIDs.size() == 1;
        assert memberIDs.contains("100");
    }

    @Test
    void getChatNameTest(){
        assert "Test Chat".equals(group.getChatName());
    }

    void AddMemberTest(){

    }



    @AfterEach
    public void tearDown() {
        group = null;
    }
}
