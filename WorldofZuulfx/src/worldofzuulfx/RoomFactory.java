/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worldofzuulfx;

import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.layout.Pane;
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

        outside = new Room("outside", "outside", backgroundLayer, objectLayer, tiles, new int[][]{{0, 12, 12, 12, 12, 12, 12, 12, 12, 214, 12, 0},
        {0, 12, 13, 13, 13, 13, 13, 13, 13, 215, 12, 0},
        {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 12, 0},
        {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 12, 0},
        {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 13, 0},
        {0, 13, 6, 20, 20, 20, 20, 20, 20, 20, 34, 0},
        {0, 7, 20, 20, 20, 20, 20, 20, 20, 20, 12, 0},
        {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 12, 0},
        {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 12, 0},
        {0, 12, 6, 20, 20, 20, 20, 20, 20, 20, 12, 0},
        {0, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 0},
        {0, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 0}
        });

        exam = new Room("exam", "exam", backgroundLayer, objectLayer, tiles, new int[][]{{0, 0, 0, 0, 0, 0, 0, 116, 0, 0},
        {0, 5, 13, 49, 57, 13, 13, 117, 21, 0},
        {0, 6, 14, 14, 14, 14, 14, 14, 22, 0},
        {0, 6, 14, 14, 14, 14, 14, 14, 22, 0},
        {0, 6, 14, 14, 14, 14, 14, 14, 22, 0},
        {0, 6, 14, 14, 14, 14, 14, 14, 22, 0},
        {0, 6, 14, 14, 14, 14, 14, 14, 22, 0},
        {0, 6, 14, 14, 14, 14, 14, 14, 22, 0},
        {0, 6, 14, 14, 14, 14, 14, 14, 22, 0},
        {0, 7, 15, 15, 15, 15, 15, 15, 23, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        });

        campus = new Room("campus", "campus", backgroundLayer, objectLayer, tiles, new int[][]{{0, 0, 0, 0, 0, 0, 0, 116, 0, 0},
        {0, 5, 13, 49, 57, 13, 13, 117, 21, 0},
        {0, 6, 14, 14, 14, 14, 14, 14, 22, 0},
        {0, 6, 14, 14, 14, 14, 14, 14, 22, 0},
        {0, 6, 14, 48, 56, 14, 14, 14, 22, 0},
        {0, 6, 14, 14, 14, 14, 14, 14, 22, 0},
        {0, 6, 14, 14, 14, 14, 48, 56, 22, 0},
        {0, 6, 14, 14, 14, 14, 14, 14, 22, 0},
        {0, 6, 14, 14, 14, 14, 14, 14, 22, 0},
        {0, 7, 15, 15, 15, 15, 15, 15, 23, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        });
        downunder = new Room("downunder", "downunder", backgroundLayer, objectLayer, tiles, new int[][]{{0, 0, 0, 0, 0, 0, 0, 116, 0, 0},
        {0, 5, 13, 49, 57, 13, 13, 117, 21, 0},
        {0, 6, 14, 14, 14, 14, 14, 14, 22, 0},
        {0, 6, 14, 14, 14, 14, 14, 14, 22, 0},
        {0, 6, 14, 48, 56, 14, 14, 14, 22, 0},
        {0, 6, 14, 14, 14, 14, 14, 14, 22, 0},
        {0, 6, 14, 14, 14, 14, 14, 14, 22, 0},
        {0, 6, 14, 48, 56, 14, 14, 14, 22, 0},
        {0, 6, 14, 14, 14, 14, 14, 14, 22, 0},
        {0, 7, 15, 15, 15, 15, 15, 15, 23, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        });
        bookstore = new Room("bookstore", "bookstore", backgroundLayer, objectLayer, tiles, new int[][]{{0, 0, 0, 0, 0, 0, 0, 116, 0, 0},
        {0, 5, 13, 49, 57, 49, 57, 117, 21, 0},
        {0, 6, 14, 14, 14, 14, 14, 14, 22, 0},
        {0, 6, 14, 14, 14, 14, 14, 14, 22, 0},
        {0, 6, 14, 48, 56, 14, 14, 14, 22, 0},
        {0, 6, 14, 14, 14, 14, 14, 14, 22, 0},
        {0, 6, 14, 14, 14, 14, 14, 14, 22, 0},
        {0, 6, 14, 14, 14, 14, 14, 14, 22, 0},
        {0, 6, 14, 14, 14, 14, 14, 14, 22, 0},
        {0, 7, 15, 15, 15, 15, 15, 15, 23, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        });
        hutten = new Room("hutten", "hutten", backgroundLayer, objectLayer, tiles, new int[][]{{0, 0, 0, 0, 0, 0, 0, 116, 0, 0},
        {0, 5, 13, 13, 13, 13, 13, 117, 21, 0},
        {0, 6, 14, 14, 14, 14, 14, 14, 22, 0},
        {0, 6, 14, 14, 14, 14, 14, 14, 22, 0},
        {0, 6, 14, 14, 14, 14, 14, 14, 22, 0},
        {0, 6, 14, 14, 14, 14, 14, 14, 22, 0},
        {0, 6, 14, 14, 14, 14, 14, 14, 22, 0},
        {0, 6, 14, 14, 14, 14, 14, 14, 22, 0},
        {0, 6, 14, 14, 14, 14, 14, 14, 22, 0},
        {0, 7, 15, 15, 15, 15, 15, 15, 23, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        });
        canteen = new Room("Canteen", "Canteen", backgroundLayer, objectLayer, tiles, new int[][]{{0, 0, 0, 0, 0, 0, 0, 116, 0, 0},
        {0, 5, 13, 13, 13, 13, 13, 117, 21, 0},
        {0, 6, 14, 14, 14, 14, 14, 14, 22, 0},
        {0, 6, 14, 14, 14, 14, 14, 14, 22, 0},
        {0, 6, 14, 48, 56, 14, 14, 14, 22, 0},
        {0, 6, 14, 14, 14, 14, 14, 14, 22, 0},
        {0, 6, 14, 14, 14, 14, 14, 14, 22, 0},
        {0, 6, 14, 48, 56, 14, 48, 56, 22, 0},
        {0, 6, 14, 14, 14, 14, 14, 14, 22, 0},
        {0, 7, 15, 15, 15, 15, 15, 15, 23, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        });
        knoldene = new Room("knoldene", "knoldene", backgroundLayer, objectLayer, tiles, new int[][]{{0, 0, 0, 0, 0, 0, 0, 116, 0, 0},
        {0, 5, 13, 49, 57, 13, 117, 117, 21, 0},
        {0, 6, 14, 14, 14, 14, 14, 14, 22, 0},
        {0, 6, 14, 14, 14, 14, 14, 14, 22, 0},
        {0, 6, 14, 48, 56, 14, 14, 14, 22, 0},
        {0, 6, 14, 14, 14, 14, 14, 14, 22, 0},
        {0, 6, 14, 14, 14, 14, 14, 14, 22, 0},
        {0, 6, 14, 14, 14, 14, 14, 14, 22, 0},
        {0, 6, 14, 14, 14, 14, 14, 14, 22, 0},
        {0, 7, 15, 15, 15, 15, 15, 15, 23, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        });
        u163 = new Room("u163", "u163", backgroundLayer, objectLayer, tiles, new int[][]{{0, 0, 0, 0, 0, 0, 0, 116, 0, 0},
        {0, 5, 13, 117, 13, 13, 13, 117, 21, 0},
        {0, 6, 14, 14, 14, 14, 14, 14, 22, 0},
        {0, 6, 14, 14, 14, 14, 14, 14, 22, 0},
        {0, 6, 14, 48, 56, 14, 14, 14, 22, 0},
        {0, 6, 14, 14, 14, 14, 14, 14, 22, 0},
        {0, 6, 14, 14, 14, 14, 14, 14, 22, 0},
        {0, 6, 14, 14, 14, 14, 14, 14, 22, 0},
        {0, 6, 14, 14, 14, 14, 14, 14, 22, 0},
        {0, 7, 15, 15, 15, 15, 15, 15, 23, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        });
        u170 = new Room("u170", "u170", backgroundLayer, objectLayer, tiles, new int[][]{{0, 0, 0, 0, 0, 0, 0, 116, 0, 0},
        {0, 5, 13, 13, 13, 13, 13, 117, 21, 0},
        {0, 6, 14, 14, 14, 14, 14, 14, 22, 0},
        {0, 6, 14, 14, 14, 14, 14, 14, 22, 0},
        {0, 6, 14, 14, 14, 14, 48, 56, 22, 0},
        {0, 6, 14, 14, 14, 14, 14, 14, 22, 0},
        {0, 6, 14, 14, 14, 14, 48, 56, 22, 0},
        {0, 6, 14, 14, 14, 14, 14, 14, 22, 0},
        {0, 6, 14, 14, 14, 14, 14, 14, 22, 0},
        {0, 7, 15, 15, 15, 15, 15, 15, 23, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        });
        u180 = new Room("u180", "u180", backgroundLayer, objectLayer, tiles, new int[][]{{0, 0, 0, 0, 0, 0, 0, 116, 0, 0},
        {0, 5, 17, 49, 57, 13, 13, 117, 21, 0},
        {0, 6, 14, 14, 14, 14, 14, 14, 22, 0},
        {0, 6, 14, 14, 14, 14, 14, 14, 22, 0},
        {0, 6, 14, 48, 56, 14, 14, 14, 22, 0},
        {0, 6, 14, 14, 14, 14, 14, 14, 22, 0},
        {0, 6, 14, 14, 14, 14, 14, 14, 22, 0},
        {0, 6, 14, 14, 14, 14, 14, 14, 22, 0},
        {0, 6, 14, 14, 14, 14, 14, 14, 22, 0},
        {0, 7, 15, 15, 15, 15, 15, 15, 23, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        });

        rooms.add(canteen);

//        outside = new Room("Outside", "outside the main entrance of the university");
//        exam = new Room("Exam", "in the exam room");
//        exam.setLocked(true);
//        campus = new Room("Campus", "in the hallway of the university");
//        bookstore = new Room("Bookstore", "in the bookstore");
//        bookstore.setLocked(true);
//        hutten = new Room("Gydehutten", "in the Gydehutten");
//        hutten.setLocked(true);
//        downunder = new Room("Fredagsbar", "in Downunder");
//        canteen = new Room("Canteen", "in the canteen");
//        knoldene = new Room("Knoldene", "in the Knoldene");
//        u163 = new Room("U163", "in U163");
//        u170 = new Room("U170", "in U170");
//        u180 = new Room("U180", "in U180");
//
//        outside.setExit("north", campus);
//
//        campus.setExit("north", hutten);
//        campus.setExit("west", exam);
//        campus.setExit("east", bookstore);
//
//        exam.setExit("east", campus);
//
//        bookstore.setExit("west", campus);
//
//        hutten.setExit("south", campus);
//        hutten.setExit("east", canteen);
//        hutten.setExit("north", knoldene);
//        hutten.setExit("down", downunder);
//
//        canteen.setExit("west", hutten);
//        canteen.setExit("north", downunder);
//
//        downunder.setExit("up", hutten);
//        downunder.setExit("south", canteen);
//
//        knoldene.setExit("north", u163);
//        knoldene.setExit("west", u170);
//        knoldene.setExit("east", u180);
//        knoldene.setExit("south", hutten);
//
//        u163.setExit("south", knoldene);
//        u170.setExit("east", knoldene);
//        u180.setExit("west", knoldene);
//

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
