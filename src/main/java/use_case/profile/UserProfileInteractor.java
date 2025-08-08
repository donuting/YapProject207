package use_case.profile;

/**
 * The User Profile Interactor.
 */
public class UserProfileInteractor implements UserProfileInputBoundary {
    private final UserProfileDataAccessInterface userDataAccessObject;
    private final UserProfileOutputBoundary userProfilePresenter;
    private String currentUserId;

    public UserProfileInteractor(UserProfileDataAccessInterface userDataAccessInterface,
                                 UserProfileOutputBoundary userProfileOutputBoundary) {
        this.userDataAccessObject = userDataAccessInterface;
        this.userProfilePresenter = userProfileOutputBoundary;
    }

    public void setCurrentUserId(String userId) {
        this.currentUserId = userId;
    }

    @Override
    public void saveProfile(UserProfileInputData userProfileInputData) {
        try {
            if (currentUserId == null) {
                userProfilePresenter.presentError("No user logged in");
                return;
            }

            // Validate input
            if (userProfileInputData.getUsername() == null || userProfileInputData.getUsername().trim().isEmpty()) {
                userProfilePresenter.presentError("Username cannot be empty");
                return;
            }

            // Update user profile
            userDataAccessObject.updateUsername(currentUserId, userProfileInputData.getUsername().trim());
            userDataAccessObject.updateBio(currentUserId, userProfileInputData.getBio());
            userDataAccessObject.updateDateOfBirth(currentUserId, userProfileInputData.getDateOfBirth());

            // Create output data
            UserProfileOutputData outputData = new UserProfileOutputData(
                    userProfileInputData.getUsername().trim(),
                    currentUserId,
                    userProfileInputData.getBio(),
                    userProfileInputData.getDateOfBirth(),
                    true,
                    "Profile updated successfully"
            );

            userProfilePresenter.presentProfileSaved(outputData);

        } catch (Exception e) {
            userProfilePresenter.presentError("Failed to save profile: " + e.getMessage());
        }
    }

    @Override
    public void loadProfile(String userId) {
        try {
            this.currentUserId = userId;

            // Load user profile data
            String username = userDataAccessObject.getUsername(userId);
            String bio = userDataAccessObject.getBio(userId);
            String dateOfBirth = userDataAccessObject.getDateOfBirth(userId);

            UserProfileOutputData outputData = new UserProfileOutputData(
                    username,
                    userId,
                    bio,
                    dateOfBirth,
                    true,
                    "Profile loaded successfully"
            );

            userProfilePresenter.presentProfileLoaded(outputData);

        } catch (Exception e) {
            userProfilePresenter.presentError("Failed to load profile: " + e.getMessage());
        }
    }
}
