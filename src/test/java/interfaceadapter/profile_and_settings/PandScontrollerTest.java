package interfaceadapter.profile_and_settings;

import dataaccess.InMemoryUserDataAccessObject;
import entity.CommonUserFactory;
import entity.UserFactory;
import interfaceadapter.ViewManagerModel;
import interfaceadapter.change_password.ChangePasswordPresenter;
import interfaceadapter.change_password.LoggedInViewModel;
import interfaceadapter.main_menu.MainMenuViewModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import usecase.add_Bio.AddBioInputBoundary;
import usecase.add_Bio.AddBioInputData;
import usecase.add_Bio.AddBioInteractor;
import usecase.add_DOB.AddDobInputBoundary;
import usecase.add_DOB.AddDobInputData;
import usecase.add_DOB.AddDobInteractor;
import usecase.change_password.ChangePasswordInputBoundary;
import usecase.change_password.ChangePasswordInputData;
import usecase.change_password.ChangePasswordInteractor;
import usecase.change_password.ChangePasswordOutputBoundary;
import usecase.delete_account.DeleteAccountInputBoundary;
import usecase.delete_account.DeleteAccountInputData;
import usecase.delete_account.DeleteAccountInteractor;


class PandScontrollerTest {
    final UserFactory userFactory = new CommonUserFactory();
    final MainMenuViewModel mainMenuViewModel = new MainMenuViewModel();
    final ViewManagerModel viewManagerModel = new ViewManagerModel();
    final PandSviewModel profileAndSettingViewModel = new PandSviewModel();
    final InMemoryUserDataAccessObject userDataAccessObject = new InMemoryUserDataAccessObject();
    final LoggedInViewModel loggedInViewModel = new LoggedInViewModel();
    final ChangePasswordOutputBoundary changePasswordOutputBoundary =
            new ChangePasswordPresenter(loggedInViewModel);

    final ChangePasswordInputBoundary changePasswordInteractor =
            new ChangePasswordInteractor(userDataAccessObject, changePasswordOutputBoundary, userFactory);

    final PandSpresenter profileAndSettingsPresenter =
            new PandSpresenter(profileAndSettingViewModel);

    final AddBioInputBoundary addBioInputBoundary =
            new AddBioInteractor(userDataAccessObject, profileAndSettingsPresenter);

    final AddDobInputBoundary addDateOfBirthInputBoundary =
            new AddDobInteractor(userDataAccessObject, profileAndSettingsPresenter, userFactory);

    final DeleteAccountInputBoundary deleteAccountInputBoundary =
            new DeleteAccountInteractor(userDataAccessObject, profileAndSettingsPresenter);

    @Test
    void switchToMenuTest() {
        final PandSviewModel profileAndSettingViewModelTest = new PandSviewModel();
        profileAndSettingViewModelTest.getState().setUsername("current username");

        final MainMenuViewModel mainMenuViewModelTest = new MainMenuViewModel() {
            @Override
            public void firePropertyChanged() {
                Assertions.assertEquals("current username", getState().getUsername());
            }
        };

        final ViewManagerModel viewManagerModelTest = new ViewManagerModel() {
            @Override
            public void firePropertyChanged() {
                Assertions.assertEquals("main menu", getState());
            }
        };

        final PandScontroller profileAndSettingsController = new PandScontroller(
                viewManagerModelTest,
                mainMenuViewModelTest,
                profileAndSettingViewModelTest,
                changePasswordInteractor,
                addBioInputBoundary,
                addDateOfBirthInputBoundary,
                deleteAccountInputBoundary);
        profileAndSettingsController.switchToMenu();
    }

    @Test
    void logoutTest() {
        final ViewManagerModel viewManagerModelTest = new ViewManagerModel() {
            @Override
            public void firePropertyChanged() {
                Assertions.assertEquals("sign up", getState());
            }
        };

        final PandScontroller profileAndSettingsController = new PandScontroller(
                viewManagerModelTest,
                mainMenuViewModel,
                profileAndSettingViewModel,
                changePasswordInteractor,
                addBioInputBoundary,
                addDateOfBirthInputBoundary,
                deleteAccountInputBoundary);
        profileAndSettingsController.logout();
    }

    @Test
    void deleteAccountTest() {
        final DeleteAccountInputBoundary deleteAccountInputBoundaryTest =
                new DeleteAccountInteractor(userDataAccessObject, profileAndSettingsPresenter) {
            @Override
            public void execute(DeleteAccountInputData deleteAccountInputData) {
                Assertions.assertEquals("username", deleteAccountInputData.getUsername());
            }
        };
        final PandSviewModel profileAndSettingViewModelTest = new PandSviewModel();
        profileAndSettingViewModelTest.getState().setUsername("username");

        final PandScontroller profileAndSettingsController = new PandScontroller(
                viewManagerModel,
                mainMenuViewModel,
                profileAndSettingViewModelTest,
                changePasswordInteractor,
                addBioInputBoundary,
                addDateOfBirthInputBoundary,
                deleteAccountInputBoundaryTest);
        profileAndSettingsController.deleteAccount();
    }

    @Test
    void changePasswordTest() {
         final ChangePasswordInputBoundary changePasswordInputBoundaryTest =
                 new ChangePasswordInteractor(userDataAccessObject, profileAndSettingsPresenter, userFactory) {
                    @Override
                    public void execute(ChangePasswordInputData changePasswordInputData) {
                        Assertions.assertEquals("password", changePasswordInputData.getPassword());
                        Assertions.assertEquals("username", changePasswordInputData.getUsername());
                    }
                };
        final PandSviewModel profileAndSettingViewModelTest = new PandSviewModel();
        profileAndSettingViewModelTest.getState().setUsername("username");

        final PandScontroller profileAndSettingsController = new PandScontroller(
                viewManagerModel,
                mainMenuViewModel,
                profileAndSettingViewModelTest,
                changePasswordInputBoundaryTest,
                addBioInputBoundary,
                addDateOfBirthInputBoundary,
                deleteAccountInputBoundary);
        profileAndSettingsController.changePassword("password");
    }

    @Test
    void addBioTest() {
        final AddBioInputBoundary addBioInputBoundaryTest =
                new AddBioInteractor(userDataAccessObject, profileAndSettingsPresenter) {
                    @Override
                    public void execute(AddBioInputData addBioInputData) {
                        Assertions.assertEquals("password", addBioInputData.getPassword());
                        Assertions.assertEquals("username", addBioInputData.getUsername());
                        Assertions.assertEquals("bio", addBioInputData.getBio());
                    }
                };
        final PandSviewModel profileAndSettingViewModelTest = new PandSviewModel();
        profileAndSettingViewModelTest.getState().setUsername("username");
        profileAndSettingViewModelTest.getState().setChangePasswordText("password");

        final PandScontroller profileAndSettingsController = new PandScontroller(
                viewManagerModel,
                mainMenuViewModel,
                profileAndSettingViewModelTest,
                changePasswordInteractor,
                addBioInputBoundaryTest,
                addDateOfBirthInputBoundary,
                deleteAccountInputBoundary);
        profileAndSettingsController.addBio("bio");
    }

    @Test
    void addDobTest() {
        final AddDobInputBoundary addDateOfBirthInputBoundaryTest =
                new AddDobInteractor(userDataAccessObject, profileAndSettingsPresenter, userFactory) {
                    @Override
                    public void execute(AddDobInputData addDobInputData) {
                        Assertions.assertEquals("password", addDobInputData.getPassword());
                        Assertions.assertEquals("username", addDobInputData.getUsername());
                        Assertions.assertEquals("dob", addDobInputData.getDateOfBirth());
                    }
                };
        final PandSviewModel profileAndSettingViewModelTest = new PandSviewModel();
        profileAndSettingViewModelTest.getState().setUsername("username");
        profileAndSettingViewModelTest.getState().setChangePasswordText("password");

        final PandScontroller profileAndSettingsController = new PandScontroller(
                viewManagerModel,
                mainMenuViewModel,
                profileAndSettingViewModelTest,
                changePasswordInteractor,
                addBioInputBoundary,
                addDateOfBirthInputBoundaryTest,
                deleteAccountInputBoundary);
        profileAndSettingsController.addDob("dob");
    }
}
