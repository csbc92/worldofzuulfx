package worldofzuulfx.tiles;

import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.layout.Pane;

public class TileTerrain {

    private final HashMap<Integer, Tile> uniqTiles;
    private final ArrayList<Tile> terrain;
    private final int[][] tileTerrainLayout;
    private final int[] nonCollideableTilesIDs;
    private final static int TILEWIDTH = 32;
    private final static int TILEHEIGHT = 32;

    public TileTerrain(int[][] tileTerrainLayout, HashMap<Integer, Tile> uniqTiles) {
        this.tileTerrainLayout = tileTerrainLayout;
        // Make a copy of the uniq tiles.
        this.uniqTiles = new HashMap<>(uniqTiles);
        // Prepare a new terrain of tiles.
        this.terrain = new ArrayList<>();
        // This is the ID's of tiles that objects can collide with.
        this.nonCollideableTilesIDs = new int[]{6, 7, 8, 19, 20, 21, 33, 34, 35, 153, 167, 184, 186, 201, 215};
        
//        this.collideableTilesIDs = new int[]{0, 12, 13, 85, 99, 112, 126, 133, 134, 137, 138, 139, 140, 141, 147, 148, 149, 150, 154, 155, 
//                                            168, 169, 185, 198, 202, 203, 204, 210, 213, 214, 216, 217, 218};
// Load the tile terrain.
        load();
    }

    public void draw(Pane pane) {
        
        // Clear the pane before drawing the map.
        pane.getChildren().clear();
        
        // Draw each tile in the tile-terrain.
        for (Tile tile : terrain) {
            tile.setLayer(pane);
            pane.getChildren().add(tile.getImageView());
            tile.updateUI();
        }
    }

    private void load() {
        // Offsets for the next tile's x and y position.
        int xOffset = 0;
        int yOffset = 0;
        
        // Loop the 2-dimensional array of tile-id's
        // starting with looping each row, then each column.
        for (int row = 0; row < getTileTerrainLayout().length; row++) {
            for (int column = 0; column < getTileTerrainLayout()[row].length; column++) {
                
                // Get the tile-id for the current row/column.
                int tileId = getTileTerrainLayout()[row][column];
                // Make a clone of that exact tile, since each tile is representet
                // by its own object.
                Tile tile = this.uniqTiles.get(tileId).clone();
                // Set collision explicitly to true.
                tile.setCanCollide(true);
                
                // Save the row/column position
                tile.setPos(column, row);
                
                // Add the tile to the tile-terrain.
                terrain.add(tile);

                // Set the tile's actual x/y position.
                tile.setX(xOffset);
                tile.setY(yOffset);
                
                // Increase the xOffset that the tile in the next column should use.
                xOffset += TileTerrain.TILEWIDTH;
            }
            // Reset the Xoffset, since we start at a new row.
            xOffset = 0;
            // Increase the yOffset that the tile in the row should use.
            yOffset += TileTerrain.TILEHEIGHT;

        }
        
        // Set collision (false) if tile id's match the collision id's.
        for (Tile tile : terrain) {
            for (int i = 0; i < this.nonCollideableTilesIDs.length; i++) {
                if (tile.getID() == this.nonCollideableTilesIDs[i]) {
                    tile.setCanCollide(false);
                }
            }
        }
    }

    /**
     * Get a specific tile based on specific tile position in the format "xxyy".
     * @param pos
     * @return A tile.
     */
    public Tile getTile(String pos) {
        for (Tile tile : terrain) {
            if (tile.getPos().equals(pos)) {
                return tile;
            }
        }
        return null;
    }

    /**
     * @return the tileTerrainLayout
     */
    public int[][] getTileTerrainLayout() {
        return tileTerrainLayout;
    }
    
    /**
     * @return the tileTerrain
     */
    public ArrayList<Tile> getTileTerrain() {
        return terrain;
    }
}
