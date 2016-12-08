package worldofzuulfx.Items;

import worldofzuulfx.ConsoleInfo;
import worldofzuulfx.Game;
import worldofzuulfx.Player;

public class Note extends Item {

    protected String content;

    /**
     * Instantiates a Note-object based on the following parameters:
     *
     * @param description The description of the Note e.g. "ISE-Note"
     * @param ID The ID of the Note
     * @param weight The weight of the Note
     * @param contentString The content of the Note.
     */
    public Note(String description, String ID, int weight, String contentString) {
        super(Game.tiles.get(142).clone().getImageView().getImage(), ID, description, weight);

        this.content = contentString;
    }

    /**
     *
     * @return The content of the Note
     */
    public String getContent() {
        return content;
    }

    /**
     * Displays the content of the Note in the console.
     * @param player
     */
    @Override
    public void use(Player player) {

        if (player.getCurrentRoom().getID().equals("exam")) {
            //TODO player cheated during exam
        } else {
            ConsoleInfo.setConsoleData(content);
        }
    }
}
