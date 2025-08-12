package usecase.signup;

/**
 * The Input Data for the Signup Use Case.
 */
public class SignupInputData {

    private final String username;
    private final String password;
    private final String repeatPassword;

    public SignupInputData(String username, String password, String repeatPassword) {
        this.username = username;
        this.password = password;
        this.repeatPassword = repeatPassword;
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

    /**
     * Gets the inputted repeat password.
     *
     * @return the inputted repeat password
     */
    public String getRepeatPassword() {
        return repeatPassword;
    }
}
