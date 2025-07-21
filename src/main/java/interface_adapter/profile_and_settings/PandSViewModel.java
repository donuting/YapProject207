package interface_adapter.profile_and_settings;

import interface_adapter.ViewModel;

/**
 * The View Model for the Profile and settings View.
 */
public class PandSViewModel extends ViewModel<PandSState> {

    public PandSViewModel() {
        super("Profile And Settings");
        setState(new PandSState());
    }
}
