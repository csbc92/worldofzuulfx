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
     * @return
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
                Util.loadFile("rooms.data");
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
                item.move(itemX, itemY);
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
     * @param list
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
        Util.storeFile("rooms.data");
        storeRoomExits(list);
    }

    /**
     * Store all room exits as a file.
     *
     * @param list
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
        Util.storeFile("exits.data");
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
                Util.loadFile("exits.data");
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

//        outside = loadRoom("outside", backgroundLayer, objectLayer, tiles);
//        exam = loadRoom("exam", backgroundLayer, objectLayer, tiles);
//        campus = loadRoom("campus", backgroundLayer, objectLayer, tiles);
//        bookstore = loadRoom("bookstore", backgroundLayer, objectLayer, tiles);
//        hutten = loadRoom("Gydehutten", backgroundLayer, objectLayer, tiles);
//        canteen = loadRoom("Canteen", backgroundLayer, objectLayer, tiles);
//        knoldene = loadRoom("knoldene", backgroundLayer, objectLayer, tiles);
//        u163 = loadRoom("u163", backgroundLayer, objectLayer, tiles);
//        u170 = loadRoom("u170", backgroundLayer, objectLayer, tiles);
//        u180 = loadRoom("u180", backgroundLayer, objectLayer, tiles);
//        downunder = loadRoom("downunder", backgroundLayer, objectLayer, tiles);
//        
        
        outside = new Room("outside", "outside", backgroundLayer, objectLayer, tiles, new int[][]{{0, 137, 137, 137, 137, 137, 213, 137, 137, 137, 137, 0},
                                                                                                    {0, 138, 139, 138, 139, 138, 215, 138, 139, 138, 138, 0},
                                                                                                    {0, 153, 153, 153, 149, 167, 167, 167, 149, 153, 153, 0},
                                                                                                    {0, 153, 153, 153, 150, 167, 167, 167, 150, 153, 153, 0},
                                                                                                    {0, 153, 153, 153, 149, 167, 167, 167, 149, 153, 153, 0},
                                                                                                    {0, 153, 153, 153, 150, 167, 167, 167, 150, 153, 153, 0},
                                                                                                    {0, 153, 153, 153, 149, 167, 167, 167, 149, 153, 153, 0},
                                                                                                    {0, 153, 153, 153, 150, 167, 167, 167, 150, 153, 153, 0},
                                                                                                    {0, 153, 153, 153, 149, 167, 167, 167, 149, 153, 153, 0},
                                                                                                    {0, 153, 153, 153, 150, 167, 167, 167, 150, 153, 153, 0},
                                                                                                    {0, 153, 153, 153, 149, 167, 167, 167, 149, 153, 153, 0},
                                                                                                    {0, 153, 153, 153, 150, 167, 167, 167, 150, 153, 153, 0},
                                                                                                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
                                                                                                    });

        exam = new Room("exam", "exam", backgroundLayer, objectLayer, tiles, new int[][]{{0, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 0},
                                                                                        {0, 12, 133, 147, 85, 99, 113, 127, 133, 147, 12, 0},
                                                                                        {0, 12, 134, 148, 20, 20, 20, 20, 134, 148, 12, 0},
                                                                                        {0, 12, 6, 20, 20, 186, 186, 20, 20, 20, 12, 0},
                                                                                        {0, 12, 6, 20, 20, 174, 188, 20, 20, 20, 12, 0},
                                                                                        {0, 12, 6, 20, 20, 20, 186, 20, 20, 20, 13, 0},
                                                                                        {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 34, 0},
                                                                                        {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 12, 0},
                                                                                        {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 12, 0},
                                                                                        {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 12, 0},
                                                                                        {0, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 0},
                                                                                        {0, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 0},
                                                                                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
                                                                                        });

        campus = new Room("campus", "campus", backgroundLayer, objectLayer, tiles, new int[][]{{0, 12, 12, 12, 12, 12, 214, 12, 12, 12, 12, 0},
                                                                                                {0, 12, 13, 13, 13, 13, 215, 13, 13, 13, 12, 0},
                                                                                                {0, 12, 6, 186, 20, 20, 20, 20, 186, 211, 12, 0},
                                                                                                {0, 12, 184, 211, 186, 20, 20, 20, 186, 212, 12, 0},
                                                                                                {0, 12, 184, 212, 186, 20, 20, 20, 20, 186, 12, 0},
                                                                                                {0, 13, 6, 186, 20, 20, 20, 20, 20, 20, 13, 0},
                                                                                                {0, 7, 20, 20, 20, 20, 20, 20, 20, 20, 34, 0},
                                                                                                {0, 12, 6, 186, 20, 20, 20, 20, 20, 20, 12, 0},
                                                                                                {0, 12, 184, 210, 186, 20, 20, 20, 186, 186, 12, 0},
                                                                                                {0, 12, 6, 186, 20, 20, 20, 186, 84, 98, 12, 0},
                                                                                                {0, 12, 12, 12, 12, 12, 20, 12, 12, 12, 12, 0},
                                                                                                {0, 13, 13, 13, 13, 13, 20, 13, 13, 13, 13, 0},
                                                                                                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
                                                                                                });
        
        bookstore = new Room("bookstore", "bookstore", backgroundLayer, objectLayer, tiles, new int[][]{{0, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 0},
                                                                                                        {0, 12, 133, 147, 133, 147, 133, 147, 133, 147, 12, 0},
                                                                                                        {0, 12, 134, 148, 134, 148, 134, 148, 134, 148, 12, 0},
                                                                                                        {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 12, 0},
                                                                                                        {0, 12, 6, 133, 147, 20, 135, 20, 135, 20, 12, 0},
                                                                                                        {0, 13, 6, 134, 148, 210, 136, 210, 136, 210, 12, 0},
                                                                                                        {0, 7, 20, 20, 20, 186, 20, 186, 20, 186, 12, 0},
                                                                                                        {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 12, 0},
                                                                                                        {0, 12, 6, 186, 210, 186, 20, 20, 186, 210, 12, 0},
                                                                                                        {0, 12, 6, 20, 186, 20, 20, 20, 20, 186, 12, 0},
                                                                                                        {0, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 0},
                                                                                                        {0, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 0},
                                                                                                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
                                                                                                        });
        hutten = new Room("Gydehutten", "hutten", backgroundLayer, objectLayer, tiles, new int[][]{{0, 12, 12, 12, 12, 214, 12, 12, 12, 214, 12, 0},
                                                                                                    {0, 12, 13, 13, 13, 215, 13, 13, 13, 215, 12, 0},
                                                                                                    {0, 12, 6, 186, 20, 20, 20, 20, 20, 20, 12, 0},
                                                                                                    {0, 12, 184, 198, 186, 20, 20, 20, 20, 20, 12, 0},
                                                                                                    {0, 12, 6, 186, 20, 20, 20, 20, 20, 20, 12, 0},
                                                                                                    {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 13, 0},
                                                                                                    {0, 12, 6, 186, 20, 20, 20, 20, 20, 20, 34, 0},
                                                                                                    {0, 12, 184, 211, 186, 20, 20, 20, 20, 20, 12, 0},
                                                                                                    {0, 12, 184, 212, 186, 20, 20, 186, 186, 20, 12, 0},
                                                                                                    {0, 12, 6, 186, 20, 20, 186, 84, 98, 186, 12, 0},
                                                                                                    {0, 12, 12, 12, 12, 20, 12, 12, 12, 12, 12, 0},
                                                                                                    {0, 13, 13, 13, 13, 20, 13, 13, 13, 13, 13, 0},
                                                                                                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
                                                                                                    });
        canteen = new Room("Canteen", "Canteen", backgroundLayer, objectLayer, tiles, new int[][]{{0, 12, 12, 12, 12, 12, 214, 12, 12, 12, 12, 0},
                                                                                                    {0, 12, 13, 13, 13, 13, 215, 13, 13, 13, 12, 0},
                                                                                                    {0, 12, 6, 186, 20, 20, 20, 20, 186, 20, 12, 0},
                                                                                                    {0, 12, 184, 211, 186, 20, 20, 186, 211, 186, 12, 0},
                                                                                                    {0, 12, 184, 212, 186, 20, 20, 186, 212, 186, 12, 0},
                                                                                                    {0, 13, 6, 186, 20, 20, 20, 20, 186, 20, 12, 0},
                                                                                                    {0, 7, 20, 20, 20, 20, 180, 166, 223, 20, 12, 0},
                                                                                                    {0, 12, 6, 186, 20, 20, 222, 195, 195, 20, 12, 0},
                                                                                                    {0, 12, 184, 210, 186, 20, 222, 20, 20, 20, 12, 0},
                                                                                                    {0, 12, 6, 186, 20, 20, 181, 20, 20, 20, 12, 0},
                                                                                                    {0, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 0},
                                                                                                    {0, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 0},
                                                                                                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
                                                                                                    });
        knoldene = new Room("knoldene", "knoldene", backgroundLayer, objectLayer, tiles, new int[][]{{0, 12, 12, 12, 12, 12, 214, 12, 12, 12, 12, 0},
                                                                                                    {0, 12, 13, 13, 13, 13, 215, 13, 13, 13, 12, 0},
                                                                                                    {0, 12, 6, 186, 20, 20, 20, 20, 186, 211, 12, 0},
                                                                                                    {0, 12, 184, 198, 186, 20, 20, 20, 186, 212, 12, 0},
                                                                                                    {0, 12, 6, 186, 20, 20, 20, 20, 20, 186, 12, 0},
                                                                                                    {0, 13, 6, 20, 20, 20, 20, 20, 20, 20, 13, 0},
                                                                                                    {0, 7, 20, 20, 20, 20, 20, 20, 20, 20, 34, 0},
                                                                                                    {0, 12, 6, 186, 20, 20, 20, 20, 20, 20, 12, 0},
                                                                                                    {0, 12, 184, 198, 186, 20, 20, 20, 20, 20, 12, 0},
                                                                                                    {0, 12, 6, 186, 20, 20, 20, 20, 20, 20, 12, 0},
                                                                                                    {0, 12, 12, 12, 12, 12, 20, 12, 12, 12, 12, 0},
                                                                                                    {0, 13, 13, 13, 13, 13, 20, 13, 13, 13, 13, 0},
                                                                                                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
                                                                                                    });
        u163 = new Room("u163", "u163", backgroundLayer, objectLayer, tiles, new int[][]{{0, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 0},
                                                                                         {0, 12, 135, 85, 99, 185, 13, 85, 99, 135, 12, 0},
                                                                                         {0, 12, 136, 20, 20, 20, 20, 20, 20, 136, 12, 0},
                                                                                         {0, 12, 6, 20, 20, 112, 126, 20, 20, 20, 12, 0},
                                                                                         {0, 12, 6, 210, 210, 198, 198, 198, 210, 20, 12, 0},
                                                                                         {0, 12, 6, 186, 186, 186, 186, 186, 186, 20, 12, 0},
                                                                                         {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 12, 0},
                                                                                         {0, 12, 6, 198, 198, 210, 210, 198, 210, 20, 12, 0},
                                                                                         {0, 12, 6, 186, 186, 186, 186, 186, 186, 20, 12, 0},
                                                                                         {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 12, 0},
                                                                                         {0, 12, 12, 12, 12, 20, 12, 12, 12, 12, 12, 0},
                                                                                         {0, 13, 13, 13, 13, 20, 13, 13, 13, 13, 13, 0},
                                                                                         {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
                                                                                            });
        u170 = new Room("u170", "u170", backgroundLayer, objectLayer, tiles, new int[][]{{0, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 0},
                                                                                         {0, 12, 135, 85, 99, 85, 99, 185, 133, 147, 12, 0},
                                                                                         {0, 12, 136, 20, 20, 20, 20, 20, 134, 148, 12, 0},
                                                                                         {0, 12, 6, 20, 112, 126, 20, 20, 20, 20, 12, 0},
                                                                                         {0, 12, 198, 210, 198, 198, 210, 210, 20, 20, 12, 0},
                                                                                         {0, 12, 184, 186, 186, 186, 186, 186, 20, 20, 13, 0},
                                                                                         {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 34, 0},
                                                                                         {0, 12, 198, 198, 210, 210, 210, 198, 20, 20, 12, 0},
                                                                                         {0, 12, 184, 186, 186, 186, 186, 186, 20, 20, 12, 0},
                                                                                         {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 12, 0},
                                                                                         {0, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 0},
                                                                                         {0, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 0},
                                                                                         {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
                                                                                            });
        u180 = new Room("u180", "u180", backgroundLayer, objectLayer, tiles, new int[][]{{0, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 0},
                                                                                         {0, 12, 133, 147, 185, 85, 99, 85, 99, 135, 12, 0},
                                                                                         {0, 12, 134, 148, 20, 20, 20, 20, 20, 136, 12, 0},
                                                                                         {0, 12, 6, 20, 20, 20, 112, 126, 20, 20, 12, 0},
                                                                                         {0, 12, 6, 20, 198, 210, 210, 198, 198, 210, 12, 0},
                                                                                         {0, 13, 6, 20, 186, 186, 186, 186, 186, 186, 12, 0},
                                                                                         {0, 7, 20, 20, 20, 20, 20, 20, 20, 20, 12, 0},
                                                                                         {0, 12, 6, 20, 210, 198, 210, 210, 210, 198, 12, 0},
                                                                                         {0, 12, 6, 20, 186, 186, 186, 186, 186, 186, 12, 0},
                                                                                         {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 12, 0},
                                                                                         {0, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 0},
                                                                                         {0, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 0},
                                                                                         {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
                                                                                            });
        downunder = new Room("downunder", "downunder", backgroundLayer, objectLayer, tiles, new int[][]{{0, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 0},
                                                                                                        {0, 12, 13, 13, 13, 13, 13, 13, 13, 13, 12, 0},
                                                                                                        {0, 12, 6, 186, 186, 20, 20, 20, 20, 20, 12, 0},
                                                                                                        {0, 12, 184, 84, 98, 186, 20, 202, 216, 20, 12, 0},
                                                                                                        {0, 12, 6, 186, 186, 20, 20, 203, 217, 20, 12, 0},
                                                                                                        {0, 13, 6, 20, 20, 20, 20, 204, 218, 20, 12, 0},
                                                                                                        {0, 7, 20, 20, 20, 20, 20, 20, 20, 20, 12, 0},
                                                                                                        {0, 12, 6, 186, 20, 20, 20, 20, 20, 20, 12, 0},
                                                                                                        {0, 12, 184, 210, 186, 20, 20, 20, 20, 20, 12, 0},
                                                                                                        {0, 12, 6, 186, 20, 20, 20, 20, 20, 20, 12, 0},
                                                                                                        {0, 12, 12, 12, 12, 12, 20, 12, 12, 12, 12, 0},
                                                                                                        {0, 13, 13, 13, 13, 13, 20, 13, 13, 13, 13, 0},
                                                                                                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
                                                                                                        });
    

        outside.setExit("0600", campus, "0610");
        campus.setExit("0612", outside, "0601");
        campus.setExit("0006", exam, "0906");
        campus.setExit("0600", hutten, "0510");
        campus.setExit("1106", bookstore, "0106");

        bookstore.setExit("0006", campus, "1006");
        exam.setExit("1106", campus, "0106");

        hutten.setExit("0512", campus, "0601");
        hutten.setExit("1106", canteen, "0106");
        hutten.setExit("0500", knoldene, "0611");
        hutten.setExit("0900", downunder, "0106");

        downunder.setExit("0612", canteen, "0601");
        downunder.setExit("0006", hutten, "0901");

        canteen.setExit("0600", downunder, "0611");
        canteen.setExit("0006", hutten, "0906");

        knoldene.setExit("0612", hutten, "0501");
        knoldene.setExit("0600", u163, "0511");
        knoldene.setExit("0006", u170, "1006");
        knoldene.setExit("1106", u180, "0106");

        u163.setExit("0512", knoldene, "0601");
        u170.setExit("1106", knoldene, "0106");
        u180.setExit("0006", knoldene, "1006");

        Item item = ItemFactory.makeGlobus(3, canteen, "0505");
        item.move(256, 64);
        item.updateUI();
        outside.getRoomInventory().addItem(item);

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

        //TODO skal kun bruges, når vi skal gemme det endelige spil. Skal slettes til sidst!
        // Alle Room-filer skal skrives så det er læseligt for et menneske. (Gør det manuelt)
//        loadRoomExits(rooms);
        //  StoreRooms(rooms);
        return rooms;
    }
}
