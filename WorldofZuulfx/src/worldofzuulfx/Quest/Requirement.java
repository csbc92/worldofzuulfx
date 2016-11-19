package worldofzuulfx.Quest;

/**
 * This interface represents a Requirement for a Quest.
 * The interface contains only one method 'execute', that executes and returns
 * true or false depending on whether the Requirement is met.
 */
public interface Requirement {
    
    public Boolean execute();
}
