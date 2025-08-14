package interfaceadapter.profile;

import dataaccess.SendBirdUserDataAccessObject;
import interfaceadapter.ViewManagerModel;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import usecase.profile.UserProfileInputBoundary;
import usecase.profile.UserProfileInputData;
import usecase.profile.UserProfileInteractor;
import usecase.profile.UserProfileOutputBoundary;

public class ProfileControllerTest {
    final UserProfileViewModel userProfileViewModel = new UserProfileViewModel();
    final SendBirdUserDataAccessObject userDataAccessObject = new SendBirdUserDataAccessObject();
    final ViewManagerModel viewManagerModel = new ViewManagerModel();
    final UserProfileOutputBoundary userProfileOutputBoundary =
            new UserProfilePresenter(userProfileViewModel);
    final UserProfileInputBoundary userProfileInteractor =
            new UserProfileInteractor(userDataAccessObject, userProfileOutputBoundary);

    @Test
    void goBackTest() {
        final ViewManagerModel viewManagerModelTest = new ViewManagerModel() {
            @Override
            public void firePropertyChanged() {
                Assertions.assertEquals("main menu", getState());
                Assertions.assertTrue(true);
            }
        };
        final UserProfileController userProfileController =
                new UserProfileController(userProfileInteractor, viewManagerModelTest);
        userProfileController.goBack();
    }

    @Test
    void loadProfileTest() {
        final UserProfileInputBoundary userProfileInteractorTest =
                new UserProfileInteractor(userDataAccessObject, userProfileOutputBoundary) {
                    @Override
                    public void loadProfile(String username) {
                    Assertions.assertEquals("username", username);
                    }
                };
        final UserProfileController userProfileController =
                new UserProfileController(userProfileInteractorTest, viewManagerModel);
        userProfileController.loadProfile("username");
    }

    @Test
    void saveProfileTest() {
        final UserProfileInputBoundary userProfileInteractorTest =
                new UserProfileInteractor(userDataAccessObject, userProfileOutputBoundary) {
                    @Override
                    public void saveProfile(UserProfileInputData userProfileInputData) {
                        Assertions.assertEquals("username", userProfileInputData.getUsername());
                        Assertions.assertEquals("oldUsername", userProfileInputData.getOldUsername());
                        Assertions.assertEquals("bio", userProfileInputData.getBio());
                        Assertions.assertEquals("dateOfBirth", userProfileInputData.getDateOfBirth());
                    }
                };
        final UserProfileController userProfileController =
                new UserProfileController(userProfileInteractorTest, viewManagerModel);
        userProfileController.saveProfile("oldUsername", "username", "bio", "dateOfBirth");
    }
}
