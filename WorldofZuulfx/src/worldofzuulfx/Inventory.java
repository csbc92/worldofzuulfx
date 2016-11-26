/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuulfx;

import java.util.ArrayList;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import worldofzuulfx.Events.ItemPickupEvent;
import worldofzuulfx.Events.ItemUseEvent;
import worldofzuulfx.Interfaces.ItemPickupListener;
import worldofzuulfx.Interfaces.ItemUseListener;
import worldofzuulfx.Items.Item;

/**
 *
 *
 */
public class Inventory implements ItemUseListener, ItemPickupListener {
    
    private final int capacity;
    private final int maxWeight;
    private int currentWeight;
    private Pane layer;
    
    private ArrayList<Item> itemList;
    private Item selected;
    private Rectangle selectionRect;
    
    public Inventory(int maxWeight, int capacity) {
        this.maxWeight = maxWeight;
        this.capacity = capacity;
        this.itemList = new ArrayList<>();
    }
    
    public Inventory(Pane layer, int maxWeight, int capacity) {
        this(maxWeight, capacity);
        this.layer = layer;
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
     * @return the itemList
     */
    public ArrayList<Item> getItemList() {
        // Makes a copy of the original ItemList.
        ArrayList<Item> copy = new ArrayList<>(itemList);
        return copy;
        
    }
    
    public ArrayList<String> getStringList() {
        ArrayList<String> list;
        list = new ArrayList<>();
        try {
            for (Item item : itemList) {
                list.add(item.getDescription());
            }
        } catch (NullPointerException e) {
            
        }
        return list;
    }
    
    public Boolean addItem(Item item) {
        // Cannot add an item that already exists
        if (itemList.contains(item)) {
            return false;
        } //Check if inventory has space to a additional item and if the current weight exceeds maxWeight.
        else if (itemList.size() != capacity && (item.getWeight() + this.currentWeight) < maxWeight) {
            itemList.add(item);
            return true;
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
    
    public void draw(boolean redraw) {
        int i = 0;
        if (redraw) {
            if (getLayer() != null) {
                getLayer().getChildren().clear();
                for (Item item : itemList) {
                    item.move(i * 50 + 20, 0);
                    item.updateUI();
                    getLayer().getChildren().add(item.getImageView());
                    i++;
                }
            }
        }
        if (selected != null) {
            getLayer().getChildren().remove(selectionRect);
            selectionRect = new Rectangle(selected.getX(), selected.getY(), selected.getHeight(), selected.getWidth());
            selectionRect.setFill(Color.TRANSPARENT);
            selectionRect.setStroke(Color.WHITESMOKE);
            selectionRect.setStrokeWidth(2);
            getLayer().getChildren().add(selectionRect);
        }
    }
    
    public void selectItem(Item i) {
        selected = i;
    }
    
    public void nextItem() {
        if (selected != null) {
            int i = itemList.indexOf(selected) + 1;
            if (i < itemList.size()) {
                selectItem(itemList.get(i));
            } else {
                selectItem(itemList.get(itemList.size() - 1));
            }
        }
    }
    
    public void previousItem() {
        if (selected != null) {
            int i = itemList.indexOf(selected) - 1;
            if (i > -1) {
                selectItem(itemList.get(i));
            } else {
                selectItem(itemList.get(0));
            }
        }
    }

    /**
     * @return the layer
     */
    public Pane getLayer() {
        return layer;
    }

    /**
     * @param layer the layer to set
     */
    public void setLayer(Pane layer) {
        this.layer = layer;
    }
    
    @Override
    public void itemUsed(ItemUseEvent event) {
        if (itemList.isEmpty()) {
            selected = null;
        } else {
            selected = itemList.get(0);
        }
        draw(true);
    }
    
    @Override
    public void itemPickedUp(ItemPickupEvent event) {
        if (selected == null) {
            selected = event.getItem();
        }
        draw(true);
        
    }
    
}
