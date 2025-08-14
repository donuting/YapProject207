package usecase.add_DOB;

/**
 * The input data for the Add DOB Use Case.
 */
public class AddDobInputData {
    private final String newDOB;
    private final String oldDOB;
    private final String username;
    private final String password;

    public AddDobInputData(String oldDOB, String newDOB, String username, String password) {
        this.oldDOB = oldDOB;
        this.newDOB = newDOB;
        this.username = username;
        this.password = password;
    }

    /**
     * Gets the original date of birth.
     *
     * @return the original dob.
     */
    public String getOldDOB() {
        return oldDOB;
    }

    /**
     * Gets the inputted date of birth.
     *
     * @return the inputted dob.
     */
    public String getNewDOB() {
        return newDOB;
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


