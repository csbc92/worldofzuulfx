/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuulfx.Items;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import worldofzuulfx.Game;
import worldofzuulfx.Player;

/**
 *
 * @author march
 */
public class Computer extends Item {

    public Computer( String ID, String description, int weight) {
        super(Game.tiles.get(160).clone().getImageView().getImage(), ID, description, weight);
    }

    @Override
    public void use(Player player) {
        //TODO Computer
       
        if (player.getNearNPC() == null) {
            // Deliver the item to NPC.  
            player.deliverItem(player.getNearNPC(), this);
        }
    }
}
