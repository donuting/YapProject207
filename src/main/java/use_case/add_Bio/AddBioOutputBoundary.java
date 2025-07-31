package use_case.add_Bio;

/**
 * The output boundary for the Add Bio Use Case.
 */
public interface AddBioOutputBoundary {

    /**
     * Prepares the success view for the Add Bio Use Case.
     * @param addBioOutputData the output data.
     */
    void prepareSuccessAddBioView(AddBioOutputData addBioOutputData);

    /**
     * Prepares the fail view for the add Bio Use Case.
     * @param addBioOutputData the output data.
     * @param errorMessage The error message to be shown.
     */
    void prepareFailAddBioView(String errorMessage, AddBioOutputData addBioOutputData);
}
