package worldofzuulfx.Quest;

import java.util.ArrayList;
import worldofzuulfx.Events.ItemDeliveredEvent;
import worldofzuulfx.Events.ItemPickupEvent;
import worldofzuulfx.Events.ItemReceivedEvent;
import worldofzuulfx.Events.ItemUseEvent;
import worldofzuulfx.Events.NavigateEvent;
import worldofzuulfx.Interfaces.ItemPickupListener;
import worldofzuulfx.Player;
import worldofzuulfx.Interfaces.NavigateListener;
import worldofzuulfx.Interfaces.ItemDeliveredListener;
import worldofzuulfx.Interfaces.ItemReceivedListener;
import worldofzuulfx.Interfaces.ItemUseListener;


public class QuestHandler implements NavigateListener, ItemPickupListener, ItemDeliveredListener, ItemUseListener {
    
    private final Player player;
    
    public QuestHandler(Player p) {
        this.player = p;
        this.player.addNavigateListener(this);
        this.player.addItemPickupListener(this);
        this.player.addItemDeliveredListener(this);
        this.player.addItemUseListener(this);
    }
   
    @Override
    public void navigated(NavigateEvent navEvent) {
        this.completeQuest();
    }

    @Override
    public void itemPickedUp(ItemPickupEvent pickupEvent) {
        this.completeQuest();
    }
    
    @Override
    public void itemDelivered(ItemDeliveredEvent event) {
        this.completeQuest();
    }
    
    @Override
    public void itemUsed(ItemUseEvent event) {
        this.completeQuest();
    }

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

                } catch (QuestNotCompletedException ex) {
                    //TODO: handle the exception
                }
            }            
        }
    }   
}
