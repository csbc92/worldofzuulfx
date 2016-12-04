/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuulfx.Items;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import worldofzuulfx.ConsoleInfo;
import worldofzuulfx.Game;
import worldofzuulfx.Player;

public class Note extends Item {

    protected String content;

    public Note(String description, String ID, int weight, String contentString) {
        super(Game.tiles.get(142).clone().getImageView().getImage(), ID, description, weight );
        
        content = contentString;
    }

    public String getContent() {
        return content;
    }

    @Override
    public void use(Player player) {
        
        if (player.getCurrentRoom().getID().equals("exam")) {
            //TODO player cheated during exam
        } else {
            ConsoleInfo.setConsoleData(content);
        }
    }
}
