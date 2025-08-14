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
    // tests getChannelURL method
    void getChannelURLTest(){
        assertEquals("chat.com", group.getChannelUrl());
    }

    @Test
    // test getMessageHistory method
    void getMessageHistoryTest(){
        assert group.getMessageHistory().isEmpty();
    }

    @Test
    // tests getMemebrIDs method
    void getMembersTest(){
        List<String> memberIDs = group.getMemberIds();
        assert memberIDs.size() == 1;
        assert memberIDs.contains("100");
    }

    @Test
    //tests getChatName method
    void getChatNameTest(){
        assert "Test Chat".equals(group.getChatName());
    }

    @Test
    // tests if addMember adds the member ID to memberIDs
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
    // Tests if message is added to messageHistory
    void addMessageTest1(){
        CommonMessage message = new CommonMessage("100", "Test Message", 10, "0000");
        boolean result = group.addMessage(message);
        assertTrue(result);
        List<Message> messages = group.getMessageHistory();
        assert  messages.contains(message);
    }

    @Test
    void deleteMessageTest1(){
        CommonMessage message1 = new CommonMessage("100", "Test Message", 10, "0000");
        CommonMessage message2 = new CommonMessage("100", "Test Message", 20, "0000");
        group.addMessage(message1);
        group.addMessage(message2);
        boolean result = group.deleteMessage(message1.GetMID().toString());
        assertTrue(result);
    }



    @Test
    // checks hasMember method
    void hasMemberTest(){
        assertTrue(group.hasMember("100"));
    }

    @Test
    // checks removerMember() method
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
    //checks setChannelURL test
    void setChannelUrlTest(){
        group.setChannelUrl("channel.com");
        assert "channel.com".equals(group.getChannelUrl());
    }

    @Test
    void setMemberIdTest(){
        List<String> memberIDs = new ArrayList<>();
        memberIDs.add("100");
        memberIDs.add("200");
        group.setMemberIds(memberIDs);
        assertEquals(memberIDs, group.getMemberIds());
    }


    @AfterEach
    public void tearDown() {
        group = null;
        user = null;
    }
}
