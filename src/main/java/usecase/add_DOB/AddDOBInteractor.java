package usecase.add_DOB;

import entity.UserFactory;

/**
 * The Add DOB Interactor.
 */
public class AddDOBInteractor implements AddDOBInputBoundary {
    private final AddDOBUserDataAccessInterface userDataAccessObject;
    private final AddDOBOutputBoundary userPresenter;
    private final UserFactory userFactory;

    public AddDOBInteractor(AddDOBUserDataAccessInterface userDataAccessObject, AddDOBOutputBoundary userPresenter
                            , UserFactory userFactory) {
        this.userDataAccessObject = userDataAccessObject;
        this.userPresenter = userPresenter;
        this.userFactory = userFactory;
    }


    @Override
    public void execute(AddDOBInputData addDOBInputData) {
        String dob = addDOBInputData.getDOB();
        String username = addDOBInputData.getUsername();
        boolean result = userDataAccessObject.addDOB(username, dob);

        if (result) {
            final AddDOBOutputData addDOBOutputData = new AddDOBOutputData(username, false, dob);
            userPresenter.prepareSuccessAddDOBView(addDOBOutputData);
        }
        else {
            final AddDOBOutputData addDOBOutputData = new AddDOBOutputData(username, true, dob);
            userPresenter.prepareFailAddDOBView("Failed Adding DOB", addDOBOutputData);
        }


    }
}
