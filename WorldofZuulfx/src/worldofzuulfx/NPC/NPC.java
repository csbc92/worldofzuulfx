
package worldofzuulfx.NPC;

import worldofzuulfx.Game;
import worldofzuulfx.Inventory;
import worldofzuulfx.Items.Item;
import worldofzuulfx.Layers;
import worldofzuulfx.Room;

/**
 * Class for Non Playable Characters like lectors and fellow students.
 * 
 * @author hjaltefromholtrindom
 */
public abstract class NPC {
    private final String ID;
    private final String name;
    private final Inventory inventory;
    private Room currentRoom;
    
    public NPC(String ID, String name){ // Constructor for NPC class
        this.ID = ID;
        this.name = name;
        this.inventory = new Inventory(Layers.spritesLayer,5000, 100);
    }
    
    public String getID() {
        return this.ID;
    }
    
    public String getName(){ // Get method for name of an NPC
        return this.name;
    }
    
    public Boolean receiveItem(Item item) {
        return this.inventory.addItem(item);
    }
    
    public Inventory getInventory() {
        return this.inventory;
    }
    
    public void navigateTo(Room room) {

        Room oldRoom = currentRoom;
        currentRoom = room;
        if(oldRoom != null){
            oldRoom.removePerson(this);
        }
        currentRoom.addNPC(this);
    }
    
    public Room getCurrentRoom(){
        return currentRoom;
    }
}
