/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuulfx.tiles;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import worldofzuulfx.sprites.SpriteBase;

/**
 *
 * @author cclausen
 */
public class Tile extends SpriteBase {

    private int id;

    public Tile(int id, Image img) {
        super(img);
        this.id = id;

    }

    public int getID() {
        return this.id;
    }
    
    public Tile clone(){
        
        return new Tile(this.id,this.getImageView().getImage());
    }
}
