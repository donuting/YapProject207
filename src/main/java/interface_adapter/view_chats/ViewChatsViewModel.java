package interface_adapter.view_chats;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * The View Model for the View Chats View.
 */
public class ViewChatsViewModel {
    public static final String TITLE_LABEL = "View Chats";

    private ViewChatsState state = new ViewChatsState();
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public void setState(ViewChatsState state) {
        this.state = state;
    }

    public ViewChatsState getState() {
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

