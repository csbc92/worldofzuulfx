/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuulfx.Items;

import javafx.scene.layout.Pane;
import worldofzuulfx.Game;
import worldofzuulfx.tiles.Tile;

/**
 *
 * @author JV
 */
public class ItemFactory {

    public ItemFactory() {
        //TODO Skifte image på samtlige items.
    }

    public static Drink makeBeer() {
        Drink drink = new Drink("beer", "Beer", 1, 1, true);
        // Get the image that represents a beer.
        Tile tile = Game.tiles.get(171);
        //
        drink.getImageView().imageProperty().set(tile.getImageView().getImage());
        return drink;
    }

    public static Drink makeCoffee() {

        return new Drink("coffee", "Coffee", 1, 10, false);
    }

    public static Computer makeComputer() {

        return new Computer("computer", "Computer", 10);
    }

    public static CoffeeVoucher makeCoffeeVoucher() {

        return new CoffeeVoucher("coffeeVoucher", "Coffee Voucher", 0, 10);
    }

    public static Book makeBook(String description) {

        return new Book(description, description, 5);
    }

    public static Note makeNote(String content) {

        return new Note("book", "Book", 5, content);
    }

}
