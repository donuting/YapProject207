package usecase.add_DOB;

/**
 * The output boundary for the Add DOB Use Case.
 */
public interface AddDobOutputBoundary {

    /**
     * Prepares the success view for the Add DOB Use Case.
     * @param addDOBOutputData the output data.
     */
    void prepareSuccessAddDOBView(AddDobOutputData addDOBOutputData);

    /**
     * Prepares the fail view for the add DOB Use Case.
     * @param addDOBOutputData the output data.
     * @param errorMessage The error message to be shown.
     */
    void prepareFailAddDOBView(String errorMessage, AddDobOutputData addDOBOutputData);
}
