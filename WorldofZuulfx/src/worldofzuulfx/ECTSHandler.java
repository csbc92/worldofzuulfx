/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuulfx;

import worldofzuulfx.Room.Room;
import worldofzuulfx.Interfaces.BarValueListener;

/**
 *
 * @author cclausen
 */
public class ECTSHandler implements BarValueListener {

    private final Room examRoom;
    
    public ECTSHandler(Player player, Room examRoom) {
        // Subscribe to the ECTS-bar when the value change
        player.getECTSBar().addBarValueListener(this);
        
        // Save a reference to the exam room
        this.examRoom = examRoom;
    }
    
    @Override
    public void barValueChanged(Bar bar) {  
        ConsoleInfo.setECTSData(String.valueOf(bar.getValue()));
        // Open the exam room if the player has enough ECTS-points
        if (bar.getValue() >= 30) {
            examRoom.setLocked(false);
        }
    }
    
}
