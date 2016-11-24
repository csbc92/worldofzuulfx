/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuulfx.tiles;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import worldofzuulfx.Room;
import worldofzuulfx.sprites.SpriteBase;

/**
 *
 * @author cclausen
 */
public class Tile extends SpriteBase {

    private int id;
    private int column;
    private int row;

    public Tile(int id, Image img) {
        super(img);
        this.id = id;

    }

    public void setPos(int column, int row) {
        this.setColumn(column);
        this.setRow(row);
    }
    
    public String getPos() {
        String c = String.valueOf(column);
        String r = String.valueOf(row);
        String result = String.format("%2s", c).replace(' ', '0') + String.format("%2s", r).replace(' ', '0') ;
        return result;
    }

    public int getID() {
        return this.id;
    }

    public Tile clone() {

        return new Tile(this.id, this.getImageView().getImage());
    }

    /**
     * @return the column
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
}
