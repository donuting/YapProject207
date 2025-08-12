package usecase.logout;

/**
 * Output Data for the Logout Use Case.
 */
public class LogoutOutputData {

    private String username;
    private boolean useCaseFailed;

    public LogoutOutputData(String username, boolean useCaseFailed) {
        // TO DO: save the parameters in the instance variables.
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
     * Return whether the use case failed.
     * @return true if unsuccessful
     */
    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}
