package entity.CommonMessageClass;

import static org.junit.jupiter.api.Assertions.*;

import entity.CommonMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class CommonMessageTest {

    private static CommonMessage commonMessage;

    @BeforeEach
    void startSetUp(){
        commonMessage = new CommonMessage("69", "Test Message");
    }

    @Test
    void getSenderIDTest(){
        String ID = commonMessage.GetSenderId();
        assertEquals("69", ID);
    }

    @Test
    void getTextTest(){
        String text = commonMessage.GetText();
        assertEquals("Test Message", text);
    }

    @Test
    void getMIDTest(){
        commonMessage = new CommonMessage("69", "Test Message", 100, "0000");
        Integer MID = commonMessage.GetMID();
        assertEquals(100, MID);
    }

    @Test
    void setMIDTest(){
        Integer originalMID = commonMessage.GetMID();
        commonMessage.SetMID(-1);
        Integer newMID = commonMessage.GetMID();
        assertNotEquals(originalMID, newMID);
        assertEquals(-1, newMID);
    }
}
