
package worldofzuulfx.NPC;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import worldofzuulfx.Game;
import worldofzuulfx.Inventory.Inventory;
import worldofzuulfx.Inventory.PlayerInventory;
import worldofzuulfx.Items.Item;
import worldofzuulfx.Layers;
import worldofzuulfx.Room.Room;
import worldofzuulfx.sprites.SpriteBase;

/**
 * Class for Non Playable Characters like lectors and fellow students.
 * 
 * @author hjaltefromholtrindom
 */
public class NPC extends SpriteBase {
    private final String ID;
    private final String name;
    private final Inventory inventory;
    private Room currentRoom;
    
    public NPC(String ID, String name, Image img){ // Constructor for NPC class
        super(img);
        this.ID = ID;
        this.name = name;
        this.setCanCollide(true);
        this.inventory = new Inventory(5000, 100);
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
