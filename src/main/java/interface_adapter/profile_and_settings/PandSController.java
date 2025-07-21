package interface_adapter.profile_and_settings;

import interface_adapter.ViewManagerModel;
import interface_adapter.main_menu.MainMenuViewModel;
import use_case.add_Bio.AddBioInputBoundary;
import use_case.add_Bio.AddBioInputData;
import use_case.add_DOB.AddDOBInputBoundary;
import use_case.add_DOB.AddDOBInputData;
import use_case.change_password.ChangePasswordInputBoundary;
import use_case.change_password.ChangePasswordInputData;

/**
 * The controller for the Profile and Settings View.
 */
public class PandSController {

    private final ViewManagerModel viewManagerModel;
    private final MainMenuViewModel mainMenuViewModel;
    private final PandSViewModel pandSViewModel;
    private final ChangePasswordInputBoundary changePasswordInputBoundary;
    private final AddBioInputBoundary addBioInputBoundary;
    private final AddDOBInputBoundary addDOBInputBoundary;

    public PandSController(ViewManagerModel viewManagerModel,MainMenuViewModel mainMenuViewModel,
                           PandSViewModel pandSViewModel, ChangePasswordInputBoundary changePasswordInteractor,
                           AddBioInputBoundary addBioInputBoundary, AddDOBInputBoundary addDOBInputBoundary) {
        this.viewManagerModel = viewManagerModel;
        this.mainMenuViewModel = mainMenuViewModel;
        this.pandSViewModel = pandSViewModel;
        this.changePasswordInputBoundary = changePasswordInteractor;
        this.addBioInputBoundary = addBioInputBoundary;
        this.addDOBInputBoundary = addDOBInputBoundary;
    }

    /**
     * Executes the change password use case.
     * @param password the new password to be changed.
     */
    public void changePassword(String password) {
        String username = pandSViewModel.getState().getUsername();
        ChangePasswordInputData changePasswordInputData = new ChangePasswordInputData(password,username);
        changePasswordInputBoundary.execute(changePasswordInputData);
    }

    /**
     * Executes the add bio use case.
     * @param bio the new bio to be added.
     */
    public void addBio(String bio) {
        String username = pandSViewModel.getState().getUsername();
        String password = pandSViewModel.getState().getChangePasswordText();
        AddBioInputData inputData = new AddBioInputData(bio,username,password);
        addBioInputBoundary.execute(inputData);

    }

    /**
     * Executes the add DOB use case.
     * @param DOB the new DOB to be added.
     */
    public void addDOB(String DOB) {
        String username = pandSViewModel.getState().getUsername();
        String password = pandSViewModel.getState().getChangePasswordText();
        AddDOBInputData inputData = new AddDOBInputData(DOB,username,password);
        addDOBInputBoundary.execute(inputData);

    }

    /**
     * Executes the switch to main menu use case.
     */
    public void switchToMenu() {
        final String currentUsername = pandSViewModel.getState().getUsername();
        mainMenuViewModel.getState().setUsername(currentUsername);
        mainMenuViewModel.firePropertyChanged();

        viewManagerModel.setState("Main Menu");
        viewManagerModel.firePropertyChanged();
    }

    /**
     * Executes the switch to logout use case.
     */
    public void logout() {
        viewManagerModel.setState("Logout");
        viewManagerModel.firePropertyChanged();
    }
}
