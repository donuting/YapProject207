package use_case.add_DOB;

import entity.User;
import entity.UserFactory;

/**
 * The Add DOB Interactor.
 */
public class AddDOBInteractor implements AddDOBInputBoundary {
    private final AddDOBUserDataAccessInterface userDataAccessObject;
    private final AddDOBOutputBoundary userPresenter;

    public AddDOBInteractor(AddDOBUserDataAccessInterface userDataAccessObject, AddDOBOutputBoundary userPresenter
    ) {
        this.userDataAccessObject = userDataAccessObject;
        this.userPresenter = userPresenter;
    }


    @Override
    public void execute(AddDOBInputData addDOBInputData) {
        String dob = addDOBInputData.getNewDOB();
        String username = addDOBInputData.getUsername();
        User user = userDataAccessObject.get(username);
        AddDOBOutputData addDOBOutputData = new AddDOBOutputData(username, true, user.getDOB());

        if (dob.length()!=8) {
            userPresenter.prepareFailAddDOBView("The input should be in the format YYYYMMDD", addDOBOutputData);
            return;
        } else if(hasNonDigits(dob)){
            userPresenter.prepareFailAddDOBView("The input should only contain numbers", addDOBOutputData);
            return;
        }
        else{
            userDataAccessObject.addDOB(username, dob);
            addDOBOutputData = new AddDOBOutputData(username, false, dob);
            userPresenter.prepareSuccessAddDOBView(addDOBOutputData);
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
