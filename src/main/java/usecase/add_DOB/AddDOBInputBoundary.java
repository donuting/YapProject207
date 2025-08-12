package usecase.add_DOB;

/**
 * The Add DOB Use Case.
 */
public interface AddDOBInputBoundary {

    /**
     * Execute the Add DOB Use Case.
     * @param addDOBInputData the input data for this use case
     */
    void execute(AddDOBInputData addDOBInputData);
}
