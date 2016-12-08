package worldofzuulfx.tiles;

import java.util.HashMap;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 *
 * This class loads all tiles from a image containing all tiles used throughout
 * the game.
 */
public class TileLoader {

    private final int tileHeight;
    private final int tileWidth;
    private final Image tilesImg;
    private int tileCounter;

    /**
     * Instantiates a TileLoader-object with the following parameters:
     *
     * @param tilesImg The image which contains all images used throughtout the
     * game.
     * @param tileHeight The height of the tiles used in the tilesImg.
     * @param tileWidth The width of the tiles used in the tilesImg.
     */
    public TileLoader(Image tilesImg, int tileHeight, int tileWidth) {
        this.tileHeight = tileHeight;
        this.tileWidth = tileWidth;
        this.tilesImg = tilesImg;
        this.tileCounter = 0;
    }

    /**
     * Reads the image and generates Map containg each tiles with a
     * corresponding tileID.
     *
     * @return The map containing the tiles.
     */
    public HashMap<Integer, Tile> getTiles() {
        HashMap<Integer, Tile> tiles = new HashMap<>();

        for (int x = 0; x < this.tilesImg.getWidth(); x += tileWidth) {
            for (int y = 0; y < this.tilesImg.getHeight(); y += tileHeight) {
                Tile tile = this.getTile(x, y);
                tiles.put(tile.getID(), tile);
            }
        }

        return tiles;
    }

    /**
     * Copies a part of the tileImg based on a startposition, the tileWidth and
     * the tileHeight.
     *
     * @param xOffset Start x-posiion.
     * @param yOffset Start y-position.
     * @return A tile
     */
    private Tile getTile(int xOffset, int yOffset) {
        PixelReader pReader = tilesImg.getPixelReader();

        WritableImage dest = new WritableImage(this.tileWidth, this.tileHeight);
        PixelWriter writer = dest.getPixelWriter();

        for (int x = xOffset; x < (xOffset + this.tileWidth); x++) {
            for (int y = yOffset; y < (yOffset + this.tileHeight); y++) {

                // reading a pixel from src image,
                // then writing a pixel to dest image
                Color color = pReader.getColor(x, y);
                writer.setColor(x - xOffset, y - yOffset, color);
            }
        }

        Tile tile = new Tile(this.tileCounter, dest);
        this.tileCounter++;

        return tile;
    }
}
