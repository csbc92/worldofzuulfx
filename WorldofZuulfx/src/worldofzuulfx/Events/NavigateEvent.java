package worldofzuulfx.Events;

import worldofzuulfx.Player;
import worldofzuulfx.Room.Room;

public class NavigateEvent {
    
    private final Room oldRoom;
    private final Room newRoom;
    private final Player player;
    
    /**
     * An event containing information about the navigation of a player
     * @param oldRoom The room which the player came from
     * @param newRoom The room which the player navigated to
     * @param player The player who navigated,
     */
    public NavigateEvent(Room oldRoom, Room newRoom, Player player) {
        this.oldRoom = oldRoom;
        this.newRoom = newRoom;
        this.player = player;
    }
    
    /**
     *
     * @return Old Room
     */
    public Room getOldRoom() {
        return this.oldRoom;
    }
    
    /**
     *
     * @return New room
     */
    public Room getNewRoom() {
        return this.newRoom;
    }
    
    /**
     *
     * @return The Player
     */
    public Player getPlayer() {
        return this.player;
    }
}
