/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuulfx.Events;

import worldofzuulfx.Items.Item;


public class ItemReceivedEvent {

    private final Item item;

    /**
     * An event containing information about reception of an item
     * @param item The item that was received.
     */
    public ItemReceivedEvent(Item item) {

        this.item = item;
    }
    
    public Item getItem() {
        return this.item;
    }
}
