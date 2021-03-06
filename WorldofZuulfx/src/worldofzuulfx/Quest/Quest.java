package worldofzuulfx.Quest;

/**
 * A Quest describes something that has to be completed, which in turn gives a
 * Reward.
 *
 * There is 2 types of quests:
 *
 * 1. A Dynamic quest where the Requirement has to be checked each time a call
 * is made to the complete() method.
 *
 * 2. A Static quest where the Requirement has to be completed just once, thus
 * setting a flag, that indicates that the Quest is completed.
 *
 * An example for creating a STATIC Quest:
 *
 * Item rewardItem = null; int ectsPoints = 10; Reward questReward = new
 * Reward(rewardItem, ectsPoints);
 *
 * Quest roomQuest = new Quest(questDescription, questReward, Questtype.STATIC);
 *
 * roomQuest.setRequirement(() -> { String currentRoomID =
 * player.getCurrentRoom().getID();
 *
 *	// Test if the player is in the examn-room. if (currentRoomID == roomID) {
 * return true; } else { return false; }
 *
 * });
 *
 *
 * An example for creating a DYNAMIC Quest:
 *
 *  * Item rewardItem = null; int ectsPoints = 10; Reward questReward = new
 * Reward(rewardItem, ectsPoints);
 *
 * Quest pickupCoffeeQuest = new Quest("description", questReward,
 * Questtype.DYNAMIC);
 *
 * pickupCoffeeQuest.setRequirement(() -> { Inventory inventory =
 * player.getInventory();
 *
 * if (inventory.contains(item)) { return true; } else { return false; }
 *
 * });
 *
 */
public class Quest {

    private String id;
    private final String description;
    private final Reward reward;
    private final QuestType questType;
    private Boolean isCompleted;
    private Boolean requirementOK;
    private Quest chainQuest;
    private Requirement requirement;
    private Action postAction;

    public Quest(String id, String description, Reward reward, QuestType type) {
        this.id = id;
        this.description = description;
        this.reward = reward;
        this.questType = type;
        this.isCompleted = false;
        this.requirementOK = false;
    }

    /**
     * Gets the description of the quest.
     *
     * @return Returns a String containing the description.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Checks if the Quest is completed.
     *
     * @return True if the Quest has been completed otherwise false.
     */
    public Boolean checkRequirement() {

        switch (this.questType) {

            case DYNAMIC:
                return this.requirement.execute();

            case STATIC:
                // Check if the quests requirement is OK.
                // aka don't check the requirement twice
                if (this.requirementOK) {
                    // Quest already completed
                    return true;
                }

                // Check the requirement
                if (this.requirement.execute()) {
                    this.requirementOK = true;
                    return true;
                } else {
                    return false;
                }

            default:
                return false;
        }
    }

    /**
     * Complete the quest and get the Reward for the quest.
     *
     * @return Returns the Reward for the quest.
     * @throws QuestRequirementException Throws a QuestNotCompletedException if
     * the quest has not set its status to Completed. You cannot get a reward
     * for a quest that is not completed.
     */
    public Reward complete() throws QuestRequirementException {

        switch (this.questType) {

            case DYNAMIC:
                if (this.requirement.execute()) {
                    this.isCompleted = true;
                    return this.reward;
                } else {
                    throw new QuestRequirementException("The quest's requirement is not complied.");
                }

            case STATIC:
                if (this.requirementOK) {
                    this.isCompleted = true;
                    return this.reward;
                } else {
                    throw new QuestRequirementException("The quest's requirement is not complied.");
                }

            default:
                return null;
        }
    }

    /**
     *
     * @return True if the Quest is completed otherwise false.
     */
    public Boolean isCompleted() {
        return this.isCompleted;
    }

    /**
     * The requirement which must be satisfied before the Quest is completed.
     *
     * @param req An class implementing the Requirement interface, or a lambda
     * expression. See javadoc for the Quest class.
     */
    public void setRequirement(Requirement req) {
        this.requirement = req;
    }

    /**
     * An action that can be executed after the requirement is satisfied.
     *
     * @param action A class implementing the PostAction interface, or a lambda
     * expression.
     */
    public void setPostAction(Action action) {
        this.postAction = action;
    }

    /**
     * Executes the Post action
     */
    public void executePostAction() {
        if (this.postAction != null) {
            this.postAction.execute();
        }
    }

    /**
     * Chain this Quest to another quest.
     * @param quest The quest to be chained
     */
    public void setChainQuest(Quest quest) {
        this.chainQuest = quest;
    }

    /**
     *
     * @return The chained quest
     */
    public Quest getChainQuest() {
        return this.chainQuest;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     *  * There is 2 types of quests:
     *
     * 1. A Dynamic quest where the Requirement has to be checked each time a
     * call is made to the complete() method.
     *
     * 2. A Static quest where the Requirement has to be completed just once,
     * thus setting a flag, that indicates that the requirement is OK.
     */
    public enum QuestType {
        DYNAMIC, STATIC
    }
}
