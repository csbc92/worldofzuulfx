package worldofzuulfx.Quest;

/**
 * Thrown to indicate that an attempt to complete a quest has been made,
 * but not all requirements for the quest has been completed.
 */
public class QuestNotCompletedException extends Exception {
    
    public QuestNotCompletedException(String message) {
        super(message);
    }
}
