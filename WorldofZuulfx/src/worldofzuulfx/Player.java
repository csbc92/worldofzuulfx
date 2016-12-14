package worldofzuulfx;

import worldofzuulfx.Room.Room;
import worldofzuulfx.Inventory.PlayerInventory;
import worldofzuulfx.Quest.Quest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import worldofzuulfx.Events.ItemDeliveredEvent;
import worldofzuulfx.Events.ItemPickupEvent;
import worldofzuulfx.Events.ItemUseEvent;
import worldofzuulfx.Events.NavigateEvent;
import worldofzuulfx.Interfaces.BarValueListener;
import worldofzuulfx.Interfaces.ItemDeliveredListener;
import worldofzuulfx.Items.Item;
import worldofzuulfx.NPC.NPC;
import worldofzuulfx.Interfaces.ItemPickupListener;
import worldofzuulfx.Interfaces.ItemUseListener;
import worldofzuulfx.Quest.Reward;
import worldofzuulfx.Interfaces.NavigateListener;
import worldofzuulfx.Inventory.Inventory;
import worldofzuulfx.sprites.SpriteBase;

public class Player extends SpriteBase implements BarValueListener {

    private String name;
    private Bar ects;
    private Bar energy;
    private Bar hp;
    private boolean drunk;
    private PlayerInventory inventory;

    private Quest activeQuest;
    private HashMap<String, Quest> inactiveQuests;
    private Room currentRoom;
    private int alcoTolerance;
    private int alcoCounter;
    private NPC nearNPC;
    private boolean droppedItem;
    private int timeLeft;
    private ArrayList<ItemPickupListener> itemPickupListeners;
    private ArrayList<ItemDeliveredListener> itemDeliveredListeners;
    private ArrayList<ItemUseListener> itemUseListeners;
    private ArrayList<NavigateListener> navigateListener;
    private ArrayList<Room> roomRandom;

    public Player(String name, Pane layer, Image image, double posX, double posY) {
        super(layer, image, posX, posY);
        this.name = name;

        drunk = false;
        inventory = new PlayerInventory(5000, 6);
        inventory.setPlayer(this);
        initializeBars();
        initializeListeners();
        inactiveQuests = new HashMap<>();
        droppedItem = false;

    }

    /**
     * Initiates all listeners e.g. navigateListener.
     */
    private void initializeListeners() {
        navigateListener = new ArrayList<>();
        itemPickupListeners = new ArrayList<>();
        itemDeliveredListeners = new ArrayList<>();
        itemUseListeners = new ArrayList<>();
    }

    /**
     * Initiates all bars and sets starting values.
     */
    private void initializeBars() {
        ects = new Bar(0, 10);
        energy = new Bar(0, 100);
        energy.addBarValueListener(this);
        hp = new Bar(0, 3);

        ects.setValue(0);
        energy.setValue(100);
        hp.setValue(3);
        timeLeft = (5 * 60);
    }

    /**
     *
     * @return Current ECTS value
     */
    public int getECTS() {
        return ects.getValue();
    }

    /**
     * @return Returns the Player's ECTS-bar
     */
    public Bar getECTSBar() {
        return this.ects;
    }

    /**
     *
     * @return Current energy value
     */
    public int getEnergy() {
        return energy.getValue();
    }

    /**
     *
     * @return Energybar
     */
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
        this.energy.increaseEnergy(energyAmount);

    }

    /**
     *
     * @return Player's inventory
     */
    public PlayerInventory getInventory() {
        return inventory;
    }

    /**
     *
     * @return Current active Quest
     */
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

        if (quest == null) {
            ConsoleInfo.setQuestData("No active quest");
        }

        if (overrideOldQuest) {
            this.activeQuest = quest;
            if (quest != null) {
                ConsoleInfo.setQuestData(quest.getDescription());
            }
            return true;
        } else if (this.activeQuest == null) {
            this.activeQuest = quest;
            if (quest != null) {
                ConsoleInfo.setQuestData(quest.getDescription());
            }
            return true;
        }

        return false;
    }

    /**
     * Untracks the Quest q if the quest is not the active quest.
     * The quest q is added to the inactive quest list.
     * @param q
     */
    public void untrackQuest(Quest q) {
        if (activeQuest != null) {
            getInactiveQuests().put(q.getId(), q);
            setActiveQuest(null, true);
        }
    }

    /**
     * The player uses an item if the player's inventory contains it.
     * The item then calls it method use() which reqeuires a Player as argument.
     * Then it notifies all ItemUse listeners.
     * @param item
     */
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
        // Checks if Player just dropped an item.
        if (!droppedItem) {
            Inventory roomInventory = this.getCurrentRoom().getRoomInventory();
            // Checks if the Item is located inside the room. If false - Player can't pickup the item.
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
     * @param reward e.g. an item and ECTS-points
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
     * ChangeRoomListeners. It checks if room is locked - if it is the case
     * nothing happens.
     *
     * @param room
     * @return It will return true if it navigates,
     */
    public boolean navigateTo(Room room) {
        if (!room.isLocked()) {
            // TODO slet Drunk, hvis det ikke anvendes.
            Random r = new Random();
            Room oldRoom = null;
            oldRoom = currentRoom;
            currentRoom = room;
            alcoCounter = 0;
            // Generates random number for alcoTolerance whenever the player changes room.
            setAlcoTolerance(r.nextInt(5 - 2) + 2);

            // Decrease the players energy each time he navigates between rooms.
            energy.increaseEnergy(-5);

            ConsoleInfo.setRoomData(room.getShortDescription());
            notifyNavigateListeners(oldRoom, currentRoom);
            return true;
        }
        return false;
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
    public void removeItemDeliveredListener(ItemDeliveredListener listener) {
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
     * Method used to notify ChangeRoomListeners
     *
     * @param item
     */
    private void notifyNavigateListeners(Room oldRoom, Room newRoom) {
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
     * @param i Item to be dropped
     */
    public void drop(Item i) {
        if (this.inventory.removeItem(i)) {
            i.move(this.getX() - 1, this.getY() - 1);
            this.currentRoom.getRoomInventory().addItem(i);
            this.currentRoom.draw();
            setDroppedItem(true);
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
            //    this.blackout(Main.getGame().getRoomHandler().getRooms(false));
            //ConsoleInfo.setConsoleData("You just had a blackout, good luck finding your missing item... MUAHAHAHAHA");

            if (getHp().getValue() > 1) {
                bar.setValue(bar.getMax());
                getHp().setValue(getHp().getValue() - 1);
                setAlcoCounter(0);
                setDrunk();
            } else {
                // TODO sæt finish flag
            }
        }
    }

    /**
     * Update the Player's Image position based on the Player's bounds.
     */
    @Override
    public void updateUI() {
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

    /**
     * @return the timeLeft
     */
    public int getTimeLeft() {
        return timeLeft;
    }

    /**
     * @param timeLeft the timeLeft to set
     */
    public void setTimeLeft(int timeLeft) {
        this.timeLeft = timeLeft;
    }

    /**
     * @return the inactiveQuests
     */
    public HashMap<String, Quest> getInactiveQuests() {
        return inactiveQuests;
    }

    /**
     * @return the hp
     */
    public Bar getHp() {
        return hp;
    }

    @Override
    public void collides(SpriteBase spriteBase) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
