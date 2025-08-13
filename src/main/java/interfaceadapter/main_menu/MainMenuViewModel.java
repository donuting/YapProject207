package interfaceadapter.main_menu;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * The View Model for the Main Menu View.
 */
public class MainMenuViewModel {
    public static final String TITLE_LABEL = "Main Menu";

    private MainMenuState state = new MainMenuState();
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public void setState(MainMenuState state) {
        this.state = state;
    }

    public MainMenuState getState() {
        return state;
    }

    /**
     * Description.
     * @param listener listener
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    /**
     * Description.
     */
    public void firePropertyChanged() {
        support.firePropertyChange("state", null, this.state);
    }
}
