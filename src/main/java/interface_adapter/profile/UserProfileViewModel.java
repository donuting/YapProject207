package interface_adapter.profile;

import interface_adapter.ViewModel;

/**
 * The View Model for the User Profile View.
 */
public class UserProfileViewModel extends ViewModel<UserProfileState> {

    public static final String TITLE_LABEL = "User Profile";
    public static final String USERNAME_LABEL = "Username";
    public static final String USER_ID_LABEL = "User ID";
    public static final String BIO_LABEL = "Bio";
    public static final String DOB_LABEL = "Date of Birth";
    public static final String BACK_BUTTON_LABEL = "Back";
    public static final String EDIT_BUTTON_LABEL = "Edit";
    public static final String SAVE_BUTTON_LABEL = "Save";

    public UserProfileViewModel() {
        super("user profile");
        setState(new UserProfileState());
    }
}
