/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuulfx.tiles;

import javafx.scene.image.Image;
import worldofzuulfx.Player;
import worldofzuulfx.sprites.SpriteBase;

/**
 *
 * A tile represent a part of a room, a item, a NPC or a Player.
 */
public class Tile extends SpriteBase {

    private int id;
    private int column;
    private int row;

    /**
     * Instantiates a Tile-object.
     * @param id The ID of the tile
     * @param img The image representing the tile.
     */
    public Tile(int id, Image img) {
        super(img);
        this.id = id;
    }

    /**
     * Define the postion of the tile in a grid.
     * @param column The x-axis
     * @param row The y-axis
     */
    public void setPos(int column, int row) {
        this.setColumn(column);
        this.setRow(row);
    }
    
    /**
     * Get the position of the tile as a String in the following format: "xxyy".
     * e.g. "0508" means five tiles to the right and eight tiles down.
     * @return The position as a String
     */
    public String getPos() {
        String c = String.valueOf(column);
        String r = String.valueOf(row);
        String result = String.format("%2s", c).replace(' ', '0') + String.format("%2s", r).replace(' ', '0') ;
        return result;
    }

    /**
     *
     * @return The ID of the tile
     */
    public int getID() {
        return this.id;
    }

    /**
     *
     * @return A copy of the tile.
     */
    public Tile clone() {

        return new Tile(this.id, this.getImageView().getImage());
    }

    /**
     * @return The column
     */
    public int getColumn() {
        return column;
    }

    /**
     * @param column the column to set
     */
    public void setColumn(int column) {
        this.column = column;
    }

    /**
     * @return the row
     */
    public int getRow() {
        return row;
    }

    /**
     * @param row the row to set
     */
    public void setRow(int row) {
        this.row = row;
    }

    @Override
    public void collides(SpriteBase spriteBase) {
        
        if (spriteBase instanceof Player) {
            Player player = (Player)spriteBase;
            
            if (this.canTeleport() && player.navigateTo(this.getNextRoom())) {
                // The Player needs to be moved with the offset 1.
                Tile nextTile = this.getNextRoom().getTileTerrain().getTile(this.getNextPos());
                player.setX(nextTile.getX() + 1);
                player.setY(nextTile.getY() + 1);
            }

            // Reset the nextPos since a collision was detected
            player.move(player.getX(), player.getY());
        }
    }
}
