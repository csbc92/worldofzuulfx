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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
