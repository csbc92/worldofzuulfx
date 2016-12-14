/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuulfx.Items;

import worldofzuulfx.ConsoleInfo;
import worldofzuulfx.Game;
import worldofzuulfx.Player;

public class CoffeeVoucher extends Item {

    private int voucherAmount;

    /**
     * Instantiates a CoffeeVoucher based on the following parameters.
     *
     * @param ID The ID of the CoffeeVoucher
     * @param description The description of the CoffeeVoucher
     * @param weight The weight of the coffeeVoucher
     * @param voucherAmount The number of times which the Voucher can be used
     */
    public CoffeeVoucher(String ID, String description, int weight, int voucherAmount) {
        super(Game.tiles.get(172).clone().getImageView().getImage(), ID, description, weight);
        this.voucherAmount = voucherAmount;

    }

    /**
     *
     * @return The voucher amount
     */
    public int getVoucherAmount() {
        return voucherAmount;
    }

    /**
     *
     * @return true if the voucher amount is greater than 0.
     */
    public Boolean checkAmount() {

        if (voucherAmount > 0) {
            return true;
        }
        return false;
    }

    /**
     * The Player can use the CoffeeVouacher when he finds himself in the
     * Canteen. When the CoffeeVoucher is used the voucherAmount is decremented
     * and if the voucherAmount is below one the CoffeeVoucher removes itself
     * from the inventory.
     *
     * @param player The player who uses the Coffee Voucher
     */
    @Override
    public void use(Player player) {
        if (player.getCurrentRoom().getID().equalsIgnoreCase("Canteen")) {
            if (this.checkAmount()) {
                Item coffee = ItemFactory.makeCoffee();
                if (player.getInventory().addItem(coffee)) {
                    voucherAmount--;
                    if (!this.checkAmount()) {
                        player.getInventory().removeItem(this);
                    }
                }
//            } else {
//                ConsoleInfo.setConsoleData("You have used up your Coffee Voucher.");
//                player.getInventory().removeItem(this);
            }
        }
    }
}
