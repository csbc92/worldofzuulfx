package worldofzuulfx.Items;

import javafx.scene.image.Image;
import worldofzuulfx.ConsoleInfo;
import worldofzuulfx.Game;
import worldofzuulfx.Player;

public class Book extends Item {

    /**
     * Instantiates an book-object based on the following parameters:
     *
     * @param ID The ID of the book e.g. "OOP-Book"
     * @param description The description of the book
     * @param weight The weight of the book
     * @param color The color of the book
     */
    public Book(String ID, String description, int weight, BookColor color) {
        super(Game.tiles.get(86).clone().getImageView().getImage(), ID, description, weight);

        Image bookImage = null;
        // Based on the selected color of the book an corresponding image is chosen.
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

    /**
     * If the player is near a NPC, the player will give the book to the NPC;
     * otherwise a text will be displayed: "Since when did you start reading
     * books?"
     *
     * @param player The Player who used the book
     */
    @Override
    public void use(Player player) {
        if (player.getNearNPC() != null) {
            // Deliver the item to NPC.  
            player.deliverItem(player.getNearNPC(), this);
        } else {
            ConsoleInfo.setConsoleData("Since when did you start reading books?");
        }
    }

    /**
     * The different colors of the book.
     * RED, BLUE, GREEN and ORANGE
     */
    public enum BookColor {
        RED, BLUE, GREEN, ORANGE
    }

}
