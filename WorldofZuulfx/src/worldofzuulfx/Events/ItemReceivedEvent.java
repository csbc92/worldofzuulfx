/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuulfx.Events;

import worldofzuulfx.Items.Item;
import worldofzuulfx.NPC.NPC;
import worldofzuulfx.Player;

/**
 *
 * @author JV
 */
public class ItemReceivedEvent {

    private final Item item;

    public ItemReceivedEvent(Item item) {

        this.item = item;
    }
}
