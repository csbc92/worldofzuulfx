package worldofzuulfx.Items;

import worldofzuulfx.ConsoleInfo;
import worldofzuulfx.Game;
import worldofzuulfx.Player;

public class Drink extends Item {

    private int energyValue;
    private boolean alcoholDrink;

    /**
     * Instantiates a Drink-object based on the following parameters:
     *
     * @param ID The ID of the Drink e.g. "beer"
     * @param description The description of the drink.
     * @param weight The weight of the drink.
     * @param energyValue The energy of the drink.
     * @param alcoholicBeverage Decides whether the drink contains alcohol or
     * not.
     */
    public Drink(String ID, String description, int weight, int energyValue, boolean alcoholicBeverage) {
        super(Game.tiles.get(158).clone().getImageView().getImage(), ID, description, weight);
        this.energyValue = energyValue;
        this.alcoholDrink = alcoholicBeverage;

    }

    /**
     *
     * @return The energy of the drink
     */
    public int getEnergyValue() {
        return energyValue;
    }

    /**
     *
     * @return True if the drink contains alcohol otherwise false.
     */
    public boolean isAlcohol() {
        return alcoholDrink;
    }

    /**
     * If the player is near a NPC, the player will give the drink to the NPC;
     * otherwise the player will drink the drink.
     * @param player
     */
    @Override
    public void use(Player player) {
        if (player.getNearNPC() == null) {
            if (this.isAlcohol()) {
                player.setAlcoCounter(player.getAlcoCounter() + 1);
                // Mulitplying the value of the energy increment based on the alcoCounter, as a risk-reward bonus.
                player.increaseEnergy(this.getEnergyValue() * player.getAlcoCounter());
                // After incrementing on the energy it is checked whether the player has reached his Alcohol tolerance.
                //player.setDrunk();
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
