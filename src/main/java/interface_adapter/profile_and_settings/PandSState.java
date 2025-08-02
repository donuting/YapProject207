package interface_adapter.profile_and_settings;

/**
 * The state for the Main Menu View Model.
 */
public class PandSState {
    private String UID = "";
    private String ChangePasswordText = "";
    private String AddBioText = "";
    private String AddDOBText = "";
    private String username = "";

    public PandSState(){
        this.UID = "";
        this.ChangePasswordText = "";
        this.AddBioText = "";
        this.AddDOBText = "";
        this.username = "";
    }

    public PandSState(PandSState pandSState){
        UID = pandSState.UID;
        ChangePasswordText = pandSState.ChangePasswordText;
        AddBioText = pandSState.AddBioText;
        AddDOBText = pandSState.AddDOBText;
        username = pandSState.username;
    }


    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getChangePasswordText() {
        return ChangePasswordText;
    }

    public void setChangePasswordText(String changePasswordText) {
        ChangePasswordText = changePasswordText;
    }

    public String getAddBioText() {
        return AddBioText;
    }

    public void setAddBioText(String addBioText) {
        AddBioText = addBioText;
    }

    public String getAddDOBText() {
        return AddDOBText;
    }

    public void setAddDOBText(String addDOBText) {
        AddDOBText = addDOBText;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
