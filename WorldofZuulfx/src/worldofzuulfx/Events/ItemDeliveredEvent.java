package worldofzuulfx.Events;

import worldofzuulfx.Items.Item;
import worldofzuulfx.NPC.NPC;
import worldofzuulfx.Player;

public class ItemDeliveredEvent {
    
    private final Player sender;
    private final NPC receiver;
    private final Item item;
    
    /**
     * An event containing information about the delivery of an item
     * @param sender The Player who delivered the item
     * @param receiver The NPC who received the item
     * @param item The item that was delivered
     */
    public ItemDeliveredEvent(Player sender, NPC receiver, Item item) {
        this.sender = sender;
        this.receiver = receiver;
        this.item = item;
    }
}
