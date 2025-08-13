package usecase.add_DOB;

/**
 * The input data for the Add DOB Use Case.
 */
public class AddDobInputData {
    private final String dateOfBirth;
    private final String username;
    private final String password;

    public AddDobInputData(String dateOfBirth, String username, String password) {
        this.dateOfBirth = dateOfBirth;
        this.username = username;
        this.password = password;
    }

    /**
     * Gets the inputted date of birth.
     *
     * @return the inputted username.
     */
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Gets the inputted username.
     *
     * @return the inputted username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the inputted password.
     *
     * @return the inputted password.
     */
    public String getPassword() {
        return password;
    }
}

