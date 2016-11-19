/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuulfx.Items;

import worldofzuulfx.Player;

/**
 *
 * @author march
 */
public class Computer extends Item {

    public Computer(String description, int weight) {
        super(description, weight);
    }

    @Override
    public void use(Player player) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
