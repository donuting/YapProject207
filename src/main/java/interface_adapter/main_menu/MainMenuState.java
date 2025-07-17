package interface_adapter.main_menu;

/**
 * The state for the Main Menu View Model.
 */
public class MainMenuState {
    private String username = "";
    private String error = "";

    public MainMenuState(MainMenuState copy) {
        username = copy.username;
        error = copy.error;
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
}