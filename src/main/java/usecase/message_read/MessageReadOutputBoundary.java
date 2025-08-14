package usecase.message_read;

public interface MessageReadOutputBoundary {
    /**
     * Presents the output for the Message Read use case.
     * @param outputData the output data
     */
    void present(MessageReadOutputData outputData);
}
