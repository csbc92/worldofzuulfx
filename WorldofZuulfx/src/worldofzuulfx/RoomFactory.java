/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuulfx;

import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.layout.Pane;
import worldofzuulfx.Items.Item;
import worldofzuulfx.Items.ItemFactory;
import worldofzuulfx.tiles.Tile;

/**
 *
 * @author JV
 */
public class RoomFactory {
    
    public RoomFactory (){
        
    }
    
    public static ArrayList<Room> createRooms(HashMap<Integer, Tile> tiles, Pane backgroundLayer, Pane objectLayer) {
        Room outside, exam, campus, downunder, bookstore, hutten, canteen, knoldene, u163, u170, u180; // Varibler af typen Room

        ArrayList<Room> rooms = new ArrayList<>();

        outside = new Room("outside", "outside", backgroundLayer, objectLayer, tiles, new int[][]{{0, 12, 12, 12, 12, 12, 214, 12, 12, 12, 12, 0},
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
                                                                                                    });

        exam = new Room("exam", "exam", backgroundLayer, objectLayer, tiles, new int[][]{{0, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 0},
                                                                                        {0, 12, 13, 13, 13, 13, 13, 13, 13, 13, 12, 0},
                                                                                        {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 12, 0},
                                                                                        {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 12, 0},
                                                                                        {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 12, 0},
                                                                                        {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 13, 0},
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
                                                                                                {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 12, 0},
                                                                                                {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 12, 0},
                                                                                                {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 12, 0},
                                                                                                {0, 13, 6, 20, 20, 20, 20, 20, 20, 20, 13, 0},
                                                                                                {0, 7, 20, 20, 20, 20, 20, 20, 20, 20, 34, 0},
                                                                                                {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 12, 0},
                                                                                                {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 12, 0},
                                                                                                {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 12, 0},
                                                                                                {0, 12, 12, 12, 12, 12, 20, 12, 12, 12, 12, 0},
                                                                                                {0, 13, 13, 13, 13, 13, 20, 13, 13, 13, 13, 0},
                                                                                                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
                                                                                                });
        
        bookstore = new Room("bookstore", "bookstore", backgroundLayer, objectLayer, tiles, new int[][]{{0, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 0},
                                                                                                        {0, 12, 13, 13, 13, 13, 13, 13, 13, 13, 12, 0},
                                                                                                        {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 12, 0},
                                                                                                        {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 12, 0},
                                                                                                        {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 12, 0},
                                                                                                        {0, 13, 6, 20, 20, 20, 20, 20, 20, 20, 12, 0},
                                                                                                        {0, 7, 20, 20, 20, 20, 20, 20, 20, 20, 12, 0},
                                                                                                        {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 12, 0},
                                                                                                        {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 12, 0},
                                                                                                        {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 12, 0},
                                                                                                        {0, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 0},
                                                                                                        {0, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 0},
                                                                                                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
                                                                                                        });
        hutten = new Room("Gydehutten", "hutten", backgroundLayer, objectLayer, tiles, new int[][]{{0, 12, 12, 12, 12, 214, 12, 12, 12, 214, 12, 0},
                                                                                                    {0, 12, 13, 13, 13, 215, 13, 13, 13, 215, 12, 0},
                                                                                                    {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 12, 0},
                                                                                                    {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 12, 0},
                                                                                                    {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 12, 0},
                                                                                                    {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 13, 0},
                                                                                                    {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 34, 0},
                                                                                                    {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 12, 0},
                                                                                                    {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 12, 0},
                                                                                                    {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 12, 0},
                                                                                                    {0, 12, 12, 12, 12, 20, 12, 12, 12, 12, 12, 0},
                                                                                                    {0, 13, 13, 13, 13, 20, 13, 13, 13, 13, 13, 0},
                                                                                                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
                                                                                                    });
        canteen = new Room("Canteen", "Canteen", backgroundLayer, objectLayer, tiles, new int[][]{{0, 12, 12, 12, 12, 12, 214, 12, 12, 12, 12, 0},
                                                                                                    {0, 12, 13, 13, 13, 13, 215, 13, 13, 13, 12, 0},
                                                                                                    {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 12, 0},
                                                                                                    {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 12, 0},
                                                                                                    {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 12, 0},
                                                                                                    {0, 13, 6, 20, 20, 20, 20, 20, 20, 20, 12, 0},
                                                                                                    {0, 7, 20, 20, 20, 20, 20, 20, 20, 20, 12, 0},
                                                                                                    {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 12, 0},
                                                                                                    {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 12, 0},
                                                                                                    {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 12, 0},
                                                                                                    {0, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 0},
                                                                                                    {0, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 0},
                                                                                                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
                                                                                                    });
        knoldene = new Room("knoldene", "knoldene", backgroundLayer, objectLayer, tiles, new int[][]{{0, 12, 12, 12, 12, 12, 214, 12, 12, 12, 12, 0},
                                                                                                    {0, 12, 13, 13, 13, 13, 215, 13, 13, 13, 12, 0},
                                                                                                    {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 12, 0},
                                                                                                    {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 12, 0},
                                                                                                    {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 12, 0},
                                                                                                    {0, 13, 6, 20, 20, 20, 20, 20, 20, 20, 13, 0},
                                                                                                    {0, 7, 20, 20, 20, 20, 20, 20, 20, 20, 34, 0},
                                                                                                    {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 12, 0},
                                                                                                    {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 12, 0},
                                                                                                    {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 12, 0},
                                                                                                    {0, 12, 12, 12, 12, 12, 20, 12, 12, 12, 12, 0},
                                                                                                    {0, 13, 13, 13, 13, 13, 20, 13, 13, 13, 13, 0},
                                                                                                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
                                                                                                    });
        u163 = new Room("u163", "u163", backgroundLayer, objectLayer, tiles, new int[][]{{0, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 0},
<<<<<<< HEAD
                                                                                         {0, 12, 168, 85, 99, 13, 13, 85, 99, 168, 12, 0},
                                                                                         {0, 12, 169, 20, 20, 20, 20, 20, 20, 169, 12, 0},
                                                                                         {0, 12, 6, 20, 20, 112, 126, 20, 20, 20, 12, 0},
                                                                                         {0, 12, 6, 210, 210, 210, 210, 210, 210, 20, 12, 0},
=======
                                                                                         {0, 12, 13, 13, 85, 99, 13, 13, 13, 13, 12, 0},
                                                                                         {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 12, 0},
                                                                                         {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 12, 0},
                                                                                         {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 12, 0},
                                                                                         {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 12, 0},
>>>>>>> 96a930d48428e97e6d2c7bc416f7e7de52d87c01
                                                                                         {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 12, 0},
                                                                                         {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 12, 0},
                                                                                         {0, 12, 6, 210, 210, 210, 210, 210, 210, 20, 12, 0},
                                                                                         {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 12, 0},
                                                                                         {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 12, 0},
                                                                                         {0, 12, 12, 12, 12, 20, 12, 12, 12, 12, 12, 0},
                                                                                         {0, 13, 13, 13, 13, 20, 13, 13, 13, 13, 13, 0},
                                                                                         {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
                                                                                            });
        u170 = new Room("u170", "u170", backgroundLayer, objectLayer, tiles, new int[][]{{0, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 0},
                                                                                         {0, 12, 168, 85, 99, 85, 99, 13, 140, 154, 12, 0},
                                                                                         {0, 12, 169, 20, 20, 20, 20, 20, 141, 155, 12, 0},
                                                                                         {0, 12, 6, 20, 112, 126, 20, 20, 20, 20, 12, 0},
                                                                                         {0, 12, 210, 210, 210, 210, 210, 210, 20, 20, 12, 0},
                                                                                         {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 13, 0},
                                                                                         {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 34, 0},
                                                                                         {0, 12, 210, 210, 210, 210, 210, 210, 20, 20, 12, 0},
                                                                                         {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 12, 0},
                                                                                         {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 12, 0},
                                                                                         {0, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 0},
                                                                                         {0, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 0},
                                                                                         {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
                                                                                            });
        u180 = new Room("u180", "u180", backgroundLayer, objectLayer, tiles, new int[][]{{0, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 0},
                                                                                         {0, 12, 140, 154, 13, 85, 99, 85, 99, 168, 12, 0},
                                                                                         {0, 12, 141, 155, 20, 20, 20, 20, 20, 169, 12, 0},
                                                                                         {0, 12, 6, 20, 20, 20, 112, 126, 20, 20, 12, 0},
                                                                                         {0, 12, 6, 20, 210, 210, 210, 210, 210, 210, 12, 0},
                                                                                         {0, 13, 6, 20, 20, 20, 20, 20, 20, 20, 12, 0},
                                                                                         {0, 7, 20, 20, 20, 20, 20, 20, 20, 20, 12, 0},
                                                                                         {0, 12, 6, 20, 210, 210, 210, 210, 210, 210, 12, 0},
                                                                                         {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 12, 0},
                                                                                         {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 12, 0},
                                                                                         {0, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 0},
                                                                                         {0, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 0},
                                                                                         {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
                                                                                            });
        downunder = new Room("downunder", "downunder", backgroundLayer, objectLayer, tiles, new int[][]{{0, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 0},
                                                                                                        {0, 12, 13, 13, 13, 13, 13, 13, 13, 13, 12, 0},
                                                                                                        {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 12, 0},
                                                                                                        {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 12, 0},
                                                                                                        {0, 12, 6, 20, 20, 20, 202, 216, 20, 20, 12, 0},
                                                                                                        {0, 13, 6, 20, 20, 20, 203, 217, 20, 20, 12, 0},
                                                                                                        {0, 7, 20, 20, 20, 20, 204, 218, 20, 20, 12, 0},
                                                                                                        {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 12, 0},
                                                                                                        {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 12, 0},
                                                                                                        {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 12, 0},
                                                                                                        {0, 12, 12, 12, 12, 12, 20, 12, 12, 12, 12, 0},
                                                                                                        {0, 13, 13, 13, 13, 13, 20, 13, 13, 13, 13, 0},
                                                                                                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
                                                                                                        });
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
        
        return rooms;
    }
}
