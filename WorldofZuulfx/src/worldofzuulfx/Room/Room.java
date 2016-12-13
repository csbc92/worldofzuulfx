package worldofzuulfx.Room;

/**
 * This class holds informations about a Room that was issued along the creation
 * of the game. Room takes one argument - a description; which used to describe
 * the current room. Room can set exits and get them as well.
 *
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import javafx.scene.layout.Pane;
import worldofzuulfx.Inventory.Inventory;
import worldofzuulfx.Items.Item;
import worldofzuulfx.NPC.NPC;
import worldofzuulfx.sprites.SpriteBase;
import worldofzuulfx.tiles.Tile;
import worldofzuulfx.tiles.TileTerrain;

public class Room {

    private String description;
    private String id;
    private boolean locked;

    private Inventory roomInventory;
    private ArrayList<NPC> npcList;
    private Pane groundLayer;
    private Pane objectLayer;
    private final TileTerrain groundTiles;

    /**
     * Instantiates a Room-object based on the following parameters: A Player
     * can walk in a room, find items, pick items up and use them.
     *
     * @param ID The ID of the room
     * @param description The description of the room
     * @param groundLayer The layer which the room is drawn on excl. items,
     * Player and NPCs.
     * @param objectLayer The layer which items is drawn on.
     * @param tiles The images which represents parts of a room, NPCs, Items and
     * Player.
     * @param groundLayout Describes the layout of the room.
     */
    public Room(String ID, String description, Pane groundLayer, Pane objectLayer, HashMap<Integer, Tile> tiles, int[][] groundLayout) {
        this.id = ID;
        this.description = description;
        this.locked = false;
        this.roomInventory = new Inventory(Integer.MAX_VALUE, Integer.MAX_VALUE);
        this.npcList = new ArrayList<>();
        this.groundLayer = groundLayer;
        this.objectLayer = objectLayer;

        this.groundTiles = new TileTerrain(groundLayout, tiles);
    }

    /**
     * Sets a exit which will navigate the player to a given room and position
     * in the room.
     *
     * @param pos The Tile position which the player is to be placd. e.g. "0508"
     * In other word five tiles to the right and eight tiles down. It starts at
     * index 0000;
     * @param room The room which the player will be navigated to.
     * @param nextPos The position in the room
     */
    public void setExit(String pos, Room room, String nextPos) {
        groundTiles.getTile(pos).setTeleport(room, nextPos);
    }

    /**
     *
     * @return A short of the description room
     */
    public String getShortDescription() {
        return description;
    }

    /**
     *
     * @return "You are "shortDescription"
     */
    public String getLongDescription() {
        // Get long description (Getter-methode)
        return "You are " + description + ".\n";
    }

    /**
     * Add a NPC to the NPClist. The NPCList contains all NPCs to be found in the room
     * @param p The NPC
     */
    public void addNPC(NPC p) {
        npcList.add(p);
    }

    /**
     * Remove a NPC from the room
     * @param p The NPC to be removed.
     */
    public void removePerson(NPC p) {
        npcList.remove(p);
    }

    /**
     * @return the ID
     */
    public String getID() {
        return id;
    }

    /**
     * The Player can not navigate to a room which is locked.
     * @return True if the rooms is locked otherwise false.
     */
    public boolean isLocked() {
        return this.locked;
    }

    /**
     * @param Locked the Locked to set
     */
    public void setLocked(boolean Locked) {
        this.locked = Locked;
    }

    /**
     *
     * @return A list of NPCs
     */
    public ArrayList<NPC> getNPCList() {
        ArrayList<NPC> copy = new ArrayList<>(npcList);
        return copy;
    }
    
    /**
     * @return the inventory
     */
    public Inventory getRoomInventory() {
        return roomInventory;
    }

    /**
     * Get a NPC based on the NPC ID
     * @param ID The ID of the NPC
     * @return NPC
     */
    public NPC getNPC(String ID) {
        for (NPC npc : this.npcList) {
            if (npc.getID().equalsIgnoreCase(ID)) {
                return npc;
            }
        }
        return null;
    }

    /**
     * The layer which the room is drawn on excl. items,
     * Player and NPCs.
     * @return
     */
    public TileTerrain getTileTerrain() {
        return this.groundTiles;
    }

    /**
     * Draw all items found in the roomInventory on to the room
     */
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

    /**
     * Draw the room, items and NPCs.
     */
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
    
    public Set<SpriteBase> getAllSpriteBases() {
        Set<SpriteBase> allSpriteBases = new HashSet<>();
        
        allSpriteBases.addAll(npcList);
        allSpriteBases.addAll(roomInventory.getItemList());
        allSpriteBases.addAll(groundTiles.getTileTerrain());
        
        return allSpriteBases;
    }
}
