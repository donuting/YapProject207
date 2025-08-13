package usecase.add_DOB;

/**
 * Output Data for the Add DOB Use Case.
 */
public class AddDobOutputData {
    private final String username;
    private final boolean useCaseFailed;
    private final String dob;

    public AddDobOutputData(String username, boolean useCaseFailed, String dob) {
        this.username = username;
        this.useCaseFailed = useCaseFailed;
        this.dob = dob;
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
    public boolean getUseCaseFailed() {
        return useCaseFailed;
    }

    /**
     * Gets the date of birth outputted by the interactor.
     * @return the date of birth.
     */
    public String getDob() {
        return dob;
    }
}
