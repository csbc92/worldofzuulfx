
package worldofzuulfx.Items;

import worldofzuulfx.Player;

public class Book extends Item {

    public Book(String description, int weight){
        super(description, weight);
    };

    @Override
    public void use(Player player) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
