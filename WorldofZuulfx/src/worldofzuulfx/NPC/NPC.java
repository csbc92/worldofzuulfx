
package worldofzuulfx.NPC;

import javafx.scene.image.Image;
import worldofzuulfx.Inventory.Inventory;
import worldofzuulfx.Items.Item;
import worldofzuulfx.Player;
import worldofzuulfx.Room.Room;
import worldofzuulfx.sprites.SpriteBase;

/**
 * Class for Non Playable Characters like lectors and fellow students.
 * 
 */
public class NPC extends SpriteBase {
    private final String ID;
    private final String name;
    private final Inventory inventory;
    private Room currentRoom;
    
    /**
     * Instantiates a NPC-object
     * @param ID The ID of the NPC
     * @param name The name of the NPC e.g. "Anders" and "Daniel"
     * @param img The image which represent the charactor
     */
    public NPC(String ID, String name, Image img){ 
        super(img);
        this.ID = ID;
        this.name = name;
        this.setCanCollide(true);
        this.inventory = new Inventory(5000, 100);
    }
    
    /**
     *
     * @return The ID of the NPC
     */
    public String getID() {
        return this.ID;
    }
    
    /**
     * @return  name of an NPC
     */
    public String getName(){ 
        return this.name;
    }
    
    /**
     * NPC receives an Item and adds the item to its inventory.
     * @param item The Item to be added
     * @return True if the Item was added otherwise false;
     */
    public Boolean receiveItem(Item item) {
        return this.inventory.addItem(item);
    }
    
    /**
     * Get the NPC's inventory
     * @return The NPC's inventory
     */
    public Inventory getInventory() {
        return this.inventory;
    }
    
    /**
     * Used to navigate the NPC to a given room
     * @param room The Room that the player will be navigated to.
     */
    public void navigateTo(Room room) {

        Room oldRoom = currentRoom;
        currentRoom = room;
        if(oldRoom != null){
            oldRoom.removePerson(this);
        }
        currentRoom.addNPC(this);
    }
    
    /**
     * 
     * @return The room in which the NPC is located
     */
    public Room getCurrentRoom(){
        return currentRoom;
    }

    @Override
    public void collides(SpriteBase spriteBase) {
        
        if (spriteBase instanceof Player) {
            Player player = (Player)spriteBase;
            
            if (this.canTeleport()) {
                player.navigateTo(this.getNextRoom());
            } else {
                // Reset the nextPos since a collision was detected
                player.setNextPosX(player.getX());
                player.setNextPosY(player.getY());
                player.setNearNPC(this);
            }
        }
    }
}
