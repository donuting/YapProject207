package usecase.add_DOB;

import entity.User;

/**
 * The Add DOB Interactor.
 */
public class AddDobInteractor implements AddDobInputBoundary {
    private final AddDobUserDataAccessInterface userDataAccessObject;
    private final AddDobOutputBoundary userPresenter;

    public AddDobInteractor(AddDobUserDataAccessInterface userDataAccessObject, AddDobOutputBoundary userPresenter
    ) {
        this.userDataAccessObject = userDataAccessObject;
        this.userPresenter = userPresenter;
    }


    @Override
    public void execute(AddDobInputData addDOBInputData) {
        String dob = addDOBInputData.getNewDOB();
        String username = addDOBInputData.getUsername();
        User user = userDataAccessObject.get(username);
        AddDobOutputData addDOBOutputData = new AddDobOutputData(username, true, user.getDOB());

        if (dob.length()!=8) {
            userPresenter.prepareFailAddDOBView("The input should be in the format YYYYMMDD", addDOBOutputData);
            return;
        } else if(hasNonDigits(dob)){
            userPresenter.prepareFailAddDOBView("The input should only contain numbers", addDOBOutputData);
            return;
        }
        else{
            userDataAccessObject.addDOB(username, dob);
            addDOBOutputData = new AddDobOutputData(username, false, dob);
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
