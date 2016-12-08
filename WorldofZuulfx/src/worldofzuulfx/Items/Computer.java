package worldofzuulfx.Items;

import worldofzuulfx.ConsoleInfo;
import worldofzuulfx.Game;
import worldofzuulfx.Main;
import worldofzuulfx.Player;
import worldofzuulfx.Room.Room;

public class Computer extends Item {

    /**
     * Instantiates a Computer-object based on the following parameters:
     *
     * @param ID The ID of the Computer
     * @param description The description of the Computer
     * @param weight The weight of the computer
     */
    public Computer(String ID, String description, int weight) {
        super(Game.tiles.get(160).clone().getImageView().getImage(), ID, description, weight);
    }

    /**
     * The Player can use the Computer when he finds himself the Exam room which
     * will trigger the exam.(FXMLExamController) If the player tries to use the
     * Computer outside the Exam room a text will be displayed.
     *
     * @param player
     */
    @Override
    public void use(Player player) {

        Room currentRoom = player.getCurrentRoom();

        if (currentRoom.getID().equals("exam")) {
            // execute exam in the main controller
            Main.getController().executeExam();

        } else {
            ConsoleInfo.setConsoleData("You don't have time to browse Facebook at this time.\n"
                    + "The computer is only for use in the exam room.");
        }

    }
}
