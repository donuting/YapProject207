package interfaceadapter.profile_and_settings;

import interfaceadapter.ViewModel;

/**
 * The View Model for the Profile and settings View.
 */
public class PandSviewModel extends ViewModel<PandSstate> {

    public PandSviewModel() {
        super("Profile And Settings");
        setState(new PandSstate());
    }
}
