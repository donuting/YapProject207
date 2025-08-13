package usecase.profile;

/**
 * Output Boundary for the User Profile Use Case.
 */
public interface UserProfileOutputBoundary {
    /**
     * Prepares the success view for the User Profile Use Case.
     * @param outputData the output data
     */
    void presentProfileSaved(UserProfileOutputData outputData);

    /**
     * Prepares the success view for the User Profile Load Use Case.
     * @param outputData the output data
     */
    void presentProfileLoaded(UserProfileOutputData outputData);

    /**
     * Prepares the failure view for the User Profile Use Case.
     * @param errorMessage the explanation of the failure
     */
    void presentError(String errorMessage);
}
