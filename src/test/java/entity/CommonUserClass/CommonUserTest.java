package entity.CommonUserClass;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import entity.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

public class CommonUserTest {

    private CommonUser user;
    private GroupChat group;

    @BeforeEach
    void setUp() {
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
    void getNameTest() {
        assertEquals("User", user.getName());
    }

    @Test
    void getPasswordTest() {
        assertEquals("Password1", user.getPassword());
    }

    @Test
    void setPasswordSuccessTest1() {
        String output = user.setPassword("Password2");
        assertEquals("Successfully set password.", output);
        assertEquals("Password2", user.getPassword());
        assertNotEquals("Password1", user.getPassword());
    }

    @Test
    void setPasswordFailTest1() {
        String output = user.setPassword("password2");
        assertEquals("Password must contain at least one digit, one uppercase letter, and one lowercase letter.",
                output);
        assertNotEquals("password2", user.getPassword());
        assertEquals("Password1", user.getPassword());
    }

    @Test
    void setPasswordFailTest2() {
        String output = user.setPassword("Password");
        assertEquals("Password must contain at least one digit, one uppercase letter, and one lowercase letter.",
                output);
        assertNotEquals("Password", user.getPassword());
        assertEquals("Password1", user.getPassword());
    }

    @Test
    void getIdTest() {
        assertEquals("100", user.getID());
    }

    @Test
    void getBioTest() {
        assertEquals("Bio", user.getBio());
    }

    @Test
    void getDOBTest() {
        assertEquals("20250823", user.getDOB());
    }

    @Test
    void setBioTest(){
        String oldBio = user.getBio();
        user.EditBiography("New Bio");
        assertEquals("New Bio", user.getBio());
        assertNotEquals(oldBio, user.getBio());
    }

    @Test
    void setDOBTest(){
        String oldDOB = user.getDOB();
        user.EditDOB("20250803");
        assertEquals("20250803", user.getDOB());
        assertNotEquals(oldDOB, user.getDOB());
    }

    @Test
    void addFriendTest() {
        CommonUser friend = new CommonUser("Friend", "Password1", "002",
                "Bio", "20250823", new ArrayList<String>(),
                new ArrayList<String>(), new ArrayList<GroupChat>(),
                new ArrayList<GroupChat>());
        // AddFriendInteractor covers both sides...
        user.addFriend(friend.getID());
        friend.addFriend(user.getID());
        List<String> friends = user.getFriendIDs();
        assert friends.contains(friend.getID());
        friends = friend.getFriendIDs();
        assert friends.contains(user.getID());
    }

    /*
     * assumes that GroupChatDataAccessObject.create works
     */
    @Test
    void addGroupChatTest() {
        user.addGroupChat(group);
        List<GroupChat> groupChats = user.getGroupChats();
        assert groupChats.contains(group);
    }

    @Test
    void getChatTest1() {
        Chat groupChat = user.getChat(group.getChannelUrl());
        assert groupChat == null;
    }

    @Test
    void getChatTest2() {
        user.addGroupChat(group);
        Chat groupChat = user.getChat(group.getChannelUrl());
        assertEquals(group, groupChat);
    }

    @Test
    void blockUserTest1() {
        boolean result = user.blockUser("001");
        assertFalse(result);
    }

    @Test
    void blockUserTest2() {
        user.addFriend("001");
        boolean result = user.blockUser("001");
        assertTrue(result);
        result = user.blockUser("001");
        assertFalse(result);
    }

    @Test
    void unblockUserTest1() {
        boolean result = user.unblockUser("001", false);
        assertFalse(result);
    }

    @Test
    void unblockUserTest2() {
        user.addFriend("001");
        boolean result = user.unblockUser("001", true);
        assertFalse(result);
    }

    @Test
    void unblockUserTest3() {
        user.addFriend("001");
        user.blockUser("001");
        boolean result = user.unblockUser("001", true);
        assertTrue(result);
    }

    @Test
    void getBlockedUsersTest() {
        List<String> blocked = user.getBlockedUserIDs();
        assert blocked.isEmpty();
    }

    @Test
    void isBlockedTest1() {
        boolean result = user.isBlocked("001");
        assertFalse(result);
    }

    @Test
    void isBlockedTest2() {
        user.addFriend("001");
        user.blockUser("001");
        boolean result = user.isBlocked("001");
        assertTrue(result);
    }

    @Test
     void createUserDataTest(){
        JsonObject userData = new JsonObject();
        userData.addProperty("username", "User");
        userData.addProperty("userId", "100");
        userData.addProperty("password", "Password1");
        userData.addProperty("biography", "Bio");
        userData.addProperty("dateOfBirth", "20250823");

        JsonArray friendIDsJson = new JsonArray();
        JsonArray blockedIDsJson = new JsonArray();
        JsonArray groupChannelURLsJson = new JsonArray();
        JsonArray personalChannelURLsJson = new JsonArray();
        userData.add("friendIDs", friendIDsJson);
        userData.add ("blockedIDs", blockedIDsJson);
        userData.add("groupChannelURLs", groupChannelURLsJson);
        userData.add("personalChannelURLs", personalChannelURLsJson);

        JsonObject receivedUserData = user.getUserData();
        assertEquals(userData.get("username"), receivedUserData.get("username"));
        assertEquals(userData.get("userId"), receivedUserData.get("userId"));
        assertEquals(userData.get("password"), receivedUserData.get("password"));
        assertEquals(userData.get("biography"), receivedUserData.get("biography"));
        assertEquals(userData.get("dateOfBirth"), receivedUserData.get("dateOfBirth"));
        assertEquals(userData.get("friendIDs"), userData.get("friendIDs"));
        assertEquals(userData.get("groupChannelURLs"), userData.get("groupChannelURLs"));
        assertEquals(userData.get("blockedIDs"), userData.get("blockedIDs"));
        assertEquals(userData.get("personalChannelURLs"), userData.get("personalChannelURLs"));


    }

    @AfterEach
    void tearDown() {
        user = null;
        group = null;

    }





}
