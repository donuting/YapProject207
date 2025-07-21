package use_case.add_DOB;

import entity.User;
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
        final User user = userFactory.create(addDOBInputData.getUsername(),
                                            addDOBInputData.getPassword());
        String dob = user.getDOB();
        user.EditDOB(addDOBInputData.getDOB());
        boolean result = userDataAccessObject.addDOB(user);

        if (result) {
            final AddDOBOutputData addDOBOutputData = new AddDOBOutputData(user.getName(), false, addDOBInputData.getDOB());
            userPresenter.prepareSuccessAddDOBView(addDOBOutputData);
        }
        else {
            final AddDOBOutputData addDOBOutputData = new AddDOBOutputData(user.getName(), true, dob);
            userPresenter.prepareFailAddDOBView("Failed Adding DOB", addDOBOutputData);
        }


    }
}
