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

    private static int[][] layout;

    public RoomFactory() {

    }

    public static int[][] loadRoom(String roomName) {
        Util.newPropFile();

        Util.loadFile("rooms.data");

        return Util.to2d(Util.getProp(roomName), "\r", ", ");
    }

    public static void StoreRooms(ArrayList<Room> list) {
        Util.newPropFile();
        layout = new int[][]{{0, 12, 12, 12, 12, 12, 214, 12, 12, 12, 12, 0},
        {0, 12, 13, 13, 13, 13, 215, 13, 13, 13, 12, 0},
        {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 12, 0},
        {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 12, 0},
        {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 12, 0},
        {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 12, 0},
        {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 12, 0},
        {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 12, 0},
        {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 12, 0},
        {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 12, 0},
        {0, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 0},
        {0, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };

        for (Room room : list) {
            String result = Arrays.stream(room.getTileMap().getTileTerrainLayout()).map(Arrays::toString).collect(Collectors.joining(System.lineSeparator()));
            Util.setProp(room.getID(), result);
        }
        Util.storeFile("rooms.data");
    }

    public static ArrayList<Room> createRooms(HashMap<Integer, Tile> tiles, Pane backgroundLayer, Pane objectLayer) {
        Room outside, exam, campus, downunder, bookstore, hutten, canteen, knoldene, u163, u170, u180; // Varibler af typen Room

        ArrayList<Room> rooms = new ArrayList<>();

        outside = new Room("outside", "outside", backgroundLayer, objectLayer, tiles, loadRoom("outside"));

        exam = new Room("exam", "exam", backgroundLayer, objectLayer, tiles, loadRoom("exam"));

        campus = new Room("campus", "campus", backgroundLayer, objectLayer, tiles, loadRoom("campus"));

        bookstore = new Room("bookstore", "bookstore", backgroundLayer, objectLayer, tiles, loadRoom("bookstore"));

        hutten = new Room("Gydehutten", "hutten", backgroundLayer, objectLayer, tiles, loadRoom("Gydehutten"));
        canteen = new Room("Canteen", "Canteen", backgroundLayer, objectLayer, tiles, loadRoom("Canteen"));
        knoldene = new Room("knoldene", "knoldene", backgroundLayer, objectLayer, tiles, loadRoom("knoldene"));
        u163 = new Room("u163", "u163", backgroundLayer, objectLayer, tiles, loadRoom("u163"));
        u170 = new Room("u170", "u170", backgroundLayer, objectLayer, tiles, loadRoom("u170"));
        u180 = new Room("u180", "u180", backgroundLayer, objectLayer, tiles, loadRoom("u180"));
        downunder = new Room("downunder", "downunder", backgroundLayer, objectLayer, tiles, loadRoom("downunder"));

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

        Item item = ItemFactory.makeBeer();
        item.move(64, 64);
        item.updateUI();
        campus.getRoomInventory().addItem(item);

        Item item2 = ItemFactory.makeComputer();
        item2.move(64, 128);
        item2.updateUI();
        campus.getRoomInventory().addItem(item2);

        Item item3 = ItemFactory.makeBeer();
        item3.move(128, 128);
        item3.updateUI();
        campus.getRoomInventory().addItem(item3);

        Item item4 = ItemFactory.makeBeer();
        item4.move(128, 256);
        item4.updateUI();
        campus.getRoomInventory().addItem(item4);

        Item item5 = ItemFactory.makeBeer();
        item5.move(256, 128);
        item5.updateUI();
        campus.getRoomInventory().addItem(item5);

        Item item6 = ItemFactory.makeCoffeeVoucher();
        item6.move(256, 256);
        item6.updateUI();
        campus.getRoomInventory().addItem(item6);
        
         Item item7 = ItemFactory.makeClock(10);
        item7.move(256, 256);
        item7.updateUI();
        outside.getRoomInventory().addItem(item7);

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

        StoreRooms(rooms);
        return rooms;
    }
}
