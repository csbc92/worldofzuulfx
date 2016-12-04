package worldofzuulfx.NPC;

import worldofzuulfx.Player;
import worldofzuulfx.Room;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.image.Image;
import worldofzuulfx.Game;
import worldofzuulfx.Inventory.Inventory;
import worldofzuulfx.Items.Item;
import worldofzuulfx.Items.ItemFactory;
import worldofzuulfx.Quest.Quest;

/**
 *
 * @author hjaltefromholtrindom
 */
public class PartyGuy extends NPC {

    int[] partyRNG = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    private int random;
    private Inventory inventory;

    public PartyGuy(String ID, String name, Image img) {
        super(ID, name, img);
        inventory = new Inventory(10000, 1);
    }

    /**
     * teleports the player to the designated room
     */
    public void partyTime(Player player, Room room) {
        if (this.getCurrentRoom() == player.getCurrentRoom() && !room.getID().equals("downunder")) {
            player.setCanMove(false);
            player.navigateTo(room);
        }
        this.getCurrentRoom().removePerson(this);
        this.navigateTo(room);
    }

    /**
     * adds partyguy to a random room
     */
    public void spawn(ArrayList<Room> rooms) {

        setRandom((int) (Math.random() * rooms.size()));
        this.navigateTo(rooms.get(getRandom()));
        inventory.addItem(ItemFactory.makeClock(30));

    }

    public void setRandom(int r) {
        this.random = r;
    }

    public int getRandom() {
        return random;
    }
<<<<<<< HEAD
<<<<<<< HEAD
    /**
     * Spawns the Partyguy randomly in a room which is not locked.
     * @param rooms
     * @param q
     */
    public void randomSpawn(ArrayList<Room> rooms, HashMap<String, Quest> q){
        int p;
        p = (int)(Math.random()*12);
        if(p==2){
            if(q.get("goToBookStoreQ").isCompleted()){
                setRandom((int)(Math.random()*rooms.size()));
                this.navigateTo(rooms.get(getRandom()));
            }

=======
    
>>>>>>> f67424506daf13213ab8b901b4bb6afdfb1fe56c
    public Item giveItem() {
        Item item;
        if (inventory.getSize() > 0) {
            item = inventory.getItem(0);
            inventory.removeItem(item);
            return item;
<<<<<<< HEAD
=======

>>>>>>> f67424506daf13213ab8b901b4bb6afdfb1fe56c
        }
        return null;
    }
}
