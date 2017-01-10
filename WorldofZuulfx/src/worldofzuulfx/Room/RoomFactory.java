package worldofzuulfx.Room;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;
import javafx.scene.layout.Pane;
import worldofzuulfx.Items.Book;
import worldofzuulfx.Items.Item;
import worldofzuulfx.Items.ItemFactory;
import worldofzuulfx.Util;
import worldofzuulfx.tiles.Tile;

public class RoomFactory {

    int gameMode;

    /**
     * Instantiates a RoomFactory-object
     *
     * @param gameMode Represents a game mode - Normal and Abnormal - using an
     * int 0 an 1 respectively
     */
    public RoomFactory(int gameMode) {
        this.gameMode = gameMode;
    }

    /**
     * Instantiates a room based on the data found in a file. e.g. "rooms.data"
     *
     * @param roomID The Room to be created. The roomID has to be found in the
     * file.
     * @param backgroundLayer The layer which the room is drawn on excl. items,
     * Player and NPCs.
     * @param objectLayer The layer which items is drawn on.
     * @param tiles The images which represents parts of a room, NPCs, Items and
     * Player.
     * @return A Rooms based of the given parameters.
     */
    public Room loadRoom(String roomID, Pane backgroundLayer, Pane objectLayer, HashMap<Integer, Tile> tiles) {
        int[][] tileLayout;
        Room room;
        String ID;
        String description;
        int numItems;
        Item item;
        String itemType;
        String itemPath;
        double itemX;
        double itemY;

        // Creates a new properties file and loads a given file.
        Util.newPropFile();

        switch (gameMode) {
            case 0: {
                Util.loadFile("rooms.data");
                break;
            }
            case 1: {
                Util.loadFile("rooms2.data");
                break;
            }
            default: {
                Util.loadFile("rooms.data");
                break;
            }
        }

        description = Util.getProp(roomID + ".description");
        tileLayout = Util.strTo2d(Util.getProp(roomID), "\r", ", ");
        // Numbers of items
        numItems = Integer.parseInt(Util.getProp(roomID + ".numItems"));
        room = new Room(roomID, description, backgroundLayer, objectLayer, tiles, tileLayout);
        for (int i = 0; i < numItems; i++) {
            itemPath = roomID + ".Item." + i;
            itemType = Util.getProp(itemPath + ".ID");
            // Create an Item based on the ItemType
            switch (itemType) {
                case "beer": {
                    item = ItemFactory.makeBeer();
                    break;
                }
                case "computer": {
                    item = ItemFactory.makeComputer();
                    break;
                }
                case "clock": {
                    item = ItemFactory.makeClock(30);
                    break;
                }
                case "note": {
                    item = ItemFactory.makeNote("", "", "");
                    break;
                }
                case "coffee": {
                    item = ItemFactory.makeCoffee();
                    break;
                }
                case "coffeeVoucher": {
                    item = ItemFactory.makeCoffeeVoucher();
                    break;
                }
                case "book": {
                    item = ItemFactory.makeBook("", Book.BookColor.RED);
                    break;
                }
                default: {
                    item = null;
                }
            }
            if (item != null) {
                itemX = Double.parseDouble(Util.getProp(itemPath + ".PosX"));
                itemY = Double.parseDouble(Util.getProp(itemPath + ".PosY"));
                item.setX(itemX);
                item.setY(itemY);
                item.updateUI();
                room.getRoomInventory().addItem(item);
            }
        }
        return room;

    }

    /**
     * Store all rooms found (incl. Items) in the given list as a file - e.g.
     * "rooms.data".
     *
     * @param list The list of rooms to be stored.
     */
    public void StoreRooms(ArrayList<Room> list) {
        Util.newPropFile();
        for (Room room : list) {
            // Converts a two-dimensional int array to a String.
            String result = Arrays.stream(room.getTileTerrain().getTileTerrainLayout()).map(Arrays::toString).collect(Collectors.joining(System.lineSeparator()));
            String roomID = room.getID();
            Util.setProp(roomID, result);
            Util.setProp(roomID + ".description", room.getShortDescription());
            Util.setProp(roomID + ".numItems", Integer.toString(room.getRoomInventory().getSize()));
            for (int i = 0; i < room.getRoomInventory().getSize(); i++) {
                String item = roomID + ".Item." + Integer.toString(i);
                Util.setProp(item + ".ID", room.getRoomInventory().getItem(i).getID());
                Util.setProp(item + ".PosX", Double.toString(room.getRoomInventory().getItem(i).getX()));
                Util.setProp(item + ".PosY", Double.toString(room.getRoomInventory().getItem(i).getY()));
            }
        }
        switch (gameMode) {
            case 0: {
                Util.storeFile("rooms.data");
                break;
            }
            case 1: {
                Util.storeFile("rooms2.data");
                break;
            }
            default: {
                Util.storeFile("rooms.data");
                break;
            }
        }
        storeRoomExits(list);
    }

    /**
     * Store all room exits as a file.
     *
     * @param list The list of exits to be stored.
     */
    public void storeRoomExits(ArrayList<Room> list) {
        int exitCounter;
        String exitID;
        Util.newPropFile();
        for (Room room : list) {
            exitCounter = 0;
            // Iterates through the Tileterrain of the room
            for (Tile tile : room.getTileTerrain().getTileTerrain()) {
                if (tile.canTeleport()) {
                    exitID = room.getID() + ".Exit." + exitCounter;
                    Util.setProp(exitID + ".door", tile.getPos());
                    Util.setProp(exitID + ".NextRoom", tile.getNextRoom().getID());
                    Util.setProp(exitID + ".NextPos", tile.getNextPos());

                    exitCounter++;
                }
            }
            Util.setProp(room.getID() + ".numExits", String.valueOf(exitCounter));
        }
           switch (gameMode) {
            case 0: {
                Util.storeFile("exits.data");
                break;
            }
            case 1: {
                Util.storeFile("exits2.data");
                break;
            }
            default: {
                Util.storeFile("exits.data");
                break;
            }
        }
    }

    /**
     * Loads all exits of all rooms and assigns them to a specific room based on data found in a given file. e.g.
     * "exits.data".
     *
     * @param list The list of rooms to be given one or more exits.
     */
    public void loadRoomExits(ArrayList<Room> list) {

        int numExits;
        String exitID;
        String doorPos;
        String nextPos;
        Room nextRoom = null;

        Util.newPropFile();

        switch (gameMode) {
            case 0: {
                Util.loadFile("exits.data");
                break;
            }
            case 1: {
                Util.loadFile("exits2.data");
                break;
            }
            default: {
                Util.loadFile("exits.data");
                break;
            }
        }

        for (Room room : list) {
            numExits = Integer.parseInt(Util.getProp(room.getID() + ".numExits"));
            for (int i = 0; i < numExits; i++) {
                exitID = room.getID() + ".Exit." + i;
                doorPos = Util.getProp(exitID + ".door");
                nextPos = Util.getProp(exitID + ".NextPos");
                for (Room next : list) {
                    if (next.getID().equalsIgnoreCase(Util.getProp(exitID + ".NextRoom"))) {
                        nextRoom = next;
                    }
                }
                room.setExit(doorPos, nextRoom, nextPos);
            }

        }
    }

    /**
     * Creates all rooms by using LoadRoom and LoadRoomExits
     *
     * @param tiles The images which represents parts of a room, NPCs, Items and
     * Player.
     * @param backgroundLayer The layer which the room is drawn on excl. items,
     * Player and NPCs.
     * @param objectLayer The layer which items is drawn on.
     * @return Returns a ArrayList containing all rooms.
     */
    public ArrayList<Room> createRooms(HashMap<Integer, Tile> tiles, Pane backgroundLayer, Pane objectLayer) {
        Room outside, exam, campus, downunder, bookstore, hutten, canteen, knoldene, u163, u170, u180; // Varibler af typen Room

        ArrayList<Room> rooms = new ArrayList<>();

        outside = loadRoom("outside", backgroundLayer, objectLayer, tiles);
        exam = loadRoom("exam", backgroundLayer, objectLayer, tiles);
        campus = loadRoom("campus", backgroundLayer, objectLayer, tiles);
        bookstore = loadRoom("bookstore", backgroundLayer, objectLayer, tiles);
        hutten = loadRoom("Gydehutten", backgroundLayer, objectLayer, tiles);
        canteen = loadRoom("Canteen", backgroundLayer, objectLayer, tiles);
        knoldene = loadRoom("knoldene", backgroundLayer, objectLayer, tiles);
        u163 = loadRoom("u163", backgroundLayer, objectLayer, tiles);
        u170 = loadRoom("u170", backgroundLayer, objectLayer, tiles);
        u180 = loadRoom("u180", backgroundLayer, objectLayer, tiles);
        downunder = loadRoom("downunder", backgroundLayer, objectLayer, tiles);
        
        rooms.add(canteen);
        rooms.add(outside);
        rooms.add(exam);
        rooms.add(campus);
        rooms.add(bookstore);
        rooms.add(hutten);
        rooms.add(downunder);
        rooms.add(canteen);
        rooms.add(knoldene);
        rooms.add(u163);
        rooms.add(u170);
        rooms.add(u180);

        loadRoomExits(rooms);
        return rooms;
    }
}
