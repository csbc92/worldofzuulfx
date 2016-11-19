package worldofzuulfx.Events;

import worldofzuulfx.Items.Item;
import worldofzuulfx.Player;

public class ItemPickupEvent {
    
    private final Item item;
    private final Player player;
    
    public ItemPickupEvent(Item item, Player player) {
        this.item = item;
        this.player = player;
    }
    
    public Item getItem() {
        return this.item;
    }
    
    public Player getPlayer() {
        return this.player;
    }
}
