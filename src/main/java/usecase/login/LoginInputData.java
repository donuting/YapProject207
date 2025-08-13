package usecase.login;

/**
 * The Input Data for the Login Use Case.
 */
public class LoginInputData {

    private final String username;
    private final String password;

    public LoginInputData(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Gets the inputted username.
     *
     * @return the inputted username
     */
    String getUsername() {
        return username;
    }

    /**
     * Gets the inputted password.
     *
     * @return the inputted password
     */
    String getPassword() {
        return password;
    }

}
