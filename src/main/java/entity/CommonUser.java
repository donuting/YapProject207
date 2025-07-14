package entity;

/**
 * A simple implementation of the User interface.
 */
public class CommonUser implements User {

    private final String name;
    private final String password;
    private final Integer ID;
    // ID between one million and 1 thousand. figure out how to not override later

    public CommonUser(String name, String password) {
        this.name = name;
        this.password = password;
        this.ID = (int)(Math.random() * (1000000 - 1000 + 1) + 1000);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Integer getID() {
        return ID;
    }

}
