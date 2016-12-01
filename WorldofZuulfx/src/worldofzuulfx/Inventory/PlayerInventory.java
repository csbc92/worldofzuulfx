/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuulfx.Inventory;

import java.util.ArrayList;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import worldofzuulfx.ConsoleInfo;
import worldofzuulfx.Events.ItemDropEvent;
import worldofzuulfx.Events.ItemPickupEvent;
import worldofzuulfx.Events.ItemUseEvent;
import worldofzuulfx.Interfaces.ItemDropListener;
import worldofzuulfx.Interfaces.ItemPickupListener;
import worldofzuulfx.Interfaces.ItemUseListener;
import worldofzuulfx.Items.Book;
import worldofzuulfx.Items.Clock;
import worldofzuulfx.Items.CoffeeVoucher;
import worldofzuulfx.Items.Computer;
import worldofzuulfx.Items.Drink;
import worldofzuulfx.Items.Item;
import worldofzuulfx.Items.Note;
import worldofzuulfx.Player;

/**
 *
 *
 */
public class PlayerInventory extends Inventory {

//    private final int capacity;
//    private final int maxWeight;
//    private int currentWeight;
    private Pane layer;
    private Player player;

//    private ArrayList<Item> itemList;
    private Item selectedItem;
    private Rectangle selectionRect;

    public PlayerInventory(int maxWeight, int capacity) {
        super(maxWeight, capacity);
    }

    public void draw(boolean redraw) {
        int i = 0;
        if (redraw) {
            if (getLayer() != null) {
                getLayer().getChildren().clear();
                for (Item item : getItemList()) {
                    item.move(i * 50 + 20, 0);
                    item.updateUI();
                    getLayer().getChildren().add(item.getImageView());
                    i++;
                }
            }
        }
        if (getSelectedItem() != null) {
            getLayer().getChildren().remove(selectionRect);
            selectionRect = new Rectangle(getSelectedItem().getX(), getSelectedItem().getY(), getSelectedItem().getHeight(), getSelectedItem().getWidth());
            selectionRect.setFill(Color.TRANSPARENT);
            selectionRect.setStroke(Color.WHITESMOKE);
            selectionRect.setStrokeWidth(2);
            getLayer().getChildren().add(selectionRect);
        }
    }

    public void selectItem(Item i) {
        selectedItem = i;
        StringBuilder txt = new StringBuilder("");
        if (i != null) {
            if (i instanceof Drink) {
                if (player.getNearNPC() != null) {
                    txt.append("Give the " + i.getDescription() + " to " + player.getNearNPC().getName());
                } else {
                    txt.append("Drink the " + i.getDescription());
                }

            }
            if (i instanceof CoffeeVoucher) {
                if (player.getCurrentRoom().getID().equalsIgnoreCase("canteen")) {
                    txt.append("Use the voucher " + "(" + ((CoffeeVoucher) i).getVoucherAmount() + ")");
                } else {
                    txt.append("The voucher can only be used in the canteen");
                }
            }
            if (i instanceof Note) {
                txt.append("Look through your notes");
            }
            if (i instanceof Computer) {
                txt.append("Use the computer");
            }
            if (i instanceof Book) {
                txt.append("Use the book");
            }
            if (i instanceof Clock) {
                txt.append("Use the clock");
            }
            ConsoleInfo.setItemData(txt.toString());
        } else {
            ConsoleInfo.setItemData(txt.toString());
        }

    }

    public void nextItem() {
        if (getSelectedItem() != null) {
            int i = getItemList().indexOf(getSelectedItem()) + 1;
            if (i < getItemList().size()) {
                selectItem(getItemList().get(i));
            } else {
                selectItem(getItemList().get(getItemList().size() - 1));
            }
        }
    }

    public void previousItem() {
        if (getSelectedItem() != null) {
            int i = getItemList().indexOf(getSelectedItem()) - 1;
            if (i > -1) {
                selectItem(getItemList().get(i));
            } else {
                selectItem(getItemList().get(0));
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
    /**
     * @return the selectedItem
     */
    public Item getSelectedItem() {
        return selectedItem;
    }

    /**
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * @param player the player to set
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public Boolean addItem(Item item) {
        if (super.addItem(item)) {
            if (getItemList().isEmpty()) {
                selectItem(null);
            } else {
                selectItem(getItemList().get(0));
            }
            draw(true);
            return true;
        }
        return false;
    }

    @Override
    public Boolean removeItem(Item item) {
        if (super.removeItem(item)) {
            if (getItemList().isEmpty()) {
                selectItem(null);
            } else {
                selectItem(getItemList().get(0));
            }
            draw(true);
            return true;
        }
        return false;
    }
}
