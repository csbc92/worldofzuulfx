package worldofzuulfx.Events;

import worldofzuulfx.Items.Item;
import worldofzuulfx.NPC.NPC;
import worldofzuulfx.Player;

public class ItemDeliveredEvent {
    
    private final Player sender;
    private final NPC receiver;
    private final Item item;
    
    public ItemDeliveredEvent(Player sender, NPC receiver, Item item) {
        this.sender = sender;
        this.receiver = receiver;
        this.item = item;
    }
}
