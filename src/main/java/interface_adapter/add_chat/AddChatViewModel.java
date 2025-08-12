package interface_adapter.add_chat;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import interface_adapter.ViewModel;

/**
 * The View Model for the Add Chat View.
 */
public class AddChatViewModel extends ViewModel<AddChatState> {
    public static final String TITLE_LABEL = "Add Chat";

    private AddChatState state = new AddChatState();
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public AddChatViewModel() {
        super(TITLE_LABEL);
        setState(new AddChatState());
    }

    public void setState(AddChatState state) {
        this.state = state;
    }

    public AddChatState getState() {
        return state;
    }

    /**
     * Description.
     * @param listener The PropertyChangeListener to be added
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
