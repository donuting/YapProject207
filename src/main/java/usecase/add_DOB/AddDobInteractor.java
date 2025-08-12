package usecase.add_DOB;

import entity.UserFactory;

/**
 * The Add DOB Interactor.
 */
public class AddDobInteractor implements AddDobInputBoundary {
    private final AddDobUserDataAccessInterface userDataAccessObject;
    private final AddDobOutputBoundary userPresenter;
    private final UserFactory userFactory;

    public AddDobInteractor(AddDobUserDataAccessInterface userDataAccessObject,
                            AddDobOutputBoundary userPresenter, UserFactory userFactory) {
        this.userDataAccessObject = userDataAccessObject;
        this.userPresenter = userPresenter;
        this.userFactory = userFactory;
    }

    @Override
    public void execute(AddDobInputData addDobInputData) {
        String dob = addDobInputData.getDateOfBirth();
        String username = addDobInputData.getUsername();
        boolean result = userDataAccessObject.addDob(username, dob);

        if (result) {
            final AddDobOutputData addDobOutputData = new AddDobOutputData(username, false, dob);
            userPresenter.prepareSuccessAddDobView(addDobOutputData);
        }
        else {
            final AddDobOutputData addDobOutputData = new AddDobOutputData(username, true, dob);
            userPresenter.prepareFailAddDobView("Failed Adding DOB", addDobOutputData);
        }
    }
}
