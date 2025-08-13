package usecase.profile;

import java.util.List;

/**
 * The User Profile Interactor.
 */
public class UserProfileInteractor implements UserProfileInputBoundary {
    private final UserProfileDataAccessInterface userDataAccessObject;
    private final UserProfileOutputBoundary userProfilePresenter;

    public UserProfileInteractor(UserProfileDataAccessInterface userDataAccessInterface,
                                 UserProfileOutputBoundary userProfileOutputBoundary) {
        this.userDataAccessObject = userDataAccessInterface;
        this.userProfilePresenter = userProfileOutputBoundary;
    }

    @Override
    public void saveProfile(UserProfileInputData userProfileInputData) {
        try {
            // Validate input
            if (userProfileInputData.getUsername() == null || userProfileInputData.getUsername().trim().isEmpty()) {
                userProfilePresenter.presentError("Username cannot be empty");
            }
            else {
                String oldUsername = userProfileInputData.getOldUsername().trim();
                String username = userProfileInputData.getUsername().trim();
                String bio = userProfileInputData.getBio();
                String dateOfBirth = userProfileInputData.getDateOfBirth();

                // Update user profile
                String userId = userDataAccessObject.saveProfile(oldUsername, username, bio, dateOfBirth);
                if (userId != null || !userId.trim().isEmpty()) {
                    userProfilePresenter.presentError("This user doesn't exist");
                }

                // Create output data
                UserProfileOutputData outputData = new UserProfileOutputData(
                        userProfileInputData.getUsername().trim(),
                        userId,
                        userProfileInputData.getBio(),
                        userProfileInputData.getDateOfBirth(),
                        true,
                        "Profile updated successfully"
                );

                userProfilePresenter.presentProfileSaved(outputData);
            }
        }
        catch (Exception exception) {
            userProfilePresenter.presentError("Failed to save profile: " + exception.getMessage());
        }
    }

    @Override
    public void loadProfile(String username) {
        try {
            // Load user profile data
            List<String> userData = userDataAccessObject.loadProfile(username);
            if (userData == null) {
                userProfilePresenter.presentError("This user doesn't exist");
            }
            else {

                String userId = userData.get(0);
                String bio = userData.get(1);
                String dateOfBirth = userData.get(2);

                UserProfileOutputData outputData = new UserProfileOutputData(
                        username,
                        userId,
                        bio,
                        dateOfBirth,
                        true,
                        "Profile loaded successfully"
                );

                userProfilePresenter.presentProfileLoaded(outputData);
            }
        }
        catch (Exception exception) {
            userProfilePresenter.presentError("Failed to load profile: " + exception.getMessage());
        }
    }
}
