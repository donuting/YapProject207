package interfaceadapter.profile_and_settings;

import usecase.add_Bio.AddBioOutputBoundary;
import usecase.add_Bio.AddBioOutputData;
import usecase.add_DOB.AddDobOutputBoundary;
import usecase.add_DOB.AddDobOutputData;
import usecase.change_password.ChangePasswordOutputBoundary;
import usecase.change_password.ChangePasswordOutputData;
import usecase.delete_account.DeleteAccountOutputBoundary;
import usecase.delete_account.DeleteAccountOutputData;


/**
 * The Presenter presents the Profile and settings view.
 * The Presenter for the Change Password Use Case.
 * The Presenter for the Add DOB Use Case.
 * The Presenter for the Add Bio Use Case.
 */
public class PandSpresenter implements ChangePasswordOutputBoundary,
        AddDobOutputBoundary,
        AddBioOutputBoundary,
        DeleteAccountOutputBoundary {
    private static final String PROFILE_AND_SETTINGS = "Profile And Settings";
    private final PandSviewModel pandSviewModel;

    public PandSpresenter(PandSviewModel pandSviewModel) {
        this.pandSviewModel = pandSviewModel;
    }

    @Override
    public void prepareSuccessAddBioView(AddBioOutputData addBioOutputData) {
        final PandSstate pandSstate = new PandSstate(pandSviewModel.getState());
        pandSstate.setAddBioText(addBioOutputData.getBio());
        pandSviewModel.setState(pandSstate);
        pandSviewModel.firePropertyChanged(PROFILE_AND_SETTINGS);

    }

    @Override
    public void prepareSuccessAddDobView(AddDobOutputData addDobOutputData) {
        final PandSstate pandSstate = new PandSstate(pandSviewModel.getState());
        pandSstate.setAddDobText(addDobOutputData.getDob());
        pandSviewModel.setState(pandSstate);
        pandSviewModel.firePropertyChanged(PROFILE_AND_SETTINGS);
    }

    @Override
    public void prepareSuccessChangePasswordView(ChangePasswordOutputData outputData) {
        final PandSstate pandSstate = new PandSstate(pandSviewModel.getState());
        pandSstate.setChangePasswordText(outputData.getPassword());
        pandSviewModel.setState(pandSstate);
        pandSviewModel.firePropertyChanged(PROFILE_AND_SETTINGS);
    }

    @Override
    public void prepareFailAddBioView(String errorMessage, AddBioOutputData addBioOutputData) {
        final PandSstate pandSstate = new PandSstate(pandSviewModel.getState());
        pandSstate.setAddBioText(addBioOutputData.getBio());
        System.out.println(errorMessage);
        pandSviewModel.setState(pandSstate);
        pandSviewModel.firePropertyChanged(PROFILE_AND_SETTINGS);

    }

    @Override
    public void prepareFailAddDobView(String errorMessage, AddDobOutputData addDobOutputData) {
        final PandSstate pandSstate = new PandSstate(pandSviewModel.getState());
        pandSstate.setAddDobText(addDobOutputData.getDob());
        System.out.println(errorMessage);
        pandSviewModel.setState(pandSstate);
        pandSviewModel.firePropertyChanged(PROFILE_AND_SETTINGS);

    }

    @Override
    public void prepareFailChangePasswordView(String errorMessage) {
        final PandSstate pandSstate = new PandSstate(pandSviewModel.getState());
        // pandSstate.setChangePasswordText(outputData.getPassword());
        System.out.println(errorMessage);
        pandSviewModel.setState(pandSstate);
        pandSviewModel.firePropertyChanged(PROFILE_AND_SETTINGS);

    }

    @Override
    public void prepareSuccessDeleteAccountView(DeleteAccountOutputData deleteAccountOutputData) {
        PandSstate pandSState = new PandSstate(pandSviewModel.getState());
        pandSState.setAccountDeleted(true);
        pandSState.setDeleteAccountErrorMessage("");
        pandSviewModel.setState(pandSState);
        pandSviewModel.firePropertyChanged("Profile And Settings");
    }

    @Override
    public void prepareFailDeleteAccountView(String errorMessage, DeleteAccountOutputData deleteAccountOutputData) {
        PandSstate PandSState = new PandSstate(pandSviewModel.getState());
        PandSState.setAccountDeleted(false);
        PandSState.setDeleteAccountErrorMessage(errorMessage);
        System.out.println(errorMessage);
        pandSviewModel.setState(PandSState);
        pandSviewModel.firePropertyChanged("Profile And Settings");
    }

}
