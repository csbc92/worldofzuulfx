package worldofzuulfx.Quest;

import worldofzuulfx.Inventory.Inventory;
import worldofzuulfx.Inventory.PlayerInventory;
import worldofzuulfx.Items.Item;
import worldofzuulfx.NPC.NPC;
import worldofzuulfx.Player;
import worldofzuulfx.Quest.Quest.QuestType;

/**
 * The QuestFactory is responsible for creating new quest.
 * Different types of quests exists e.g.
 * 1. A quest where a player has to carry a special item in the players inventory.
 * 2. A quest where a player has to go to a certain room.
 * 
 * The quests can be created with different parameters, e.g. which item the player
 * has to carry or which room the player has to be in.
 */
public class QuestFactory {

    private final Player player;    // The QuestFactory needs a relationship to the player
                                    // to check the status of the player.

    
    public QuestFactory(Player p) {
        this.player = p;
    }

    /**
     * Creates a new quest that is completed when the player IS in a certain
     * room. Once the player has been into this room, the quest is forever completed,
     * even when the player exits the room.
     * @param roomID The ID of the room the player has to be in.
     * @param questDescription The description of the quest, e.g. Go to room xyz.
     * @param reward The reward of the quest.
     * @return Returns the Quest.
     */
    public Quest roomQuest(String roomID, String questDescription, Reward reward) {
        // Create the quest without any reward.
        Quest roomQuest = new Quest(questDescription, reward, QuestType.STATIC);

        roomQuest.setRequirement(() -> {
            String currentRoomID = player.getCurrentRoom().getID();
            
            // Test if the player is in the examn-room.
            if (currentRoomID.equalsIgnoreCase(roomID)) {
                return true;
            } else {
                return false;
            }
           
        });

        return roomQuest;
    }
    
    /**
     * Creates a new quest that is completed when the player carries a certain
     * Item in the player's inventory. This quest is dynamic, meaning the completed
     * status will change behavior depending on if the player is carrying the Item or not.
     * @param item The type of Item the player has to carry.
     * @param questDescription The description of the quest, e.g. Pick up a Coffee.
     * @param reward The reward of the quest.
     * @return Returns the Quest.
     */
    public Quest pickupItemQuest(String ID, String questDescription, Reward reward) {
        // Create the quest without any reward.
        Quest pickupQuest = new Quest(questDescription, reward, QuestType.DYNAMIC);

        pickupQuest.setRequirement(() -> {
            PlayerInventory inventory = player.getInventory();
            
            if (inventory.contains(ID)) {
                return true;
            } else {
                return false;
            }
           
        });

        return pickupQuest;
    }
    
    /**
     * Creates a new quest that is completed when the NPC carries a certain
     * Item in the NPC's inventory.
     * @param ID The specific Item the NPC has to carry.
     * @param npc The NPC who has to carry the item.
     * @param questDescription The description of the quest, e.g. Pick up a Coffee.
     * @param reward The reward of the quest.
     * @return Returns the Quest.
     */
    public Quest deliveryQuest(String ID, NPC npc, String questDescription, Reward reward) {
        
        Quest deliverQuest = new Quest(questDescription, reward, QuestType.STATIC);
        
        deliverQuest.setRequirement(() -> {
            // Check if NPC has the required item in inventory.
            NPC n = npc;
            Inventory inventory = n.getInventory();
            
            if (inventory.contains(ID)) {
                return true;
            } else {
                return false;
            }
        });
        
        return deliverQuest;
    }
}
