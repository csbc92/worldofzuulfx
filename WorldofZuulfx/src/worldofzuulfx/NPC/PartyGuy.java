package worldofzuulfx.NPC;

import worldofzuulfx.Player;
import worldofzuulfx.Room.Room;
import java.util.ArrayList;
import javafx.scene.image.Image;
import worldofzuulfx.Inventory.Inventory;
import worldofzuulfx.Items.Item;
import worldofzuulfx.Items.ItemFactory;

/**
 *
 */
public class PartyGuy extends NPC {

    int[] partyRNG = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    private Inventory inventory;

    public PartyGuy(String ID, String name, Image img) {
        super(ID, name, img);
        // The inventory can carry an item which weighs 10000 grams.
        // However, the inventory can only contain one item at the time.
        inventory = new Inventory(10000, 1);
    }

    // TODO write about Party Time
    /**
     * Adds partyguy to a random room
     * and makes sure that PartyGoy always has a clock on him.
     * @param rooms The list of rooms which the PartyGuy can spawn in.
     */
    public void spawn(ArrayList<Room> rooms) {
        int random;
        random = (int) (Math.random() * rooms.size());
        this.navigateTo(rooms.get(random));
        inventory.addItem(ItemFactory.makeClock(30));
    }

    /**
     *
     * @return The one item found in the NPC's inventory.
     */
    public Item giveItem() {
        Item item;
        if (inventory.getSize() > 0) {
            item = inventory.getItem(0);
            inventory.removeItem(item);
            return item;
        }
        return null;
    }
}
