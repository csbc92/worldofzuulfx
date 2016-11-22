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
import javafx.scene.paint.Color;

/**
 *
 * @author cclausen
 */
public class TileMap {

    private HashMap<Integer, Tile> tileSet;
    private ArrayList<Tile> tileTerrain;
    private int[][] tileTerrainLayout;
    private int[] collideableTilesIDs;
    private final static int mapWidth = 512;
    public final static int mapHeight = 384;
    private final static int tileWidth = 32;
    private final static int tileHeight = 32;

    public TileMap(int[][] tileTerrainLayout, HashMap<Integer, Tile> tileSet) {
        this.tileTerrainLayout = tileTerrainLayout;
        this.tileSet = new HashMap<>(tileSet);
        this.tileTerrain = new ArrayList<>();
        this.collideableTilesIDs = new int[] { 0, 12, 13, 214 };
    }

    public void draw(Pane pane) {

        int xOffset = 0;
        int yOffset = 0;

        for (int row = 0; row < tileTerrainLayout.length; row++) {
            for (int column = 0; column < tileTerrainLayout[row].length; column++) {

                int tileId = tileTerrainLayout[row][column];
                Tile tile = this.tileSet.get(tileId).clone();
                tile.setCanCollide(false);
                
                // Set collision
                for (int i = 0; i < this.collideableTilesIDs.length; i++) {
                    if (tile.getID() == this.collideableTilesIDs[i]) {
                        tile.setCanCollide(true);
                    }
                }
                
                // DEBUGGING ONLY
//                if (tile.getID() == 0) {
//                    tile.getBounds().setFill(Color.RED);
//                    tile.getBounds().setStroke(Color.BLACK);
//
//                    pane.getChildren().add(tile.getBounds());
//                } else {
//                    tile.getBounds().setFill(Color.GREEN);
//                    tile.getBounds().setStroke(Color.BLACK);
//
//                    pane.getChildren().add(tile.getBounds());
//                }
                tile.setLayer(pane);

                tileTerrain.add(tile);

                // This line draws the tile on the pane
                pane.getChildren().add(tile.getImageView());
                tile.setX(xOffset);
                tile.setY(yOffset);
                tile.updateUI();

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
