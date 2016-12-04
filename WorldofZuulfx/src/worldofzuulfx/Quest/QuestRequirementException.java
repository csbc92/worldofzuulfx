package worldofzuulfx.Quest;

/**
 * Thrown to indicate that an attempt to complete a quest has been made,
 * but the requirement for the quest has not been complied.
 */
public class QuestRequirementException extends Exception {
    
    public QuestRequirementException(String message) {
        super(message);
    }
}
