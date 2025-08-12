package use_case.add_Bio;

import data_access.InMemoryUserDataAccessObject;
import entity.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AddBioInteractorTest {

    private CommonUser user;
    private  InMemoryUserDataAccessObject dataAccess;

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
    // Success case for AddBio
    void AddBioInteractorSuccessTest(){
        dataAccess.save(user);
        AddBioInputData inputData = new AddBioInputData(user.getName(), "New Bio", user.getPassword());
        AddBioOutputBoundary presenter = new AddBioOutputBoundary() {
            @Override
            public void prepareSuccessAddBioView(AddBioOutputData addBioOutputData) {
                assertEquals(user.getName(), addBioOutputData.getUsername());
                assertEquals("New Bio", addBioOutputData.getBio());
                assertFalse(addBioOutputData.isUseCaseFailed());
                assertEquals("New Bio", user.getBio());
            }

            @Override
            public void prepareFailAddBioView(String errorMessage, AddBioOutputData addBioOutputData) {
                fail("Interactor does not work on success case");
            }
        };
        AddBioInteractor interactor = new AddBioInteractor(dataAccess, presenter);
        interactor.execute(inputData);
    }

    @Test
    // input bio is empty
    void AddBioInteractorFailTest1(){
        dataAccess.save(user);
        AddBioInputData inputData = new AddBioInputData(user.getName(), "", user.getPassword());
        AddBioOutputBoundary presenter = new AddBioOutputBoundary() {
            @Override
            public void prepareSuccessAddBioView(AddBioOutputData addBioOutputData) {
                fail("Interactor does not check if the bio is empty");
            }

            @Override
            public void prepareFailAddBioView(String errorMessage, AddBioOutputData addBioOutputData) {
                assertEquals("Add Bio Failed", errorMessage);
            }
        };
        AddBioInteractor interactor = new AddBioInteractor(dataAccess, presenter);
        interactor.execute(inputData);
    }

    @Test
    // input bio matches password
    void AddBioInteractorFailTest2(){
        dataAccess.save(user);
        AddBioInputData inputData = new AddBioInputData(user.getName(), user.getPassword(), user.getPassword());
        AddBioOutputBoundary presenter = new AddBioOutputBoundary() {
            @Override
            public void prepareSuccessAddBioView(AddBioOutputData addBioOutputData) {
                fail("Interactor does not check if the bio matches password");
            }

            @Override
            public void prepareFailAddBioView(String errorMessage, AddBioOutputData addBioOutputData) {
                assertEquals("Add Bio Failed", errorMessage);
            }
        };
        AddBioInteractor interactor = new AddBioInteractor(dataAccess, presenter);
        interactor.execute(inputData);
    }

    @AfterEach
    void tearDown(){
        user = null;
        dataAccess = null;
    }

}
