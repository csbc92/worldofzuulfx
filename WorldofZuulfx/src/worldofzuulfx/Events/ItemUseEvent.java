
package worldofzuulfx.Events;

import worldofzuulfx.Items.Item;
import worldofzuulfx.Player;

public class ItemUseEvent {
    private final Item item;
    private final Player player;
    
    public ItemUseEvent(Item item, Player player){
        this.item = item;
        this.player = player;
    }
    
    public Item getItem(){
        return this.item;
    }
    
    public Player getPlayer(){
        return this.player;
    }
}
