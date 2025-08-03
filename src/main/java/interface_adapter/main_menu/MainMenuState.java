package interface_adapter.main_menu;

/**
 * The state for the Main Menu View Model.
 */
public class MainMenuState {
    private String username = "";
    private String error = "";
    private String password = "";
    private String UID = "";

    public MainMenuState(MainMenuState copy) {
        username = copy.username;
        error = copy.error;
        password = copy.password;
        UID = copy.UID;
    }

    // Because of the previous copy constructor, the default constructor must be explicit.
    public MainMenuState() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}

    public String getUID() {return UID;}

    public void setUID(String UID) {this.UID = UID;}
}