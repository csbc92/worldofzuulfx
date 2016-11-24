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
    private final static int tileWidth = 32;
    private final static int tileHeight = 32;

    public TileMap(int[][] tileTerrainLayout, HashMap<Integer, Tile> tileSet) {
        this.tileTerrainLayout = tileTerrainLayout;
        this.tileSet = new HashMap<>(tileSet);
        this.tileTerrain = new ArrayList<>();
        this.collideableTilesIDs = new int[]{0, 12, 13, 214};
        load();
    }

    public void draw(Pane pane) {

        pane.getChildren().clear();
        for (Tile tile : tileTerrain) {
            tile.setLayer(pane);
            pane.getChildren().add(tile.getImageView());
            tile.updateUI();

        }
    }

    /**
     * @return the tileTerrain
     */
    public ArrayList<Tile> getTileTerrain() {
        return tileTerrain;
    }

    private void load() {
        int xOffset = 0;
        int yOffset = 0;
        for (int row = 0; row < tileTerrainLayout.length; row++) {
            for (int column = 0; column < tileTerrainLayout[row].length; column++) {

                int tileId = tileTerrainLayout[row][column];
                Tile tile = this.tileSet.get(tileId).clone();
                tile.setCanCollide(false);

                tile.setPos(column, row);

                tileTerrain.add(tile);

                // This line draws the tile on the pane
                tile.setX(xOffset);
                tile.setY(yOffset);

                xOffset += TileMap.tileWidth;
            }
            xOffset = 0;
            yOffset += TileMap.tileHeight;

        }
        
        // Set collision
        for (Tile tile : tileTerrain) {
            for (int i = 0; i < this.collideableTilesIDs.length; i++) {
                if (tile.getID() == this.collideableTilesIDs[i]) {
                    tile.setCanCollide(true);
                }
            }
        }
    }

    public Tile getTile(String pos) {
        for (Tile tile : tileTerrain) {
            if (tile.getPos().equals(pos)) {
                return tile;
            }
        }
        return null;
    }

}
