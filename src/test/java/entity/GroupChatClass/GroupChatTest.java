package entity.GroupChatClass;

import entity.*;
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

    @Test
    void AddMemberTest1(){
        CommonUser newUser = new CommonUser("User2", "Password1", "200",
                "Bio", "20250823", new ArrayList<String>(),
                new ArrayList<String>(), new ArrayList<GroupChat>(),
                new ArrayList<GroupChat>());
        boolean result = group.AddMember("200");
        assertTrue(result);
        List<String> memberIDs = group.getMemberIDs();
        assert memberIDs.contains("200");
        result = group.AddMember("200");
        assertFalse(result);
    }

    @Test
    void AddMemberTest2(){
        CommonUser newUser = new CommonUser("User2", "Password1", "200",
                "Bio", "20250823", new ArrayList<String>(),
                new ArrayList<String>(), new ArrayList<GroupChat>(),
                new ArrayList<GroupChat>());
        user.blockUser("200");
        boolean result = group.AddMember("200");
        assertFalse(result);
        List<String> memberIDs = group.getMemberIDs();
        assert !memberIDs.contains("200");
    }

    @Test
    void AddMessageTest1(){
        CommonMessage message = new CommonMessage("100", "Test Message", 10, "0000");
        boolean result = group.AddMessage(message);
        assertTrue(result);
        List<Message> messages = group.getMessageHistory();
        assert  messages.contains(message);
    }

    @Test
    void AddMessageTest2(){
       CommonMessage message = new CommonMessage("200", "Test Message", 10, "0000");
       boolean result = group.AddMessage(message);
       assertFalse(result);
    }

    @Test
    void AddMessageTest3(){
        CommonUser newUser = new CommonUser("User2", "Password1", "200",
                "Bio", "20250823", new ArrayList<String>(),
                new ArrayList<String>(), new ArrayList<GroupChat>(),
                new ArrayList<GroupChat>());
        group.AddMember("200");
        user.blockUser("200");
        CommonMessage message = new CommonMessage("100", "Test Message", 10, "0000");
        boolean result = group.AddMessage(message);
        assertFalse(result);
    }

    @Test
    void AddMessageTest4(){
        CommonUser newUser = new CommonUser("User2", "Password1", "200",
                "Bio", "20250823", new ArrayList<String>(),
                new ArrayList<String>(), new ArrayList<GroupChat>(),
                new ArrayList<GroupChat>());
        group.AddMember("200");
        newUser.blockUser("100");
        CommonMessage message = new CommonMessage("100", "Test Message", 10, "0000");
        boolean result = group.AddMessage(message);
        assertFalse(result);
    }

    @Test
    void DeleteMessageTest(){
        CommonMessage message = new CommonMessage("100", "Test Message", 10, "0000");
        group.AddMessage(message);
        boolean result = group.DeleteMessage(message);
        assertTrue(result);
    }

    @Test
    void HasMemberTest(){
        assertTrue(group.HasMember("100"));
    }

    @Test
    void removeMemberTest1(){
        boolean result = group.removeMember(user);
        assertTrue(result);
        List<String> memberIDs = group.getMemberIDs();
        assert memberIDs.isEmpty();
    }

    @Test
    void removeMemberTest2(){
        CommonUser newUser = new CommonUser("User2", "Password1", "200",
                "Bio", "20250823", new ArrayList<String>(),
                new ArrayList<String>(), new ArrayList<GroupChat>(),
                new ArrayList<GroupChat>());
        boolean result = group.removeMember(newUser);
        assertFalse(result);
    }

    @Test
    void setChatNameTest(){
        boolean result = group.setChatName("new name");
        assertTrue(result);
        assert "new name".equals(group.getChatName());
    }

    @Test
    void setMessageHistoryTest(){
        List<Message> messages = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            CommonMessage message = new CommonMessage("100", "Test Message"+i, i, Integer.toString(i));
            messages.add(message);
        }
        group.setMessageHistory(messages);
        List<Message> messageHistory = group.getMessageHistory();
        assertEquals(messages, messageHistory);
    }

    @Test
    void setChannelURLTest(){
        group.setChannelURL("channel.com");
        assert "channel.com".equals(group.getChannelURL());
    }


    @AfterEach
    public void tearDown() {
        group = null;
        user = null;
    }
}
