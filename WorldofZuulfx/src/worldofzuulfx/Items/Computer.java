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

    public Computer(Pane layer, String ID, String description, int weight) {
        super(layer, Game.tiles.get(60).clone().getImageView().getImage(), ID, description, weight);
    }

    @Override
    public void use(Player player) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
