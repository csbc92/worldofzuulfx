/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuulfx.Inventory;

import java.util.ArrayList;
import worldofzuulfx.Items.Item;

public class Inventory {

    private final int capacity;
    private final int maxWeight;
    private int currentWeight;
    private ArrayList<Item> itemList;

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
    
       
    public int getSize() {
        return itemList.size();
    }

    /**
     * @return the itemList
     */
    public ArrayList<Item> getItemList() {
        // Makes a copy of the original ItemList.
        ArrayList<Item> copy = new ArrayList<>(itemList);
        return copy;

    }

    public Boolean addItem(Item item) {
        // Cannot add an item that already exists
        if (item != null) {
            if (itemList.contains(item)) {
                return false;
            } //Check if inventory has space to a additional item and if the current weight exceeds maxWeight.
            else if (itemList.size() != capacity && (item.getWeight() + this.currentWeight) < maxWeight) {
                itemList.add(item);
                return true;
            }
        }
        return false;
    }

    public Boolean removeItem(Item item) {
        if (item != null && !item.getIsLocked()) {
            return itemList.remove(item);
        }
        return false;
    }

    public Item getItem(int index) {
        if (index > - 1) {
            return itemList.get(index);
        }
        return null;
    }

    public boolean contains(Class<?> cls) {
        if (amountOf(cls) > 0) {
            return true;
        } else {
            return false;
        }
    }

    public int amountOf(Class<?> cls) {
        int counter = 0;
        for (Item item : itemList) {
            if (cls.isInstance(item)) {
                counter++;
            }
        }
        return counter;
    }

    public boolean contains(String ID) {
        for (Item item : itemList) {
            if (item.getID().equals(ID)) {
                return true;
            }
        }
        return false;
    }

    public Item find(String itemString) {
        try {
            for (Item item : itemList) {
                if (item.getDescription().equalsIgnoreCase(itemString)) {
                    return item;
                }
            }
        } catch (NullPointerException e) {
            //TODO: Handle nullpointer
        }
        return null;
    }
}
