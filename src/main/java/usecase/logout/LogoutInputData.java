package usecase.logout;

/**
 * The Input Data for the Logout Use Case.
 */
public class LogoutInputData {
    private String username;

    public LogoutInputData(String username) {
        this.username = username;
        // save the current username in an instance variable and add a getter.
    }

    /**
     * Gets the inputted username.
     *
     * @return the inputted username
     */
    public String getUsername() {
        return username;
    }
}
