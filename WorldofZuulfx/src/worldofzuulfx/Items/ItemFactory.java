package worldofzuulfx.Items;

import worldofzuulfx.Game;
import worldofzuulfx.Room.Room;
import worldofzuulfx.tiles.Tile;

/**
 * This class is used to create different item-objects e.g. Drink and Clock Its
 * methods is static which gives the option to create an Item-object in any
 * class.
 */
public class ItemFactory {

    public ItemFactory() {
    }

    /**
     * Instantiates a Drink-object with parameters of a beer. E.g. a image that
     * represents a beer.
     *
     * @return A beer
     */
    public static Drink makeBeer() {
        Drink drink = new Drink("beer", "Beer", 1, 25, true);
        // Get the image that represents a beer.
        Tile tile = Game.tiles.get(171);
        drink.getImageView().imageProperty().set(tile.getImageView().getImage());
        return drink;
    }

    /**
     * Instantiates a Drink-object with parameters of a coffee. E.g. a image
     * that represents a coffee(default image) and the energy of a coffee.
     *
     * @return a Coffee
     */
    public static Drink makeCoffee() {

        return new Drink("coffee", "Coffee", 1, 10, false);
    }

    /**
     * Instantiates a Computer-object with predefined parameeters.
     *
     * @return A computer
     */
    public static Computer makeComputer() {

        return new Computer("computer", "Computer", 10);
    }

    /**
     * Instantiates a CoffeeVoucher-object with predefined parameters. E.g.
     * VoucherAmount = 10;
     *
     * @return A coffee voucher
     */
    public static CoffeeVoucher makeCoffeeVoucher() {

        return new CoffeeVoucher("coffeeVoucher", "Coffee Voucher", 0, 10);
    }

    /**
     * Instantiates a Book-object with a predefined weight
     *
     * @param description The desciption of the Book. In this case the
     * description as the ID as well.
     * @param color The color of the Book
     * @return A book
     */
    public static Book makeBook(String description, Book.BookColor color) {

        return new Book(description, description, 500, color);
    }

    /**
     * Instantiates a Note-object with a predefined weight
     *
     * @param noteID The ID of the Note
     * @param description The description of the Note
     * @param content The content of the Note.
     * @return A note based on the given paramters.
     */
    public static Note makeNote(String noteID, String description, String content) {

        return new Note(description, noteID, 300, content);
    }

    /**
     * Instantiates a Clock-object with predefined parameters: ID, description
     * and weight
     *
     * @param time The time value of the Clock.
     * @return A clock
     */
    public static Clock makeClock(int time) {

        return new Clock("clock", "Clock", 300, time);
    }

    /**
     * Instantiates a Globus-object with a predefined ID.
     *
     * @param spins The amount of times which the Globus can be used.
     * @param toRoom The Room which the player is teleported to.
     * @param nextPos The position of room the player will be placed when he
     * teleports.
     * @return A globus
     */
    public static Globus makeGlobus(int spins, Room toRoom, String nextPos) {

        return new Globus("globus", spins, toRoom, nextPos);
    }

}
