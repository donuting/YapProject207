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
        final User user = userFactory.create(addBioInputData.getUsername(),
                                            addBioInputData.getPassword());
        String bio = user.getBio();
        user.EditBiography(addBioInputData.getBio());
        boolean result = userDataAccessObject.addBio(user);

        if (result) {
            final AddBioOutputData changeBioOutputData = new AddBioOutputData(user.getName(), false, addBioInputData.getBio());
            userPresenter.prepareSuccessAddBioView(changeBioOutputData);
        }
        else {
            final AddBioOutputData changeBioOutputData = new AddBioOutputData(user.getName(), true, bio);
            userPresenter.prepareFailAddBioView("Add Bio Failed", changeBioOutputData);
        }


    }
}
