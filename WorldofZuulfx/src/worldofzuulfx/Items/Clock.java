package worldofzuulfx.Items;

import worldofzuulfx.Game;
import worldofzuulfx.Player;

public class Clock extends Item {

    private int timeValue;

    /**
     * Instantiates an Clock-object based on the following parameters:
     *
     * @param ID The ID of the clock e.g. "clock"
     * @param description The description of the clock
     * @param weight The weight of the clock
     * @param time The time which the Player can receive when using the clock.
     */
    public Clock(String ID, String description, int weight, int time) {
        super(Game.tiles.get(101).clone().getImageView().getImage(), ID, description, weight);
        this.timeValue = time;

    }

    /**
     * The player who uses the clock receives the amount of time that the clock
     * contains and removes itself from the inventory.
     *
     * @param player The Player who uses the clock
     */
    @Override
    public void use(Player player) {
        player.setTimeLeft(player.getTimeLeft() + timeValue);
        player.getInventory().removeItem(this);
    }

    /**
     * @return the timeValue
     */
    public int getTimeValue() {
        return timeValue;
    }
}
