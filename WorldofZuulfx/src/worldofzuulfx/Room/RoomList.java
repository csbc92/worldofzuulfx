package worldofzuulfx.Room;

import java.util.ArrayList;

public class RoomList {

    private ArrayList<Room> rooms;

    /**
     * This class present the extra functionality such as get a room by a
     * specific roomID Get all rooms and get all rooms based on whether it is
     * locked or not.
     *
     * @param rooms A list of rooms
     */
    public RoomList(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }

    /**
     * Get a specific room based on a roomID.
     *
     * @param ID The roomID
     * @return
     */
    public Room getRoom(String ID) {

        for (Room r : this.getRooms()) {
            if (r.getID().equalsIgnoreCase(ID)) {
                return r;
            }
        }

        return null;
    }

    /**
     * Get all rooms based on whether it is locked or not.
     *
     * @param lockedRooms True only locked rooms are returned otherwise only
     * unlocked rooms are returned.
     * @return
     */
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
     * @return The list of rooms.
     */
    public ArrayList<Room> getRooms() {
        return rooms;
    }
}
