package usecase.add_DOB;

import dataaccess.InMemoryUserDataAccessObject;
import entity.CommonUser;
import entity.CommonUserFactory;
import entity.GroupChat;
import entity.Message;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AddDOBInteractorTest {

    private CommonUser user;
    private InMemoryUserDataAccessObject dataAccess;

    @BeforeEach
    void setUp(){
        List<String> membersID = new ArrayList<>();
        membersID.add("100");
        GroupChat selfChat = new GroupChat(membersID, "User's self chat", new ArrayList<Message>());
        user= new CommonUser("User", "Password1",
                "100", "Bio", "20250808",
                new ArrayList<String>(), new ArrayList<String>(),
                new ArrayList<GroupChat>(), new ArrayList<GroupChat>(),
                selfChat);
        dataAccess = new InMemoryUserDataAccessObject();
    }

    @Test
    // success case for Add DOB
    void addDOBInteractorSuccessTest(){
        dataAccess.save(user);
        AddDobInputData inputData = new AddDobInputData("20061007", user.getName(), user.getPassword());
        AddDobOutputBoundary presneter = new AddDobOutputBoundary() {
            @Override
            public void prepareSuccessAddDobView(AddDobOutputData addDobOutputData) {
                assertEquals(user.getName(), addDobOutputData.getUsername());
                assertFalse(addDobOutputData.getUseCaseFailed());
                assertEquals("20061007", addDobOutputData.getDob());
                assertEquals("20061007", user.getDOB());
            }

            @Override
            public void prepareFailAddDobView(String errorMessage, AddDobOutputData addDobOutputData) {
                fail("Interactor does not work on success case");
            }
        };

        AddDobInteractor interactor = new AddDobInteractor(dataAccess, presneter, new CommonUserFactory());
        interactor.execute(inputData);
    }

    @Test
    // DOB is not of appropriate length
    void addDOBInteractorFailTest1(){
        dataAccess.save(user);
        AddDobInputData inputData = new AddDobInputData("1", user.getName(), user.getPassword());
        AddDobOutputBoundary presneter = new AddDobOutputBoundary() {
            @Override
            public void prepareSuccessAddDobView(AddDobOutputData addDobOutputData) {
               fail("Interactor does not check input format");
            }

            @Override
            public void prepareFailAddDobView(String errorMessage, AddDobOutputData addDobOutputData) {
                assertEquals("The input should be in the format YYYYMMDD", errorMessage);
                assertEquals(user.getName(), addDobOutputData.getUsername());
                assertTrue(addDobOutputData.getUseCaseFailed());
                assertEquals("20250808", addDobOutputData.getDob());
                assertEquals("20250808", user.getDOB());
            }
        };

        AddDobInteractor interactor = new AddDobInteractor(dataAccess, presneter, new CommonUserFactory());
        interactor.execute(inputData);
    }

    @Test
    // DOB is contains non-digit characters
    void addDOBInteractorFailTest2(){
        dataAccess.save(user);
        AddDobInputData inputData = new AddDobInputData("abcdefgh", user.getName(), user.getPassword());
        AddDobOutputBoundary presneter = new AddDobOutputBoundary() {
            @Override
            public void prepareSuccessAddDobView(AddDobOutputData addDobOutputData) {
                fail("Interactor does not check input format");
            }

            @Override
            public void prepareFailAddDobView(String errorMessage, AddDobOutputData addDobOutputData) {
                assertEquals("The input should only contain numbers", errorMessage);
                assertEquals(user.getName(), addDobOutputData.getUsername());
                assertTrue(addDobOutputData.getUseCaseFailed());
                assertEquals("20250808", addDobOutputData.getDob());
                assertEquals("20250808", user.getDOB());
            }
        };

        AddDobInteractor interactor = new AddDobInteractor(dataAccess, presneter, new CommonUserFactory());
        interactor.execute(inputData);
    }

    @AfterEach
    void tearDown(){
        user = null;
        dataAccess = null;
    }

}
