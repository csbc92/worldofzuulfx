package worldofzuulfx.Events;

import worldofzuulfx.Player;
import worldofzuulfx.Room.Room;

public class NavigateEvent {
    
    private final Room oldRoom;
    private final Room newRoom;
    private final Player player;
    
    public NavigateEvent(Room oldRoom, Room newRoom, Player player) {
        this.oldRoom = oldRoom;
        this.newRoom = newRoom;
        this.player = player;
    }
    
    public Room getOldRoom() {
        return this.oldRoom;
    }
    
    public Room getNewRoom() {
        return this.newRoom;
    }
    
    public Player getPlayer() {
        return this.player;
    }
}
