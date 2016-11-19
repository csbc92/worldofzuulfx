/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuulfx.Items;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import worldofzuulfx.ConsoleInfo;
import worldofzuulfx.Main;
import worldofzuulfx.NPC.NPC;
import worldofzuulfx.Player;
import worldofzuulfx.Room;
import worldofzuulfx.Styles;
import worldofzuulfx.Util;


/**
 *
 * @author JV
 */
public class Drink extends Item {

    private int energyValue;
    private boolean alcoholDrink;

    public Drink(String description, int weight, int energyValueVar, boolean alcoholicBeverage) {
        super(description, weight);
        this.energyValue = energyValueVar;
        this.alcoholDrink = alcoholicBeverage;

    }

    public int getEnergyValue() {
        return energyValue;
    }
    public boolean isAlcohol(){
        return alcoholDrink;
    }

    @Override
    public void use(Player player) {
        
        ArrayList<String> options;
        String input;
        Room currentRoom = player.getCurrentRoom();
        // TODO
        options = Main.getGame().getNPCNameList(currentRoom);
    
        options.add("drink");
        ConsoleInfo.setConsoleData(String.format("Give the %s to a person in the room or drink it.", this.getDescription()));

        ConsoleInfo.setConsoleData(Util.arrayToString(options));
        ConsoleInfo.setConsoleData(">");
        // TODO
        input = "Get input";
        //The For-each loop iterates through the Options (e.g Drink   Anders   Daniel)
        for (String option : options) {
            // Then checks if the option equals the userInput.
            if (option.equalsIgnoreCase(input)) {
                // Then checks if the userInput equals the Drink options, which was added in the begining.
                if (input.equalsIgnoreCase(options.get(options.size() - 1))) {
                    if (this.isAlcohol()) {
                        player.setAlcoCounter(player.getAlcoCounter()+1);
                        // Mulitplying the value of the energy increment based on the alcoCounter, as a risk-reward bonus.
                        player.increaseEnergy(this.getEnergyValue()*player.getAlcoCounter());
                        // After incrementing on the energy it is checked whether the player has reached his Alcohol tolerance.
                        player.setDrunk();
                        player.barValueChanged(player.getEnergyBar());
                        System.out.println(player.getEnergy());
                    } else {
                        player.increaseEnergy(this.getEnergyValue());
                    }
                    
                    player.getInventory().removeItem(this);
                    ConsoleInfo.setConsoleData(String.format("You drank the %s.", this.getDescription()));
                    try {
                        Thread.sleep(800);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
                    }             
                } else {
                    // Deliver the item to NPC.  
                    NPC npc = currentRoom.getNPC(input);
                    if (npc != null) {
                        player.deliverItem(npc, this);
                    }
                }
            }
        }
    }
}
