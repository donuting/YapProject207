package interface_adapter.profile_and_settings;

import use_case.add_Bio.AddBioOutputBoundary;
import use_case.add_Bio.AddBioOutputData;
import use_case.add_DOB.AddDOBOutputBoundary;
import use_case.add_DOB.AddDOBOutputData;
import use_case.change_password.ChangePasswordOutputBoundary;
import use_case.change_password.ChangePasswordOutputData;

/**
 * The Presenter presents the Profile and settings view.
 * The Presenter for the Change Password Use Case.
 * The Presenter for the Add DOB Use Case.
 * The Presenter for the Add Bio Use Case.
 */
public class PandSpresenter implements ChangePasswordOutputBoundary,
        AddDOBOutputBoundary,
        AddBioOutputBoundary {
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
    public void prepareSuccessAddDOBView(AddDOBOutputData addDobOutputData) {
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
    public void prepareFailAddDOBView(String errorMessage, AddDOBOutputData addDobOutputData) {
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

}
