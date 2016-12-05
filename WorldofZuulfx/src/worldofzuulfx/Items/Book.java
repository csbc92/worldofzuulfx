package worldofzuulfx.Items;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import worldofzuulfx.ConsoleInfo;
import worldofzuulfx.Game;
import worldofzuulfx.Player;

public class Book extends Item {
    
    public Book(String ID, String description, int weight, BookColor color) {
        super(Game.tiles.get(86).clone().getImageView().getImage(), ID, description, weight);
        
        Image bookImage = null;
        
        switch (color) {
            case BLUE:
                bookImage = Game.tiles.get(100).clone().getImageView().getImage();
                break;
            case GREEN:
                bookImage = Game.tiles.get(114).clone().getImageView().getImage();
                break;
            case RED:
                bookImage = Game.tiles.get(86).clone().getImageView().getImage();
                break;
            case ORANGE:
                bookImage = Game.tiles.get(128).clone().getImageView().getImage();
                break;
        }
        
        this.setImage(bookImage);
    }

    @Override
    public void use(Player player) {      
        if (player.getNearNPC() != null) {
            // Deliver the item to NPC.  
            player.deliverItem(player.getNearNPC(), this);
        } else {
            ConsoleInfo.setConsoleData("Since when did you start reading books?");
        }
    }
    
    public enum BookColor {
        RED, BLUE, GREEN, ORANGE
    }

}
