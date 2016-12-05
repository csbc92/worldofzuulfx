/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuulfx.Items;

import worldofzuulfx.Game;
import worldofzuulfx.Player;
import worldofzuulfx.Room.Room;
import worldofzuulfx.tiles.Tile;

/**
 *
 * @author JV
 */
public class Globus extends Item {

    private int spinAmount;
    private Room toRoom;
    private String nextPos;

    public Globus(String id, int spinAmount, Room toRoom, String nextPos) {
        super(Game.tiles.get(156).clone().getImageView().getImage(), id, "Globus", 1000);
        this.spinAmount = spinAmount;
        this.toRoom = toRoom;
        this.nextPos = nextPos;
    }

    /**
     * @return the spinAmount
     */
    public int getSpinAmount() {
        return spinAmount;
    }

    public Boolean checkAmount() {

        if (spinAmount > 0) {
            return true;
        }
        return false;
    }

    @Override
    public void use(Player player) {
        Tile nextTile;

        if (this.checkAmount()) {
            player.navigateTo(toRoom);
            nextTile = toRoom.getTileMap().getTile(nextPos);
            player.setNextPosX(nextTile.getX() + 1);
            player.setNextPosY(nextTile.getY() + 1);
            spinAmount--;

        } else {
            player.getInventory().removeItem(this);
        }

    }
}
