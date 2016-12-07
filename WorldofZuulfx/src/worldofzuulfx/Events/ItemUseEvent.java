
package worldofzuulfx.Events;

import worldofzuulfx.Items.Item;
import worldofzuulfx.Player;

public class ItemUseEvent {
    private final Item item;
    private final Player player;
    
    /**
     * An event containing information about the use of an item
     * @param item The item that has been used
     * @param player The Player who uses the item
     */
    public ItemUseEvent(Item item, Player player){
        this.item = item;
        this.player = player;
    }
    
    /**
     *
     * @return The item
     */
    public Item getItem(){
        return this.item;
    }
    
    /**
     *
     * @return The Player
     */
    public Player getPlayer(){
        return this.player;
    }
}
