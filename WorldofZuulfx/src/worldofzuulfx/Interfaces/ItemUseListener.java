package worldofzuulfx.Interfaces;

import worldofzuulfx.Events.ItemUseEvent;

/**
 * An interface which gives the functionality to listen for when an item is
 * used. itemUsed describes what happens when a item has been used
 */
public interface ItemUseListener {

    public void itemUsed(ItemUseEvent event);
}
