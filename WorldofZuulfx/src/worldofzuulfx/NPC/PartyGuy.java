package worldofzuulfx.NPC;

import worldofzuulfx.Player;
import worldofzuulfx.Room.Room;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.image.Image;
import worldofzuulfx.ConsoleInfo;
import worldofzuulfx.Inventory.Inventory;
import worldofzuulfx.Items.Item;
import worldofzuulfx.Items.ItemFactory;
import worldofzuulfx.Minigame.RockPaperScissors;
import worldofzuulfx.sprites.SpriteBase;

/**
 *
 */
public class PartyGuy extends NPC {

    int[] partyRNG = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    private Inventory inventory;
    private RockPaperScissors RPS;
    private Thread rpsThread;

    public PartyGuy(String ID, String name, Image img) {
        super(ID, name, img);
        // The inventory can carry an item which weighs 10000 grams.
        // However, the inventory can only contain one item at the time.
        inventory = new Inventory(10000, 1);
    }

    /**
     * Adds partyguy to a random room and makes sure that PartyGoy always has a
     * clock on him.
     *
     * @param rooms The list of rooms which the PartyGuy can spawn in.
     */
    public void spawn(ArrayList<Room> rooms) {
        int random;
        random = (int) (Math.random() * rooms.size());
        this.navigateTo(rooms.get(random));
        inventory.addItem(ItemFactory.makeClock(30));
    }

    /**
     *
     * @return The one item found in the NPC's inventory.
     */
    public Item giveItem() {
        Item item;
        if (inventory.getSize() > 0) {
            item = inventory.getItem(0);
            inventory.removeItem(item);
            return item;
        }
        return null;
    }

    /**
     *
     * @return The Rock, Paper, Scissors game
     */
    public RockPaperScissors getRPS() {
        return RPS;
    }

    @Override
    public void collides(SpriteBase spriteBase) {
        super.collides(spriteBase);

        if (spriteBase instanceof Player) {
            Player player = (Player) spriteBase;

            if (player.getCurrentRoom().getID().equals("downunder")) {
                challenge(player);
            } else if (!player.getInventory().addItem(this.giveItem())) {
                ConsoleInfo.setConsoleData("You have already received your clock or you don't have enough space in your inventory");
            }

        }
    }

    /**
     * The command to start the minigame if the player is within downunder
     *
     * @param command
     */
    private void challenge(Player player) {

        player.setCanMove(false);
        RPS = new RockPaperScissors();

        rpsThread = new Thread() {
            // runnable for that thread
            public void run() {

                ConsoleInfo.setConsoleData("Rock (R), Paper (P) or Scissors (S)");
                // .play() waits for user-input.
                while (RPS.getPlayerMove() == null) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
                RPS.play();

                Platform.runLater(new Runnable() {

                    public void run() {
                        if (RPS.getMoveComparison() == 1) {
                            if (!player.getInventory().addItem(ItemFactory.makeBeer())) {
                                ConsoleInfo.setConsoleData("You don't have enough space to get your reward!");
                            }
                        }
                        if (RPS.getMoveComparison() == -1) {
                            player.increaseEnergy(-30);
                        }
                        player.setCanMove(true);
                    }

                });
            }
        };
        rpsThread.start();
    }
}
