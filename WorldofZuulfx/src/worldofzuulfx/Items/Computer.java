/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuulfx.Items;

import worldofzuulfx.ConsoleInfo;
import worldofzuulfx.Game;
import worldofzuulfx.Main;
import worldofzuulfx.Player;
import worldofzuulfx.Room.Room;

/**
 *
 * @author march
 */
public class Computer extends Item {

    public Computer( String ID, String description, int weight) {
        super(Game.tiles.get(160).clone().getImageView().getImage(), ID, description, weight);
    }

    @Override
    public void use(Player player) {
        
        Room currentRoom = player.getCurrentRoom();
        
        if (currentRoom.getID().equals("exam")) {
            // execute exam in the main controller
            Main.getController().executeExam();
            
        } else {
            ConsoleInfo.setConsoleData("You don't have time to browse Facebook at this time.\n" +
                                       "The computer is only for use in the exam room.");
        }
        
    }
}
