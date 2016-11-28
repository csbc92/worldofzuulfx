/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuulfx.Events;

import worldofzuulfx.Items.Item;
import worldofzuulfx.Player;

/**
 *
 * @author JV
 */
public class ItemDropEvent {

    private final Item item;
    private final Player player;
    
    public ItemDropEvent(Item item, Player player) {
        this.item = item;
        this.player = player;
    }

    public Item getItem() {
        return this.item;
    }

    public Player getPlayer() {
        return this.player;
    }
}
