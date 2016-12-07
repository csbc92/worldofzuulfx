package worldofzuulfx.Events;

import worldofzuulfx.Items.Item;
import worldofzuulfx.Player;

public class ItemPickupEvent {
    
    private final Item item;
    private final Player player;
    
    /**
     * An event containing information about the pickup of an item
     * @param item The item that was picked up
     * @param player The player who picked up the item
     */
    public ItemPickupEvent(Item item, Player player) {
        this.item = item;
        this.player = player;
    }
    
    /**
     * 
     * @return The Item
     */
    public Item getItem() {
        return this.item;
    }
    
    /**
     * 
     * @return The Player
     */
    public Player getPlayer() {
        return this.player;
    }
}
