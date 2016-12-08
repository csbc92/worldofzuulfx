/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuulfx.Room;

import java.util.ArrayList;

public class RoomList {
    private ArrayList<Room> rooms;
    
    public RoomList(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }

    public Room getRoom(String ID) {

        for (Room r : this.getRooms()) {
            if (r.getID().equalsIgnoreCase(ID)) {
                return r;
            }
        }

        return null;
    }

    public ArrayList<Room> getRooms(Boolean lockedRooms) {
        ArrayList<Room> roomsToReturn = new ArrayList<>();
        for (Room room : this.getRooms()) {
            if (room.isLocked() == lockedRooms) {
                roomsToReturn.add(room);
            }
        }
        return roomsToReturn;
    }

    /**
     * @return the rooms
     */
    public ArrayList<Room> getRooms() {
        return rooms;
    }    
}
