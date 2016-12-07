
package worldofzuulfx.Inventory;

import java.util.ArrayList;
import worldofzuulfx.Items.Item;

public class Inventory {

    private final int capacity;
    private final int maxWeight;
    private int currentWeight;
    private ArrayList<Item> itemList;

    /**
     * Creates an inventory which contains items.
     * @param maxWeight The maximum weight that the inventory can carry
     * @param capacity The capacity that the inventory can carry.
     */
    public Inventory(int maxWeight, int capacity) {
        this.maxWeight = maxWeight;
        this.capacity = capacity;
        this.itemList = new ArrayList<>();
    }

    /**
     * @return the capacity
     *
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * @return the maxWeight
     */
    public int getMaxWeight() {
        return maxWeight;
    }

    /**
     * @return the currentWeight
     */
    public int getCurrentWeight() {
        return currentWeight;
    }
    
    /**
     *
     * @return The numbers of items inside the inventory
     */
    public int getSize() {
        return itemList.size();
    }

    /**
     * @return a copy of the itemList
     */
    public ArrayList<Item> getItemList() {
        // Makes a copy of the original ItemList.
        ArrayList<Item> copy = new ArrayList<>(itemList);
        return copy;

    }

    /**
     * Adds an item to the inventory if the following are true:
     * Item is not null.
     * Item does not already exists.
     * Inventory har space to an additional item.
     * The current weight plus the weight of the item does not exceed max weight
     * @param item
     * @return
     */
    public Boolean addItem(Item item) {
        // Cannot add an item that already exists
        if (item != null) {
            if (itemList.contains(item)) {
                return false;
            } //Check if inventory has space to an additional item and if the current weight exceeds maxWeight.
            else if (itemList.size() != capacity && (item.getWeight() + this.currentWeight) < maxWeight) {
                itemList.add(item);
                return true;
            }
        }
        return false;
    }

    /**
     * Removes an item from the inventory if item is not null and the item
     * which is to be removed is not locked. 
     * @param item The item to be removed
     * @return True if the item was removed otherwise false
     */
    public Boolean removeItem(Item item) {
        if (item != null && !item.getIsLocked()) {
            return itemList.remove(item);
        }
        return false;
    }

    /**
     * Get an item based on the inventory index
     * @param index
     * @return The item based on the index
     */
    public Item getItem(int index) {
        if (index > - 1) {
            return itemList.get(index);
        }
        return null;
    }

    /**
     * Checks if the inventory contains a specific classe
     * @param cls The class to be found
     * @return True if the item class was found otherwise false
     */
    public boolean contains(Class<?> cls) {
        if (amountOf(cls) > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Gets the amount of a given item class in the inventory
     * @param cls
     * @return 
     */
    public int amountOf(Class<?> cls) {
        int counter = 0;
        for (Item item : itemList) {
            if (cls.isInstance(item)) {
                counter++;
            }
        }
        return counter;
    }

    /**
     * Checks if the inventory contains a specific Item ID
     * @param ID The ID of the item.
     * @return True if the item ID was found otherwise false
     */
    public boolean contains(String ID) {
        for (Item item : itemList) {
            if (item.getID().equalsIgnoreCase(ID)) {
                return true;
            }
        }
        return false;
    }
}
