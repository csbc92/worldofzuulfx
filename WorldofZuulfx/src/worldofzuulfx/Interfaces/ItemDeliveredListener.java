package worldofzuulfx.Interfaces;

import worldofzuulfx.Events.ItemDeliveredEvent;

/**
 * An interface which gives the functionality to listen for when an item is delivered.
 * itemDelivered describes what happens when a item has been delivered. 
 */
public interface ItemDeliveredListener {

    public void itemDelivered(ItemDeliveredEvent event);
}
