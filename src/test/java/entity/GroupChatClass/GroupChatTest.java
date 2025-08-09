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
        assertEquals("chat.com", group.getChannelURL());
    }


    @Test
    // test getMessageHistory method
    void getMessageHistoryTest(){
        assert group.getMessageHistory().isEmpty();
    }

    @Test
    // tests getMemebrIDs method
    void getMembersTest(){
        List<String> memberIDs = group.getMemberIDs();
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
    // tests if blocked members can be added to a chat
    // TODO: business logic to be checked in interactor
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
    // Tests if message is added to messageHistory
    void AddMessageTest1(){
        CommonMessage message = new CommonMessage("100", "Test Message", 10, "0000");
        boolean result = group.AddMessage(message);
        assertTrue(result);
        List<Message> messages = group.getMessageHistory();
        assert  messages.contains(message);
    }

    @Test
    // tests if messages sent by members not in teh group are added into messageHistory
    //TODO: business logic to be tested in teh interactor
    void AddMessageTest2(){
       CommonMessage message = new CommonMessage("200", "Test Message", 10, "0000");
       boolean result = group.AddMessage(message);
       assertFalse(result);
    }

    @Test
    // If there is a blocked user in teh chat, then the sender cannot send the message in the chat
    //TODO: business logic to be tested in teh interactor
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
    // tests if a member in the chat is blocked then the sender cannot senf the message in chat
    // TODO: business logic to be tested in the interactor
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
    // tests if deleteMessage() deleted message from message History
    void DeleteMessageTest(){
        CommonMessage message = new CommonMessage("100", "Test Message", 10, "0000");
        group.AddMessage(message);
        boolean result = group.DeleteMessage(message);
        assertTrue(result);
    }

    @Test
    // checks hasMember method
    void HasMemberTest(){
        assertTrue(group.HasMember("100"));
    }

    @Test
    // checks removerMember() method
    void removeMemberTest1(){
        boolean result = group.removeMember(user);
        assertTrue(result);
        List<String> memberIDs = group.getMemberIDs();
        assert memberIDs.isEmpty();
    }

    @Test
    //Tests if a user is not in group, then the method returns false
    void removeMemberTest2(){
        CommonUser newUser = new CommonUser("User2", "Password1", "200",
                "Bio", "20250823", new ArrayList<String>(),
                new ArrayList<String>(), new ArrayList<GroupChat>(),
                new ArrayList<GroupChat>());
        boolean result = group.removeMember(newUser);
        assertFalse(result);
    }

    @Test
    // tests setChat method
    void setChatNameTest(){
        boolean result = group.setChatName("new name");
        assertTrue(result);
        assert "new name".equals(group.getChatName());
    }

    @Test
    //tests setMessageHistory method
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
