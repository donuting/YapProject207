package use_case.add_Bio;

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

    public String getUsername() {
        return username;
    }
    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
    public String getBio() {
        return bio;
    }
}
