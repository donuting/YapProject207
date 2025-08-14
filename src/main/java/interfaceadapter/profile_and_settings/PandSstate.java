package interfaceadapter.profile_and_settings;

/**
 * The state for the Main Menu View Model.
 */
public class PandSstate {
    private String userId = "";
    private String changePasswordText = "";
    private String addBioText = "";
    private String addDobText = "";
    private String username = "";

    public PandSstate() {
        this.userId = "";
        this.changePasswordText = "";
        this.addBioText = "";
        this.addDobText = "";
        this.username = "";
    }

    public PandSstate(PandSstate pandSstate) {
        userId = pandSstate.userId;
        changePasswordText = pandSstate.changePasswordText;
        addBioText = pandSstate.addBioText;
        addDobText = pandSstate.addDobText;
        username = pandSstate.username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getChangePasswordText() {
        return changePasswordText;
    }

    public void setChangePasswordText(String changePasswordText) {
        this.changePasswordText = changePasswordText;
    }

    public String getAddBioText() {
        return addBioText;
    }

    public void setAddBioText(String addBioText) {
        this.addBioText = addBioText;
    }

    public String getAddDobText() {
        return addDobText;
    }

    public void setAddDobText(String addDobText) {
        this.addDobText = addDobText;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
