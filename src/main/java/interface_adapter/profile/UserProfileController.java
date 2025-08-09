package interface_adapter.profile;

import interface_adapter.ViewManagerModel;
import use_case.profile.UserProfileInputBoundary;
import use_case.profile.UserProfileInputData;

/**
 * The controller for the User Profile Use Case.
 */
public class UserProfileController {

    private final UserProfileInputBoundary userProfileUseCaseInteractor;
    private final ViewManagerModel viewManagerModel;

    public UserProfileController(UserProfileInputBoundary userProfileUseCaseInteractor,
                                 ViewManagerModel viewManagerModel) {
        this.userProfileUseCaseInteractor = userProfileUseCaseInteractor;
        this.viewManagerModel = viewManagerModel;
    }

    /**
     * Executes the "save profile" Use Case.
     * @param username the new username
     * @param bio the new bio
     * @param dateOfBirth the new date of birth
     */
    public void saveProfile(String oldUsername, String username, String bio, String dateOfBirth) {
        final UserProfileInputData userProfileInputData = new UserProfileInputData(oldUsername, username, bio, dateOfBirth);
        userProfileUseCaseInteractor.saveProfile(userProfileInputData);
    }

    /**
     * Executes the "load profile" Use Case.
     * @param username the username whose profile to load
     */
    public void loadProfile(String username) {
        userProfileUseCaseInteractor.loadProfile(username);
    }

    /**
     * Navigates back to the previous view.
     */
    public void goBack() {
        // Navigate back to main menu or previous view
        viewManagerModel.setState("main menu");
        viewManagerModel.firePropertyChanged();
    }
}