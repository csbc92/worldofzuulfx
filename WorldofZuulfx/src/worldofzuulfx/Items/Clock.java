
package worldofzuulfx.Items;
import worldofzuulfx.Game;
import worldofzuulfx.Player;


public class Clock extends Item {

    private int timeValue;

    public Clock(String ID, String description, int weight, int time) {
        super(Game.tiles.get(101).clone().getImageView().getImage(), ID, description, weight);
        this.timeValue = time;

    }

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
