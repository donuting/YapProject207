package usecase.create_chat;

/**
 * Input Boundary for actions which are related to creating a group chat.
 */
public interface CreateChatInputBoundary {

    /**
     * Execute the Create Chat Use Case.
     * @param createChatInputData the input data for this use case
     */
    void execute(CreateChatInputData createChatInputData);
}

