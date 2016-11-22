/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuulfx.Items;

import javafx.scene.layout.Pane;

/**
 *
 * @author JV
 */
public class ItemFactory {

    public ItemFactory() {

    }

    public static Drink makeBeer() {

        return new Drink("beer", "Beer", 1, 1, true);
    }

    public static Drink makeCoffee(Pane layer) {

        return new Drink("coffee", "Coffee", 1, 10, false);
    }

    public static Computer makeComputer(Pane layer) {

        return new Computer("computer", "Computer", 10);
    }

    public static CoffeeVoucher makeCoffeeVoucher(Pane layer) {

        return new CoffeeVoucher("coffeeVoucher", "Coffee Voucher", 0, 10);
    }

    public static Book makeBook(Pane layer, String description) {

        return new Book(description, description, 5);
    }

    public static Note makeNote(Pane layer, String content) {

        return new Note("book", "Book", 5, content);
    }

}
