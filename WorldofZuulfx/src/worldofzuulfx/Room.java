package worldofzuulfx;

/**
 * This class holds informations about a Room that was issued along the creation
 * of the game. Room takes one argument - a description; which used to describe
 * the current room. Room can set exits and get them as well.
 *
 */
import java.util.ArrayList;
import java.util.Set;
import java.util.HashMap;
import worldofzuulfx.NPC.NPC;

public class Room {

    private String description;
    private String ID;
    private boolean Locked;
    private HashMap<String, Room> exits;
    private Inventory roomInventory;
    private ArrayList<NPC> npcList;

    public Room(String ID, String description) {
        // Constructor - defines the description of the room.
        this.ID = ID;
        this.description = description;
        this.Locked = false;
        // Create HashMap containing exit-String and a Room
        exits = new HashMap<>();
        roomInventory = new Inventory();
        npcList = new ArrayList<>();
    }

    public void setExit(String direction, Room neighbor) {
        // Insert key and value into HashMap.
        exits.put(direction, neighbor);
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

    public Room getExit(String direction) {
        // Get neighbor room
        return getExitsList().get(direction);
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
        return ID;
    }

    public boolean isLocked() {
        return this.Locked;
    }

    /**
     * @param Locked the Locked to set
     */
    public void setLocked(boolean Locked) {
        this.Locked = Locked;
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

}
