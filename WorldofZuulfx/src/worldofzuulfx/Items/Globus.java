package worldofzuulfx.Items;

import worldofzuulfx.Game;
import worldofzuulfx.Player;
import worldofzuulfx.Room.Room;
import worldofzuulfx.tiles.Tile;

public class Globus extends Item {

    private int spinAmount;
    private Room toRoom;
    private String nextPos;

    /**
     * Instantiates a Globus-object based on the following parameters:
     *
     * @param id The ID of the Globus e.g. "globus"
     * @param spinAmount The amount of times which the Globus can be used.
     * @param toRoom The Room which the player is teleported to.
     * @param nextPos The position of room the player will be placed when he
     * teleports.
     */
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

    /**
     * @return true if the amount of spins is greater than 0.
     */
    public Boolean checkAmount() {

        if (spinAmount > 0) {
            return true;
        }
        return false;
    }

    /**
     * The Globus can be used in all rooms. Using the Globus will force the
     * player to teleport to a given rooms (toRoom)

     * @param player
     */
    @Override
    public void use(Player player) {
        Tile nextTile;

        if (this.checkAmount()) {
            player.navigateTo(toRoom);
            nextTile = toRoom.getTileTerrain().getTile(nextPos);
            player.setNextPosX(nextTile.getX() + 1);
            player.setNextPosY(nextTile.getY() + 1);
            spinAmount--;

        } else {
            player.getInventory().removeItem(this);
        }

    }
}
