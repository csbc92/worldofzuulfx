package worldofzuulfx;

import worldofzuulfx.Quest.Quest;
import java.util.ArrayList;
import java.util.Random;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import worldofzuulfx.Events.ItemDeliveredEvent;
import worldofzuulfx.Events.ItemDropEvent;
import worldofzuulfx.Events.ItemPickupEvent;
import worldofzuulfx.Events.ItemUseEvent;
import worldofzuulfx.Events.NavigateEvent;
import worldofzuulfx.Interfaces.BarValueListener;
import worldofzuulfx.Interfaces.ItemDeliveredListener;
import worldofzuulfx.Interfaces.ItemDropListener;
import worldofzuulfx.Items.Item;
import worldofzuulfx.NPC.NPC;
import worldofzuulfx.Interfaces.ItemPickupListener;
import worldofzuulfx.Interfaces.ItemUseListener;
import worldofzuulfx.Quest.Reward;
import worldofzuulfx.Interfaces.NavigateListener;
import worldofzuulfx.sprites.SpriteBase;

public class Player extends SpriteBase implements BarValueListener {

    private String name;
    private Bar ects;
    private Bar energy;
    private Bar hp;
    private boolean drunk;
    private Inventory inventory;
    // TODO skal dette flyttes ind i QuestInventory, så alt er samlet et sted?
    private Quest activeQuest;
    private ArrayList<Quest> inactiveQuests;
    private Room currentRoom;
    private int alcoTolerance;
    private int alcoCounter;
    private NPC nearNPC;
    private boolean droppedItem;
    private ArrayList<NavigateListener> changeRoomListeners;
    private ArrayList<ItemPickupListener> itemPickupListeners;
    private ArrayList<ItemDropListener> itemDropListeners;
    private ArrayList<ItemDeliveredListener> itemDeliveredListeners;
    private ArrayList<ItemUseListener> itemUseListeners;
    private ArrayList<NavigateListener> navigateListener;
    private ArrayList<Room> roomRandom;

    public Player(String name, Pane layer, Image image, double posX, double posY) {
        super(layer, image, posX, posY);
        this.name = name;
        ects = new Bar(0, 30, 0);
        energy = new Bar(0, 100, 100);
        energy.addBarValueListener(this);
        hp = new Bar(0, 3, 3);
        drunk = false;
        inventory = new Inventory(5000, 6);
        inventory.setPlayer(this);

        navigateListener = new ArrayList<>();
        itemPickupListeners = new ArrayList<>();
        itemDeliveredListeners = new ArrayList<>();
        itemUseListeners = new ArrayList<>();
        inactiveQuests = new ArrayList<>();
        itemDropListeners = new ArrayList<>();
        droppedItem = false;
        addItemUseListener(inventory);
        addItemPickupListener(inventory);
        addItemDropListener(inventory);
    }

    public int getECTS() {
        return ects.getValue();
    }

    public int getEnergy() {
        return energy.getValue();
    }

    public Bar getEnergyBar() {
        return energy;
    }

    /**
     * Increase the player's energi-level by adding x amount of energy to the
     * current energy-level.
     *
     * @param energyAmount
     */
    public void increaseEnergy(int energyAmount) {
        if (energyAmount > 0) {
            int currentEnergyVal = this.getEnergy();
            int newEnergyVal = currentEnergyVal + energyAmount;

            // Set the energy to max if the new value would otherwise
            // be higher than what the Bar allows.
            if (newEnergyVal > this.energy.getMax()) {
                this.energy.setValue(this.energy.getMax());
            } else {
                this.energy.setValue(newEnergyVal);
            }
        }
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Quest getActiveQuest() {
        return this.activeQuest;
    }

    /**
     * Set the player's Quest.
     *
     * @param quest The quest a player should do.
     * @param overrideOldQuest If this flag is set to true the player's "old"
     * quest will be overridden.
     * @return
     */
    public boolean setActiveQuest(Quest quest, boolean overrideOldQuest) {

        if (overrideOldQuest) {
            this.activeQuest = quest;
            return true;
        } else if (this.activeQuest == null) {
            this.activeQuest = quest;
            return true;
        }

        return false;
    }

    public void untrackQuest(Quest q) {
        if (activeQuest != null) {
            inactiveQuests.add(q);
            activeQuest = null;
        }
    }

    public void useItem(Item item) {
        if (item != null) {
            if (this.inventory.contains(item.getClass())) {
                item.use(this);
                notifyItemUseListeners(item);
            }
        }
    }

    /**
     * Deliver an item to the specified NPC.
     *
     * @param receiver
     * @param item
     */
    public void deliverItem(NPC receiver, Item item) {
        //TODO: If-statement should check for the exact item - not the class.
        if (this.inventory.contains(item.getClass())) {
            if (receiver.receiveItem(item)) {
                // Remove the item from the players inventory.
                this.inventory.removeItem(item);
                // Notify listeners that an item was delivered.
                notifyItemDeliveredListeners(receiver, item);
            }
        }
    }

    /**
     * Pick up the specified item from the player's current room and add it to
     * the player's inventory. The same item cannot be added multiple times.
     *
     * @param item
     */
    public void pickupItem(Item item) {
        if (!droppedItem) {   
            Inventory roomInventory = this.getCurrentRoom().getRoomInventory();
            if (roomInventory.contains(item.getID())) {
                if (this.inventory.addItem(item)) {
                    roomInventory.removeItem(item);
                    this.notifyItemPickupListeners(item);
                }
            }
        }
    }

    /**
     * Gives the player a Reward.
     *
     * @param reward
     */
    public void giveReward(Reward reward) {
        if (reward != null) {

            Item itemReward = reward.getItem();
            if (itemReward != null) {
                this.inventory.addItem(itemReward);
            }

            int ectsReward = reward.getECTSPoints();
            int currentEcts = this.getECTS();

            this.ects.setValue(currentEcts + ectsReward);
        }
    }

    /**
     * Navigates the player to the specified room. This method notifies
     * ChangeRoomListeners.
     *
     * @param room
     */
    public void navigateTo(Room room) {
        Random r = new Random();
        Room oldRoom = currentRoom;
        currentRoom = room;
        alcoCounter = 0;
        // Generates random number for alcoTolerance whenever the player changes room.
        setAlcoTolerance(r.nextInt(5 - 2) + 2);

        // Decrease the players energy each time he navigates between rooms.
        energy.setValue(energy.getValue() - 2);

        notifyChangeRoomListeners(oldRoom, currentRoom);
    }

    /**
     * Navigates the player to the specified room. This method does NOT notify
     * ChangeRoomListeners.
     *
     * @param room
     */
    public void navigateSilentlyTo(Room room) {
        currentRoom = room;
    }

    /**
     * Subscribe to the event when a player navigates.
     *
     * @param listener
     */
    public void addNavigateListener(NavigateListener listener) {
        if (!this.navigateListener.contains(listener)) {
            this.navigateListener.add(listener);
        }
    }

    /**
     * Unsubscribe to the event when a player navigates.
     *
     * @param listener
     */
    public void removeNavigateListener(NavigateListener listener) {
        if (this.navigateListener.contains(listener)) {
            this.navigateListener.remove(listener);
        }
    }
    /**
     * Subscribe to the event when a player drops an item.
     *
     * @param listener
     */
    public void addItemDropListener(ItemDropListener listener) {
        if (!this.itemDropListeners.contains(listener)) {
            this.itemDropListeners.add(listener);
        }
    }

    /**
     * Unsubscribe to the event when a player drops an item.
     *
     * @param listener
     */
    public void removeItemDropListener(ItemDropListener listener) {
        if (this.itemDropListeners.contains(listener)) {
            this.itemDropListeners.remove(listener);
        }
    }

    /**
     * Subscribe to the event when a player picks up an Item.
     *
     * @param listener
     */
    public void addItemPickupListener(ItemPickupListener listener) {
        if (!this.itemPickupListeners.contains(listener)) {
            this.itemPickupListeners.add(listener);
        }
    }

    /**
     * Subscribe to the event when a player uses an item.
     *
     * @param listener
     */
    public void addItemUseListener(ItemUseListener listener) {
        if (!this.itemUseListeners.contains(listener)) {
            this.itemUseListeners.add(listener);
        }
    }

    /**
     * Unsubscribe to the event when a player picks up an Item.
     *
     * @param listener
     */
    public void removeItemPickupListener(ItemPickupListener listener) {
        if (this.itemPickupListeners.contains(listener)) {
            this.itemPickupListeners.remove(listener);
        }
    }

    /**
     * Unsubscribe to the event when a player uses an item.
     *
     * @param listener
     */
    public void removeItemUseListener(ItemUseListener listener) {
        if (this.itemUseListeners.contains(listener)) {
            this.itemUseListeners.remove(listener);
        }
    }

    /**
     * Subscribe to the event when a player delivers an Item.
     *
     * @param listener
     */
    public void addItemDeliveredListener(ItemDeliveredListener listener) {
        if (!this.itemDeliveredListeners.contains(listener)) {
            this.itemDeliveredListeners.add(listener);
        }
    }

    /**
     * Unsubscribe to the event when a player delivers an Item.
     *
     * @param listener
     */
    public void removeItemDeliveredListnerr(ItemDeliveredListener listener) {
        if (this.itemDeliveredListeners.contains(listener)) {
            this.itemDeliveredListeners.remove(listener);
        }
    }

    /**
     * Method used to notify ItemPickupListeners
     *
     * @param item
     */
    private void notifyItemPickupListeners(Item item) {
        if (this.itemPickupListeners != null) {
            for (ItemPickupListener listener : this.itemPickupListeners) {
                listener.itemPickedUp(new ItemPickupEvent(item, this));
            }
        }
    }
    
    /**
     * Method used to notify ItemPickupListeners
     *
     * @param item
     */
    private void notifyItemDropListeners(Item item) {
        if (this.itemDropListeners != null) {
            for (ItemDropListener listener : this.itemDropListeners) {
                listener.ItemDropped(new ItemDropEvent(item, this));
            }
        }
    }

    /**
     * Method used to notify ChangeRoomListeners
     *
     * @param item
     */
    private void notifyChangeRoomListeners(Room oldRoom, Room newRoom) {
        if (this.navigateListener != null) {
            for (NavigateListener listener : this.navigateListener) {
                listener.navigated(new NavigateEvent(oldRoom, newRoom, this));
            }
        }
    }

    /**
     * Method used to notify ItemDeliveredListeners
     *
     * @param receiver
     * @param item
     */
    public void notifyItemDeliveredListeners(NPC receiver, Item item) {
        if (this.itemDeliveredListeners != null) {
            for (ItemDeliveredListener listener : this.itemDeliveredListeners) {
                listener.itemDelivered(new ItemDeliveredEvent(this, receiver, item));
            }
        }
    }

    /**
     * Method used to notify ItemUseListeners
     *
     * @param item
     */
    public void notifyItemUseListeners(Item item) {
        if (this.itemUseListeners != null) {
            for (ItemUseListener listener : this.itemUseListeners) {
                listener.itemUsed(new ItemUseEvent(item, this));
            }
        }
    }

    /**
     * Drops the specified Item in the room where the player currently is. If
     * the item doesn't exist in the player's inventory - nothing is dropped in
     * the current room.
     *
     * @param i
     */
    public void drop(Item i) {
        if (this.inventory.removeItem(i)) {
            i.move(this.getX()-1, this.getY()-1);
            this.currentRoom.getRoomInventory().addItem(i);
            this.currentRoom.drawItems();
            setDroppedItem(true);
            notifyItemDropListeners(i);
        }
    }

    /**
     * Makes the player teleport to random location
     *
     * @param rooms
     */
    public void blackout(ArrayList<Room> rooms) {
        int random = (int) (Math.random() * rooms.size());
        this.navigateTo(rooms.get(random));
    }

    /**
     * @return the currentRoom
     */
    public Room getCurrentRoom() {
        return currentRoom;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the alcoCounter
     *
     * @param c
     */
    public void setAlcoCounter(int c) {
        this.alcoCounter = c;
    }

    /**
     * Gets the value of the alcoCounter.
     *
     * @return alcoCounter
     */
    public int getAlcoCounter() {
        return alcoCounter;
    }

    /**
     * Checks whether a player is drunk or not by comparing the tolerance with
     * the alcoCounter.
     */
    public void setDrunk() {
        if (alcoCounter == alcoTolerance) {
            this.drunk = true;
        } else {
            this.drunk = false;
        }
    }

    /**
     * Sets the alcoTolerance.
     *
     * @param i
     */
    public void setAlcoTolerance(int i) {
        this.alcoTolerance = i;
    }

    /**
     * Question whether the player is drunk or not.
     *
     * @return the boolean drunk
     */
    public boolean isDrunk() {
        return this.drunk;
    }

//     public SpriteBase getSprite() {
//        return this.sprite;
//    }
    /**
     * Checks if the player reached minimum energy after energy change, or if
     * player reached it's alcoTolerance.
     *
     * @param bar
     */
    @Override
    public void barValueChanged(Bar bar) {
        if (bar.getValue() <= 0 || isDrunk() == true) {
            // TODO - Håndter blackout!
            this.blackout(Main.getGame().getRoomHandler().getRooms(false));
            //ConsoleInfo.setConsoleData("You just had a blackout, good luck finding your missing item... MUAHAHAHAHA");

            if (hp.getValue() > 0) {
                bar.setValue(bar.getMax());
                hp.setValue(hp.getValue() - 1);
                setAlcoCounter(0);
                setDrunk();
                System.out.println(hp.getValue());
            } else {
                // TODO sæt finish flag
                Main.getGame().setFinished();
            }
        }
    }

    @Override
    public void updateUI() {
        super.updateUI();
        getImageView().relocate(getBounds().getX(), getBounds().getY() - 16);
    }

    /**
     * Get the NPC who is touched by the Player.
     *
     * @return the nearNPC
     */
    public NPC getNearNPC() {
        return nearNPC;
    }

    /**
     * Set the NPC who is touched by the Player.
     *
     * @param nearNPC the nearNPC to set
     */
    public void setNearNPC(NPC nearNPC) {
        this.nearNPC = nearNPC;
    }

    /**
     * @param DroppedItem the DroppedItem to set
     */
    public void setDroppedItem(boolean DroppedItem) {
        this.droppedItem = DroppedItem;
    }
}
