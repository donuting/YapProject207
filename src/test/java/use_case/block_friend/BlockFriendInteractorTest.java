package use_case.block_friend;

import data_access.InMemoryUserDataAccessObject;
import entity.CommonUserFactory;
import entity.User;
import entity.UserFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BlockFriendInteractorTest {

    private InMemoryUserDataAccessObject dataAccess;
    private UserFactory userFactory;
    private User user;
    private User friend;

    @BeforeEach
    void setUp() {
        dataAccess = new InMemoryUserDataAccessObject();
        userFactory = new CommonUserFactory();
        user = userFactory.create("User", "Password1");
        friend = userFactory.create("Friend", "Password2");
        dataAccess.save(user);
        dataAccess.save(friend);
        dataAccess.setCurrentUser(user);
    }

    @Test
    void blockFriendSuccessTest() {
        BlockFriendInputData inputData = new BlockFriendInputData(user.getName(), friend.getName());
        BlockFriendOutputBoundary successPresenter = new BlockFriendOutputBoundary() {
            @Override
            public void prepareSuccessView(BlockFriendOutputData outputData) {
                assertEquals(user.getName(), outputData.getCurrentUsername());
                assertEquals(friend.getName(), outputData.getBlockedUsername());
                assertTrue(outputData.isSuccess());
            }

            @Override
            public void prepareFailView(String errorMessage, BlockFriendOutputData outputData) {
                fail("Block friend use case should succeed");
            }
        };
        BlockFriendInteractor interactor = new BlockFriendInteractor(dataAccess, successPresenter);
        interactor.execute(inputData);
    }

    @Test
    void blockFriendFailFriendDoesNotExistTest() {
        BlockFriendInputData inputData = new BlockFriendInputData(user.getName(), "NonExistentUser");
        BlockFriendOutputBoundary failPresenter = new BlockFriendOutputBoundary() {
            @Override
            public void prepareSuccessView(BlockFriendOutputData outputData) {
                fail("Block friend use case should fail when blocked user does not exist");
            }

            @Override
            public void prepareFailView(String errorMessage, BlockFriendOutputData outputData) {
                assertFalse(outputData.isSuccess());
                assertEquals("Failed to block friend.", errorMessage);
            }
        };
        BlockFriendInteractor interactor = new BlockFriendInteractor(dataAccess, failPresenter);
        interactor.execute(inputData);
    }

    @Test
    void blockFriendFailUserDoesNotExistTest() {
        dataAccess.setCurrentUser(null); 
        BlockFriendInputData inputData = new BlockFriendInputData("NonExistentUser", friend.getName());
        BlockFriendOutputBoundary failPresenter = new BlockFriendOutputBoundary() {
            @Override
            public void prepareSuccessView(BlockFriendOutputData outputData) {
                fail("Block friend use case should fail when current user does not exist");
            }

            @Override
            public void prepareFailView(String errorMessage, BlockFriendOutputData outputData) {
                assertFalse(outputData.isSuccess());
                assertEquals("Failed to block friend.", errorMessage);
            }
        };
        BlockFriendInteractor interactor = new BlockFriendInteractor(dataAccess, failPresenter);
        interactor.execute(inputData);
    }

    @AfterEach
    void tearDown() {
        dataAccess = null;
        user = null;
        friend = null;
        userFactory = null;
    }
}
