package worldofzuulfx.Quest;

import worldofzuulfx.ConsoleInfo;
import worldofzuulfx.Events.ItemDeliveredEvent;
import worldofzuulfx.Events.ItemPickupEvent;
import worldofzuulfx.Events.ItemUseEvent;
import worldofzuulfx.Events.NavigateEvent;
import worldofzuulfx.Interfaces.ItemPickupListener;
import worldofzuulfx.Player;
import worldofzuulfx.Interfaces.NavigateListener;
import worldofzuulfx.Interfaces.ItemDeliveredListener;
import worldofzuulfx.Interfaces.ItemUseListener;


public class QuestHandler implements NavigateListener, ItemPickupListener, ItemDeliveredListener, ItemUseListener {
    
    private final Player player;
    
    public QuestHandler(Player p) {
        this.player = p;
        // Subscribe for events throwed by the player.
        this.player.addNavigateListener(this);
        this.player.addItemPickupListener(this);
        this.player.addItemDeliveredListener(this);
        this.player.addItemUseListener(this);
    }
   
    /**
     * Checks if the Active Quest is completed when the player navigates
     * @param navEvent An event containing information about the navigation.
     */
    @Override
    public void navigated(NavigateEvent navEvent) {
        this.completeQuest();
    }

    /**
     * Checks if the Active Quest is completed when the player picks up an item.
     * @param pickupEvent An event containing information about item which was picked up.
     */
    @Override
    public void itemPickedUp(ItemPickupEvent pickupEvent) {
        this.completeQuest();
    }
    
    /**
     * Checks if the Active Quest is completed when the player deliveres an item.
     * @param event An event containing information about the delivery of an item.
     */
    @Override
    public void itemDelivered(ItemDeliveredEvent event) {
        this.completeQuest();
    }
    
    /**
     * Checks if the Active Quest is completed when the player uses an item
     * @param event An event containing information about the use of an item.
     */
    @Override
    public void itemUsed(ItemUseEvent event) {
        this.completeQuest();
    }

    /**
     * Checks if the Active Quest is completed.
     * True: The Player is given a reward and a new Quest if there is any.
     * False: The Player still needs to complete the Active Quest.
     */
    private void completeQuest() {
        
        Quest activeQuest = this.player.getActiveQuest();

        if (activeQuest != null ) {

            if (activeQuest.checkRequirement()) {
                try {

                    // Complete the quest
                    Reward reward = activeQuest.complete();
                    this.player.giveReward(reward);

                    // Execute the post action.
                    activeQuest.executePostAction();

                    // This quest should not be processed further.
                    this.player.untrackQuest(activeQuest);

                    if (activeQuest.getChainQuest() != null) {
                        // Move chain quests to active quests
                        this.player.setActiveQuest(activeQuest.getChainQuest(), true);
                    }

                } catch (QuestRequirementException ex) {
                    ConsoleInfo.setConsoleData("The requirement to complete the quest has not been met. " 
                                                + "Quest: " + activeQuest.getDescription());
                }
            }            
        }
    }   
}
