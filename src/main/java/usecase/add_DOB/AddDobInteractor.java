package usecase.add_DOB;

import entity.User;
import entity.UserFactory;

/**
 * The Add DOB Interactor.
 */
public class AddDobInteractor implements AddDobInputBoundary {
    public static final int MINIMUM_DOB_LENGTH = 8;
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
        User user = userDataAccessObject.get(username);
        AddDobOutputData addDobOutputData = new AddDobOutputData(username, true, user.getDOB());

        if (dob.length() != MINIMUM_DOB_LENGTH) {
            userPresenter.prepareFailAddDobView("The input should be in the format YYYYMMDD", addDobOutputData);
        }
        else if (hasNonDigits(dob)) {
            userPresenter.prepareFailAddDobView("The input should only contain numbers", addDobOutputData);
        }
        else {
            userDataAccessObject.addDob(username, dob);
            addDobOutputData = new AddDobOutputData(username, false, dob);
            userPresenter.prepareSuccessAddDobView(addDobOutputData);
        }

    }

    private boolean hasNonDigits(String dob) {
        for (int i = 0; i < dob.length(); i++) {
            if (!Character.isDigit(dob.charAt(i))) {
                return true;
            }
        }
        return false;
    }
}
