package entity;

/**
 * Factory for creating users.
 */
public interface ChatFactory {
    /**
     * Creates a new User.
     * @param user1id the id of user1
     * @param user2id the id of user2
     * @return the new chat
     */
    Chat create(Integer user1id, Integer user2id);
}
