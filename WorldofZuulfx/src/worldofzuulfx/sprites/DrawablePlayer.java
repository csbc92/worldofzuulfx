/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuulfx.sprites;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import worldofzuulfx.Player;
import worldofzuulfx.Player;
import worldofzuulfx.sprites.SpriteBase;

/**
 *
 * @author JV
 */
public class DrawablePlayer extends Player {

    private final SpriteBase sprite;
    
    public DrawablePlayer(String name, Pane layer, Image image, double posX, double posY) {
        super(name);
        sprite = new SpriteBase(layer, image, posX, posY) {};
    }
    
    public SpriteBase getSprite() {
        return this.sprite;
    }
    
}
