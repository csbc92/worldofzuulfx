/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuulfx.Items;

import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import worldofzuulfx.ConsoleInfo;
import worldofzuulfx.Game;
import worldofzuulfx.Player;

/**
 *
 * @author march
 */
public class CoffeeVoucher extends Item {

    protected int voucherAmount;

    public CoffeeVoucher(String ID, String description, int weight, int voucherAmountVar) {
        super(Game.tiles.get(60).clone().getImageView().getImage(), ID, description, weight);
        voucherAmount = voucherAmountVar;

    }

    public int getVoucherAmount() {
        return voucherAmount;
    }

    /**
     * Use the Coffee Voucher by decrementing the total amount of vouchers by 1.
     *
     * @return true if the voucher amount is greater than 0.
     */
    public Boolean checkAmount() {

        if (voucherAmount > 0) {
            return true;
        }
        return false;
    }

    @Override
    public void use(Player player) {
        if (player.getCurrentRoom().getID().equalsIgnoreCase("Canteen")) {
            if (this.checkAmount()) {
                if (player.getInventory().addItem(ItemFactory.makeCoffee())) {
                 voucherAmount--;   
                }         
            } else {
                ConsoleInfo.setConsoleData("You have used up your Coffee Voucher.");
                player.getInventory().removeItem(this);
            }
        }
    }
}
