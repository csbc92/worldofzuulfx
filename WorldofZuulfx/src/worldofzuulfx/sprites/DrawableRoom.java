/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuulfx.sprites;

import java.util.HashMap;
import javafx.scene.layout.Pane;
import worldofzuulfx.Room;
import worldofzuulfx.tiles.Tile;
import worldofzuulfx.tiles.TileMap;


/**
 *
 * @author cclausen
 */
public class DrawableRoom extends Room {
    
    private final TileMap tileMap;
    private final int[][] tileLayout;
    
    public DrawableRoom(String ID, String description, HashMap<Integer, Tile> tiles, int[][] tileLayout) {
        super(ID, description);
        
        this.tileLayout = tileLayout;
        
        tileMap = new TileMap(this.tileLayout, tiles);
    }
    
    public TileMap getTileMap() {
        return this.tileMap;
    }
    
    public void draw(Pane pane) {
        tileMap.draw(pane);
    }
    
}
