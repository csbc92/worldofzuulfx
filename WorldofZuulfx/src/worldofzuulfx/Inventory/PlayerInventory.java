/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuulfx.Inventory;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import worldofzuulfx.ConsoleInfo;
import worldofzuulfx.Items.Book;
import worldofzuulfx.Items.Clock;
import worldofzuulfx.Items.CoffeeVoucher;
import worldofzuulfx.Items.Computer;
import worldofzuulfx.Items.Drink;
import worldofzuulfx.Items.Globus;
import worldofzuulfx.Items.Item;
import worldofzuulfx.Items.Note;
import worldofzuulfx.Player;

/**
 * This class extends Inventory with functionality to draw the inventory on a
 * layer.
 *
 */
public class PlayerInventory extends Inventory {

    private Pane layer;
    private Player player;
    private Item selectedItem;
    private Rectangle selectionRect;

    /**
     * Uses the constructor of the super-class
     *
     * @param maxWeight The maximum weight that the inventory can carry
     * @param capacity The capacity that the inventory can carry.
     */
    public PlayerInventory(int maxWeight, int capacity) {
        super(maxWeight, capacity);
    }

    /**
     * Draws the PlayerInventory
     *
     * @param redraw If true the inventory is redrawn which means that all items
     * are drawn again. Otherwise only the selection box is redrawn based on the
     * selected item.
     */
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

    /**
     * Select an item in the inventory based on Item i. Based on the selected
     * item, a text is displayed. e.g. "Give the beer" and "Use the globus".
     *
     * @param i The Item to be selected
     */
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

            if (i instanceof Globus) {
                txt.append("Use the globus " + "(" + ((Globus) i).getSpinAmount() + ")");
            }

            if (i instanceof Note) {
                txt.append("Look through your notes");
            }
            if (i instanceof Computer) {
                txt.append("Use the computer");
            }
            if (i instanceof Book) {
                if (player.getNearNPC() != null) {
                    txt.append("Give the " + i.getDescription() + " to " + player.getNearNPC().getName());
                } else {
                    txt.append("Use the " + i.getDescription());
                }
            }
            if (i instanceof Clock) {
                txt.append("Use the clock");
            }
            ConsoleInfo.setItemData(txt.toString());
        } else {
            ConsoleInfo.setItemData(txt.toString());
        }

    }

    /**
     * Selects the next item in the inventory. If the current selection is the
     * last item nothing happens
     */
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

    /**
     * Selects the previous item in the inventory. If the current selection is
     * the first item nothing happens
     */
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
     * @param player The player who owens this inventory
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Executes the Ancestor-method (adds an item) and if the inventory contains
     * at least one item, the first item in the inventory is selected.
     *
     * @param item item to be added
     * @return True if the item was added and the inventory was drawn otherwise false.
     */
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

    /**
     * Executes the Ancestor-method (Removes an item) and if the inventory contains
     * at least one item, the first item in the inventory is selected.
     * 
     * @param item item to be removed
     * @return True if the item was removed and the inventory was drawn otherwise false.
     */
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
