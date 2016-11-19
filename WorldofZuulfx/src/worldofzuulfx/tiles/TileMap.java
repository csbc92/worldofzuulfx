/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuulfx.tiles;

import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 *
 * @author cclausen
 */
public class TileMap {
    private HashMap<Integer, Tile> tileSet;
    private ArrayList<Tile> tileTerrain;
    private int[][] tileTerrainLayout;
    private final static int mapWidth = 512;
    public final static int mapHeight = 384;
    private final static int tileWidth = 32;
    private final static int tileHeight = 32;
    
    public TileMap(int[][] tileTerrainLayout, HashMap<Integer, Tile> tileSet) {
        this.tileTerrainLayout = tileTerrainLayout;
        this.tileSet = new HashMap<>(tileSet);
        this.tileTerrain = new ArrayList<>();
    }
    
    public void draw(Pane pane) {
        
        int xOffset = 0;
        int yOffset = 0;
        
        for (int row = 0; row < tileTerrainLayout.length; row++) {
            for (int column = 0; column < tileTerrainLayout[row].length; column++) {
                
                int tileId = tileTerrainLayout[row][column];
                Tile tile = this.tileSet.get(tileId).clone();
                if (tile.getID() == 0) {
                   tile.setCanCollide(true);
                }
                tile.setLayer(pane);
                
                tileTerrain.add(tile);
                
                ImageView iv = tile.getImageView();
                
                iv.setLayoutX(xOffset);
                iv.setLayoutY(yOffset);
                
                pane.getChildren().add(iv);              
                
                xOffset += 32;
            }
            xOffset = 0;
            yOffset += 32;
        }
    }

    /**
     * @return the tileTerrain
     */
    public ArrayList<Tile> getTileTerrain() {
        return tileTerrain;
    }
    
    
}
