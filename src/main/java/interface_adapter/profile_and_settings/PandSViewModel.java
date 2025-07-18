package interface_adapter.profile_and_settings;

import interface_adapter.ViewModel;

public class PandSViewModel extends ViewModel<PandSState> {

    public PandSViewModel() {
        super("Profile And Settings");
        setState(new PandSState());
    }
}
