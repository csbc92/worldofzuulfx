package worldofzuulfx.Items;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import worldofzuulfx.Game;
import worldofzuulfx.Player;

public class Book extends Item {

    public Book(String ID, String description, int weight) {
        super(Game.tiles.get(60).clone().getImageView().getImage(), ID, description, weight);
    }

    ;

    @Override
    public void use(Player player) {
        //TODO
       
        if (player.getNearNPC() == null) {
            // Deliver the item to NPC.  
            player.deliverItem(player.getNearNPC(), this);
        }
    }

}
