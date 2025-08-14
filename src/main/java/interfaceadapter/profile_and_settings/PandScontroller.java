package interfaceadapter.profile_and_settings;

import interfaceadapter.ViewManagerModel;
import interfaceadapter.main_menu.MainMenuViewModel;
import usecase.add_Bio.AddBioInputBoundary;
import usecase.add_Bio.AddBioInputData;
import usecase.add_DOB.AddDobInputBoundary;
import usecase.add_DOB.AddDobInputData;
import usecase.change_password.ChangePasswordInputBoundary;
import usecase.change_password.ChangePasswordInputData;

/**
 * The controller for the Profile and Settings View.
 */
public class PandScontroller {

    private final ViewManagerModel viewManagerModel;
    private final MainMenuViewModel mainMenuViewModel;
    private final PandSviewModel pandsViewModel;
    private final ChangePasswordInputBoundary changePasswordInputBoundary;
    private final AddBioInputBoundary addBioInputBoundary;
    private final AddDobInputBoundary addDobInputBoundary;

    public PandScontroller(ViewManagerModel viewManagerModel, MainMenuViewModel mainMenuViewModel,
                           PandSviewModel pandsViewModel, ChangePasswordInputBoundary changePasswordInteractor,
                           AddBioInputBoundary addBioInputBoundary, AddDobInputBoundary addDobInputBoundary) {
        this.viewManagerModel = viewManagerModel;
        this.mainMenuViewModel = mainMenuViewModel;
        this.pandsViewModel = pandsViewModel;
        this.changePasswordInputBoundary = changePasswordInteractor;
        this.addBioInputBoundary = addBioInputBoundary;
        this.addDobInputBoundary = addDobInputBoundary;
    }

    /**
     * Executes the change password use case.
     * @param password the new password to be changed.
     */
    public void changePassword(String password) {
        final String username = pandsViewModel.getState().getUsername();
        final ChangePasswordInputData changePasswordInputData = new ChangePasswordInputData(password, username);
        changePasswordInputBoundary.execute(changePasswordInputData);
    }

    /**
     * Executes the add bio use case.
     * @param newBio the new bio to be added.
     */
    public void addBio(String oldBio,String newBio) {
        final String username = pandsViewModel.getState().getUsername();
        final String password = pandsViewModel.getState().getChangePasswordText();
        final AddBioInputData inputData = new AddBioInputData(username, oldBio, newBio, password);
        addBioInputBoundary.execute(inputData);

    }

    /**
     * Executes the add doB use case.
     * @param newDOB the new doB to be added.
     */
    public void addDob(String oldDOB,String newDOB) {
        final String username = pandsViewModel.getState().getUsername();
        final String password = pandsViewModel.getState().getChangePasswordText();
        final AddDobInputData inputData = new AddDobInputData(oldDOB, newDOB, username, password);
        addDobInputBoundary.execute(inputData);

    }

    /**
     * Executes the switch to main menu use case.
     */
    public void switchToMenu() {
        final String currentUsername = pandsViewModel.getState().getUsername();
        mainMenuViewModel.getState().setUsername(currentUsername);
        mainMenuViewModel.firePropertyChanged();

        viewManagerModel.setState("main menu");
        viewManagerModel.firePropertyChanged();
    }

    /**
     * Executes the switch to logout use case.
     */
    public void logout() {
        viewManagerModel.setState("sign up");
        viewManagerModel.firePropertyChanged();
    }
}
