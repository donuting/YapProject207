package interface_adapter.profile_and_settings;

import use_case.add_Bio.AddBioOutputBoundary;
import use_case.add_Bio.AddBioOutputData;
import use_case.add_DOB.AddDOBOutputBoundary;
import use_case.add_DOB.AddDOBOutputData;
import use_case.change_password.ChangePasswordOutputBoundary;
import use_case.change_password.ChangePasswordOutputData;
import use_case.delete_account.DeleteAccountOutputBoundary;
import use_case.delete_account.DeleteAccountOutputData;

/**
 * The Presenter presents the Profile and settings view.
 * The Presenter for the Change Password Use Case.
 * The Presenter for the Add DOB Use Case.
 * The Presenter for the Add Bio Use Case.
 */
public class PandSPresenter implements ChangePasswordOutputBoundary,
        AddDOBOutputBoundary,
        AddBioOutputBoundary,
        DeleteAccountOutputBoundary {
    private final PandSViewModel pandSViewModel;

    public PandSPresenter(PandSViewModel pandSViewModel) {
        this.pandSViewModel = pandSViewModel;
    }

    @Override
    public void prepareSuccessAddBioView(AddBioOutputData addBioOutputData) {
        PandSState pandSState = new PandSState(pandSViewModel.getState());
        pandSState.setAddBioText(addBioOutputData.getBio());
        pandSViewModel.setState(pandSState);
        pandSViewModel.firePropertyChanged("Profile And Settings");

    }

    @Override
    public void prepareSuccessAddDOBView(AddDOBOutputData addDOBOutputData) {
        PandSState pandSState = new PandSState(pandSViewModel.getState());
        pandSState.setAddDOBText(addDOBOutputData.getDob());
        pandSViewModel.setState(pandSState);
        pandSViewModel.firePropertyChanged("Profile And Settings");
    }

    @Override
    public void prepareSuccessChangePasswordView(ChangePasswordOutputData outputData) {
        PandSState pandSState = new PandSState(pandSViewModel.getState());
        pandSState.setChangePasswordText(outputData.getPassword());
        pandSViewModel.setState(pandSState);
        pandSViewModel.firePropertyChanged("Profile And Settings");
    }

    @Override
    public void prepareFailAddBioView(String errorMessage, AddBioOutputData addBioOutputData) {
        PandSState pandSState = new PandSState(pandSViewModel.getState());
        pandSState.setAddBioText(addBioOutputData.getBio());
        System.out.println(errorMessage);
        pandSViewModel.setState(pandSState);
        pandSViewModel.firePropertyChanged("Profile And Settings");

    }

    @Override
    public void prepareFailAddDOBView(String errorMessage, AddDOBOutputData addDOBOutputData) {
        PandSState pandSState = new PandSState(pandSViewModel.getState());
        pandSState.setAddDOBText(addDOBOutputData.getDob());
        System.out.println(errorMessage);
        pandSViewModel.setState(pandSState);
        pandSViewModel.firePropertyChanged("Profile And Settings");

    }

    @Override
    public void prepareFailChangePasswordView(String errorMessage) {
        //TODO: to be discussed
        PandSState pandSState = new PandSState(pandSViewModel.getState());
        //pandSState.setChangePasswordText(outputData.getPassword());
        System.out.println(errorMessage);
        pandSViewModel.setState(pandSState);
        pandSViewModel.firePropertyChanged("Profile And Settings");

    }

    @Override
    public void prepareSuccessDeleteAccountView(DeleteAccountOutputData deleteAccountOutputData) {
        PandSState pandSState = new PandSState(pandSViewModel.getState());
        pandSState.setAccountDeleted(true);
        pandSState.setDeleteAccountErrorMessage("");
        pandSViewModel.setState(pandSState);
        pandSViewModel.firePropertyChanged("Profile And Settings");
    }

    @Override
    public void prepareFailDeleteAccountView(String errorMessage, DeleteAccountOutputData deleteAccountOutputData) {
        PandSState pandSState = new PandSState(pandSViewModel.getState());
        pandSState.setAccountDeleted(false);
        pandSState.setDeleteAccountErrorMessage(errorMessage);
        System.out.println(errorMessage);
        pandSViewModel.setState(pandSState);
        pandSViewModel.firePropertyChanged("Profile And Settings");
    }

}
