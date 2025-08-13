package usecase.message_read;

public interface MessageReadInputBoundary {

    /**
     * Executes the Message Read use case.
     * @param inputData the input data
     */
    void execute(MessageReadInputData inputData);
}
