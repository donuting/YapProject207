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

   public String getOldDOB() {return oldDOB;}

    public String getNewDOB() {
        return newDOB;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

