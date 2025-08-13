package usecase.add_Bio;

/**
 * Output Data for the Add Bio Use Case.
 */
public class AddBioOutputData {
    private final String username;
    private final String bio;
    private final boolean useCaseFailed;

    public AddBioOutputData(String username, boolean useCaseFailed, String bio) {
        this.username = username;
        this.useCaseFailed = useCaseFailed;
        this.bio = bio;
    }

    /**
     * Gets the username outputted by the interactor.
     * @return the username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns whether the use case failed.
     * @return true if the use case failed.
     */
    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }

    /**
     * Gets the bio outputted by the interactor.
     * @return the bio.
     */
    public String getBio() {
        return bio;
    }
}
