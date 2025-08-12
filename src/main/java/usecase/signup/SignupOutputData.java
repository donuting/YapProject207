package usecase.signup;

/**
 * Output Data for the Signup Use Case.
 */
public class SignupOutputData {

    private final String username;

    private final boolean useCaseFailed;

    public SignupOutputData(String username, boolean useCaseFailed) {
        this.username = username;
        this.useCaseFailed = useCaseFailed;
    }

    /**
     * Gets the username outputted by the interactor.
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns whether the use case failed.
     * @return true if unsuccessful
     */
    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}
