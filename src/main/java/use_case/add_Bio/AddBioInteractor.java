package use_case.add_Bio;

import entity.User;
import entity.UserFactory;

/**
 * The Add Bio Interactor.
 */
public class AddBioInteractor implements AddBioInputBoundary{
    private final AddBioUserDataAccessInterface userDataAccessObject;
    private final AddBioOutputBoundary userPresenter;
    private final UserFactory userFactory;

    public AddBioInteractor(AddBioUserDataAccessInterface userDataAccessObject, AddBioOutputBoundary userPresenter
                            ,UserFactory userFactory) {
        this.userDataAccessObject = userDataAccessObject;
        this.userPresenter = userPresenter;
        this.userFactory = userFactory;
    }


    @Override
    public void execute(AddBioInputData addBioInputData) {
        String bio = addBioInputData.getBio();
        String username = addBioInputData.getUsername();
        boolean result = userDataAccessObject.addBio(username, bio);

        if (result) {
            final AddBioOutputData changeBioOutputData = new AddBioOutputData(username, false, bio);
            userPresenter.prepareSuccessAddBioView(changeBioOutputData);
        }
        else {
            final AddBioOutputData changeBioOutputData = new AddBioOutputData(username, true, bio);
            userPresenter.prepareFailAddBioView("Add Bio Failed", changeBioOutputData);
        }


    }
}
