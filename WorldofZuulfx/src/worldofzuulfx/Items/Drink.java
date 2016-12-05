
package worldofzuulfx.Items;

import worldofzuulfx.ConsoleInfo;
import worldofzuulfx.Game;
import worldofzuulfx.Player;


public class Drink extends Item {

    private int energyValue;
    private boolean alcoholDrink;

    public Drink(String ID, String description, int weight, int energyValueVar, boolean alcoholicBeverage) {
        super(Game.tiles.get(158).clone().getImageView().getImage(), ID, description, weight);
        this.energyValue = energyValueVar;
        this.alcoholDrink = alcoholicBeverage;

    }

    public int getEnergyValue() {
        return energyValue;
    }

    public boolean isAlcohol() {
        return alcoholDrink;
    }

    @Override
    public void use(Player player) {
        if (player.getNearNPC() == null) {
            if (this.isAlcohol()) {
                player.setAlcoCounter(player.getAlcoCounter() + 1);
                // Mulitplying the value of the energy increment based on the alcoCounter, as a risk-reward bonus.
                player.increaseEnergy(this.getEnergyValue() * player.getAlcoCounter());
                // After incrementing on the energy it is checked whether the player has reached his Alcohol tolerance.
                player.setDrunk();
                player.barValueChanged(player.getEnergyBar());
            } else {
                player.increaseEnergy(this.getEnergyValue());
            }
            player.getInventory().removeItem(this);
            ConsoleInfo.setConsoleData(String.format("You drank the %s.", this.getDescription()));
        } else {
            // Deliver the item to NPC.  
            player.deliverItem(player.getNearNPC(), this);
        }
    }
}
