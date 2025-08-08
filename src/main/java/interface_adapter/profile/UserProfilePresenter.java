package interface_adapter.profile;

import use_case.profile.UserProfileOutputBoundary;
import use_case.profile.UserProfileOutputData;

/**
 * The Presenter for the User Profile Use Case.
 */
public class UserProfilePresenter implements UserProfileOutputBoundary {

    private final UserProfileViewModel userProfileViewModel;

    public UserProfilePresenter(UserProfileViewModel userProfileViewModel) {
        this.userProfileViewModel = userProfileViewModel;
    }

    @Override
    public void presentProfileSaved(UserProfileOutputData outputData) {
        // Create a new state with updated information
        UserProfileState userProfileState = userProfileViewModel.getState();
        userProfileState.setUsername(outputData.getUsername());
        userProfileState.setUserId(outputData.getUserId());
        userProfileState.setBio(outputData.getBio());
        userProfileState.setDateOfBirth(outputData.getDateOfBirth());
        userProfileState.setSuccessMessage(outputData.getMessage());
        userProfileState.setError(null);

        userProfileViewModel.setState(userProfileState);
        userProfileViewModel.firePropertyChanged();
    }

    @Override
    public void presentProfileLoaded(UserProfileOutputData outputData) {
        // Create a new state with loaded information
        UserProfileState userProfileState = new UserProfileState();
        userProfileState.setUsername(outputData.getUsername());
        userProfileState.setUserId(outputData.getUserId());
        userProfileState.setBio(outputData.getBio());
        userProfileState.setDateOfBirth(outputData.getDateOfBirth());
        userProfileState.setError(null);
        userProfileState.setSuccessMessage(null);

        userProfileViewModel.setState(userProfileState);
        userProfileViewModel.firePropertyChanged();
    }

    @Override
    public void presentError(String errorMessage) {
        UserProfileState userProfileState = userProfileViewModel.getState();
        userProfileState.setError(errorMessage);
        userProfileState.setSuccessMessage(null);

        userProfileViewModel.setState(userProfileState);
        userProfileViewModel.firePropertyChanged();
    }
}