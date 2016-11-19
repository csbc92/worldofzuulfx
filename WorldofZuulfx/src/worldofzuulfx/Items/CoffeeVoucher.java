/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuulfx.Items;

import worldofzuulfx.ConsoleInfo;
import worldofzuulfx.Player;




/**
 *
 * @author march
 */
public class CoffeeVoucher extends Item {

    protected int voucherAmount;

    public CoffeeVoucher(String description, int weight, int voucherAmountVar) {
        super(description, weight);
        voucherAmount = voucherAmountVar;

    }

    public int getVoucherAmount() {
        return voucherAmount;
    }

    /**
     *  Use the Coffee Voucher by decrementing the total amount of vouchers by 1.
     * @return true if the voucher amount is greater than 0.
     */
    public Boolean decrementAmount() {

        if (voucherAmount > 0) {
            voucherAmount--;
            return true;
        }
        return false;
    }

    @Override
    public void use(Player player) {
        if (player.getCurrentRoom().getID().equalsIgnoreCase("Canteen")) {
                if (this.decrementAmount()) {
                    player.pickupItem(new Coffee("Coffee", 250, 40, false));
                } else {
                    ConsoleInfo.setConsoleData("You have used up your Coffee Voucher.");
                    player.getInventory().removeItem(this);
                }
            } else {
                ConsoleInfo.setConsoleData("The Coffee Voucher can be used in the canteen.");
        }
    }
}
