package worldofzuulfx.Interfaces;

import worldofzuulfx.Events.ItemPickupEvent;

/**
 * An interface which gives the functionality to listen for when an item is
 * picked up. itemPickedUp describes what happens when a item has been picked up.
 */
public interface ItemPickupListener {

    public void itemPickedUp(ItemPickupEvent event);
}
