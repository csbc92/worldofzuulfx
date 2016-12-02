/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuulfx;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;
import javafx.scene.layout.Pane;
import worldofzuulfx.Items.Item;
import worldofzuulfx.Items.ItemFactory;
import worldofzuulfx.tiles.Tile;

/**
 *
 * @author JV
 */
public class RoomFactory {

    public RoomFactory() {

    }

    public static Room loadRoom(String roomID, Pane backgroundLayer, Pane objectLayer, HashMap<Integer, Tile> tiles) {
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
        Util.loadFile("rooms.data");

        description = Util.getProp(roomID + "." + "description");
        tileLayout = Util.strTo2d(Util.getProp(roomID), "\r", ", ");
        // Numbers of items
        numItems = Integer.parseInt(Util.getProp(roomID + "." + "numItems"));
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
                    item = ItemFactory.makeClock(600);
                    break;
                }
                case "note": {
                    item = ItemFactory.makeNote("");
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
                    item = ItemFactory.makeBook("");
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

    public static void StoreRooms(ArrayList<Room> list) {
        Util.newPropFile();
        for (Room room : list) {
            String result = Arrays.stream(room.getTileMap().getTileTerrainLayout()).map(Arrays::toString).collect(Collectors.joining(System.lineSeparator()));
            String roomID = room.getID();
            Util.setProp(roomID, result);
            Util.setProp(roomID + "." + "description", room.getShortDescription());
            Util.setProp(roomID + "." + "numItems", Integer.toString(room.getRoomInventory().getSize()));
            for (int i = 0; i < room.getRoomInventory().getSize(); i++) {
                String item = roomID + ".Item." + Integer.toString(i);
                Util.setProp(item + "." + "ID", room.getRoomInventory().getItem(i).getID());
                Util.setProp(item + "." + "PosX", Double.toString(room.getRoomInventory().getItem(i).getX()));
                Util.setProp(item + "." + "PosY", Double.toString(room.getRoomInventory().getItem(i).getY()));
            }
        }
        Util.storeFile("rooms.data");
    }

    /**
     *  Creates all rooms.
     * @param tiles
     * @param backgroundLayer
     * @param objectLayer
     * @return Returns a ArrayList containing all rooms.
     */
    public static ArrayList<Room> createRooms(HashMap<Integer, Tile> tiles, Pane backgroundLayer, Pane objectLayer) {
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

        
        outside.setExit("0600", campus, outside.getTileMap().getTile("0610"));
        campus.setExit("0612", outside, outside.getTileMap().getTile("0601"));
        campus.setExit("0006", exam, exam.getTileMap().getTile("0906"));
        campus.setExit("0600", hutten, hutten.getTileMap().getTile("0510"));
        campus.setExit("1106", bookstore, bookstore.getTileMap().getTile("0106"));

        bookstore.setExit("0006", campus, campus.getTileMap().getTile("1006"));
        exam.setExit("1106", campus, campus.getTileMap().getTile("0106"));

        hutten.setExit("0512", campus, campus.getTileMap().getTile("0601"));
        hutten.setExit("1106", canteen, canteen.getTileMap().getTile("0106"));
        hutten.setExit("0500", knoldene, knoldene.getTileMap().getTile("0611"));
        hutten.setExit("0900", downunder, downunder.getTileMap().getTile("0106"));

        downunder.setExit("0612", canteen, canteen.getTileMap().getTile("0601"));
        downunder.setExit("0006", hutten, hutten.getTileMap().getTile("0901"));

        canteen.setExit("0600", downunder, downunder.getTileMap().getTile("0611"));
        canteen.setExit("0006", hutten, hutten.getTileMap().getTile("0906"));

        knoldene.setExit("0612", hutten, hutten.getTileMap().getTile("0501"));
        knoldene.setExit("0600", u163, u163.getTileMap().getTile("0511"));
        knoldene.setExit("0006", u170, u170.getTileMap().getTile("1006"));
        knoldene.setExit("1106", u180, u180.getTileMap().getTile("0106"));

        u163.setExit("0512", knoldene, knoldene.getTileMap().getTile("0601"));
        u170.setExit("1106", knoldene, knoldene.getTileMap().getTile("0106"));
        u180.setExit("0006", knoldene, knoldene.getTileMap().getTile("1006"));

//        Item item = ItemFactory.makeBeer();
//        item.move(64, 64);
//        item.updateUI();
//        campus.getRoomInventory().addItem(item);

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
        //StoreRooms(rooms);
        return rooms;
    }
}
