package interface_adapter.profile_and_settings;

import interface_adapter.ViewModel;

/**
 * The View Model for the Profile and settings View.
 */
public class PandSviewModel extends ViewModel<PandSstate> {

    public PandSviewModel() {
        super("Profile And Settings");
        setState(new PandSstate());
    }
}
