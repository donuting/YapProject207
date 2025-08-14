package usecase.add_Bio;

/**
 * The Add Bio Interactor.
 */
public class AddBioInteractor implements AddBioInputBoundary {
    private final AddBioUserDataAccessInterface userDataAccessObject;
    private final AddBioOutputBoundary userPresenter;

    public AddBioInteractor(AddBioUserDataAccessInterface userDataAccessObject, AddBioOutputBoundary userPresenter) {
        this.userDataAccessObject = userDataAccessObject;
        this.userPresenter = userPresenter;
    }

    @Override
    public void execute(AddBioInputData addBioInputData) {
        String bio = addBioInputData.getNewBio();
        String username = addBioInputData.getUsername();
        boolean result = false;
        if (!bio.isEmpty() && !bio.equals(addBioInputData.getPassword())) {
            result = userDataAccessObject.addBio(username, bio);
        }


        if (result) {
            final AddBioOutputData changeBioOutputData = new AddBioOutputData(username, false, bio);
            userPresenter.prepareSuccessAddBioView(changeBioOutputData);
        }
        else {
            final AddBioOutputData changeBioOutputData = new AddBioOutputData(username, true, addBioInputData.getOldBio());
            userPresenter.prepareFailAddBioView("Add Bio Failed", changeBioOutputData);
        }
    }
}
