package interface_adapter.profile_and_settings;

/*
* The state for the Profile and setting View Model.
 */
public class PandSState {
    private String UID = "";
    private String ChangePasswordText = "";
    private String ChangeBioText = "";
    private Integer ChangeDOBText = 0;

    public PandSState(){
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

    public String getChangeBioText() {
        return ChangeBioText;
    }

    public void setChangeBioText(String changeBioText) {
        ChangeBioText = changeBioText;
    }

    public Integer getChangeDOBText() {
        return ChangeDOBText;
    }

    public void setChangeDOBText(Integer changeDOBText) {
        ChangeDOBText = changeDOBText;
    }
}
