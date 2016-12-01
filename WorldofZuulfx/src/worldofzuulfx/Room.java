package worldofzuulfx;

/**
 * This class holds informations about a Room that was issued along the creation
 * of the game. Room takes one argument - a description; which used to describe
 * the current room. Room can set exits and get them as well.
 *
 */
import worldofzuulfx.Inventory.PlayerInventory;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashMap;
import javafx.scene.layout.Pane;
import worldofzuulfx.Inventory.Inventory;
import worldofzuulfx.Items.Item;
import worldofzuulfx.NPC.NPC;
import worldofzuulfx.tiles.Tile;
import worldofzuulfx.tiles.TileMap;

public class Room {

    private String description;
    private String id;
    private boolean locked;
    private HashMap<String, Room> exits;
    private Inventory roomInventory;
    private ArrayList<NPC> npcList;
    private Pane groundLayer;
    private Pane objectLayer;
    private final TileMap groundTiles;

    public Room(String ID, String description, Pane groundLayer, Pane objectLayer, HashMap<Integer, Tile> tiles, int[][] groundLayout) {
        // Constructor - defines the description of the room.
        this.id = ID;
        this.description = description;
        this.locked = false;
        // Create HashMap containing exit-String and a Room
        this.exits = new HashMap<>();
        this.roomInventory = new Inventory(Integer.MAX_VALUE, Integer.MAX_VALUE);
        this.npcList = new ArrayList<>();
        this.groundLayer = groundLayer;
        this.objectLayer = objectLayer;

        this.groundTiles = new TileMap(groundLayout, tiles);
    }

    public void setExit(String pos, Room room, Tile tile) {
        // Insert key and value into HashMap.
        //      exits.put(pos, event);
        groundTiles.getTile(pos).setTeleport(room, tile.getX(), tile.getY());
    }

    public String getShortDescription() {
        // Get short description (Getter-methode)
        return description;
    }

    public String getLongDescription() {
        // Get long description (Getter-methode)
        return "You are " + description + ".\n" + getExitString();
    }

    private String getExitString() {
        String returnString = "Exits:";
        // Get all keys from HashMap exits
        Set<String> keys = getExitsList().keySet();
        // Gather all keys into one String
        for (String exit : keys) {
            returnString += "  " + exit;
        }
        return returnString;
    }

    public Room getExit(String pos) {
        // Get neighbor room
        return getExitsList().get(pos);
    }

    public void addNPC(NPC p) {
        npcList.add(p);
    }

    public void removePerson(NPC p) {
        npcList.remove(p);
    }

    /**
     * @return the ID
     */
    public String getID() {
        return id;
    }

    public boolean isLocked() {
        return this.locked;
    }

    /**
     * @param Locked the Locked to set
     */
    public void setLocked(boolean Locked) {
        this.locked = Locked;
    }

    public ArrayList<NPC> getNPCList() {
        ArrayList<NPC> copy = new ArrayList<>(npcList);
        return copy;
    }

    public String getPersonsString() {
        String result;
        result = "";
        for (NPC person : npcList) {
            result += "  " + person.getName();
        }
        return result;
    }

    /**
     * @return the exits
     */
    public HashMap<String, Room> getExitsList() {
        return new HashMap<>(exits);
    }

    /**
     * @return the inventory
     */
    public Inventory getRoomInventory() {
        return roomInventory;
    }

    public NPC getNPC(String ID) {
        for (NPC npc : this.npcList) {
            if (npc.getID().equalsIgnoreCase(ID)) {
                return npc;
            }
        }
        return null;
    }

    public TileMap getTileMap() {
        return this.groundTiles;
    }

    public void drawItems() {
        // Clear the object layer before drawing new objects.
        objectLayer.getChildren().clear();
        // Draw Items in the room.
        for (Item item : roomInventory.getItemList()) {
            item.setLayer(objectLayer);
            item.addToLayer();
            item.updateUI();
        }
    }

    public void draw() {
        groundTiles.draw(groundLayer);

        drawItems();

        // Draw NPCs in the room.
        for (NPC npc : this.npcList) {
            npc.setLayer(objectLayer);
            npc.addToLayer();
            npc.updateUI();
        }
    }

    /**
     * @return the background
     */
    public Pane getBackground() {
        return groundLayer;
    }
}
