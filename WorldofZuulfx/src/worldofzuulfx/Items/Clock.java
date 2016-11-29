/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuulfx.Items;
import worldofzuulfx.Game;
import worldofzuulfx.Player;

/**
 *
 * @author JV
 */
public class Clock extends Item {

    private int timeValue;

    public Clock(String ID, String description, int weight, int time) {
        super(Game.tiles.get(56).clone().getImageView().getImage(), ID, description, weight);
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
