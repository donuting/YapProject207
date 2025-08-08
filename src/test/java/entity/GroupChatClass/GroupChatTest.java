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
    void getChannelUrlTest(){
        assertEquals("chat.com", group.getChannelUrl());
    }

    @Test
    void getMessageHistoryTest(){
        assert group.getMessageHistory().isEmpty();
    }

    @Test
    void getMembersTest(){
        List<String> memberIDs = group.getMemberIds();
        assert memberIDs.size() == 1;
        assert memberIDs.contains("100");
    }

    @Test
    void getChatNameTest(){
        assert "Test Chat".equals(group.getChatName());
    }

    @Test
    void addMemberTest1(){
        CommonUser newUser = new CommonUser("User2", "Password1", "200",
                "Bio", "20250823", new ArrayList<String>(),
                new ArrayList<String>(), new ArrayList<GroupChat>(),
                new ArrayList<GroupChat>());
        boolean result = group.addMember("200");
        assertTrue(result);
        List<String> memberIDs = group.getMemberIds();
        assert memberIDs.contains("200");
        result = group.addMember("200");
        assertFalse(result);
    }

    @Test
    void addMemberTest2(){
        CommonUser newUser = new CommonUser("User2", "Password1", "200",
                "Bio", "20250823", new ArrayList<String>(),
                new ArrayList<String>(), new ArrayList<GroupChat>(),
                new ArrayList<GroupChat>());
        user.blockUser("200");
        boolean result = group.addMember("200");
        assertFalse(result);
        List<String> memberIDs = group.getMemberIds();
        assert !memberIDs.contains("200");
    }

    @Test
    void addMessageTest1(){
        CommonMessage message = new CommonMessage("100", "Test Message", 10, "0000");
        boolean result = group.addMessage(message);
        assertTrue(result);
        List<Message> messages = group.getMessageHistory();
        assert  messages.contains(message);
    }

    @Test
    void addMessageTest2(){
       CommonMessage message = new CommonMessage("200", "Test Message", 10, "0000");
       boolean result = group.addMessage(message);
       assertFalse(result);
    }

    @Test
    void addMessageTest3(){
        CommonUser newUser = new CommonUser("User2", "Password1", "200",
                "Bio", "20250823", new ArrayList<String>(),
                new ArrayList<String>(), new ArrayList<GroupChat>(),
                new ArrayList<GroupChat>());
        group.addMember("200");
        user.blockUser("200");
        CommonMessage message = new CommonMessage("100", "Test Message", 10, "0000");
        boolean result = group.addMessage(message);
        assertFalse(result);
    }

    @Test
    void addMessageTest4(){
        CommonUser newUser = new CommonUser("User2", "Password1", "200",
                "Bio", "20250823", new ArrayList<String>(),
                new ArrayList<String>(), new ArrayList<GroupChat>(),
                new ArrayList<GroupChat>());
        group.addMember("200");
        newUser.blockUser("100");
        CommonMessage message = new CommonMessage("100", "Test Message", 10, "0000");
        boolean result = group.addMessage(message);
        assertFalse(result);
    }

    @Test
    void deleteMessageTest(){
        CommonMessage message = new CommonMessage("100", "Test Message", 10, "0000");
        group.addMessage(message);
        boolean result = group.deleteMessage(message.GetMID().toString());
        assertTrue(result);
    }

    @Test
    void hasMemberTest(){
        assertTrue(group.hasMember("100"));
    }

    @Test
    void removeMemberTest1(){
        boolean result = group.removeMember(user);
        assertTrue(result);
        List<String> memberIDs = group.getMemberIds();
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
    void setChannelUrlTest(){
        group.setChannelUrl("channel.com");
        assert "channel.com".equals(group.getChannelUrl());
    }


    @AfterEach
    public void tearDown() {
        group = null;
        user = null;
    }
}
