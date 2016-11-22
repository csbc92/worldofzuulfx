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

    public static Drink makeBeer(Pane layer) {

        return new Drink(layer, "beer", "Beer", 1, 1, true);
    }

    public static Drink makeCoffee(Pane layer) {

        return new Drink(layer, "coffee", "Coffee", 1, 10, false);
    }

    public static Computer makeComputer(Pane layer) {

        return new Computer(layer, "computer", "Computer", 10);
    }

    public static CoffeeVoucher makeCoffeeVoucher(Pane layer) {

        return new CoffeeVoucher(layer, "coffeeVoucher", "Coffee Voucher", 0, 10);
    }

    public static Book makeBook(Pane layer, String description) {

        return new Book(layer, description, description, 5);
    }

    public static Note makeNote(Pane layer, String content) {

        return new Note(layer, "book", "Book", 5, content);
    }

}
