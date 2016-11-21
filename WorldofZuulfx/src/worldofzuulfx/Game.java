package worldofzuulfx;

import java.util.ArrayList;
import java.util.HashMap;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import worldofzuulfx.Items.Book;
import worldofzuulfx.Items.Coffee;
import worldofzuulfx.Items.CoffeeVoucher;
import worldofzuulfx.Items.Drink;
import worldofzuulfx.Items.Item;
import worldofzuulfx.Items.Note;
import worldofzuulfx.NPC.*;
import worldofzuulfx.Quest.Quest;
import worldofzuulfx.Quest.QuestFactory;
import worldofzuulfx.Quest.QuestHandler;
import worldofzuulfx.Quest.Reward;
import worldofzuulfx.Minigame.RockPaperScissors;
import worldofzuulfx.sprites.SpriteBase;
import worldofzuulfx.tiles.Tile;
import worldofzuulfx.tiles.TileLoader;
import worldofzuulfx.tiles.TileMap;

public class Game {

    //private Room currentRoom;
    private boolean finished;
//    private Player player;
    private QuestHandler questHandler;
//    private ArrayList<Room> rooms;
    private PartyGuy partyguy;
    private HashMap<String, Quest> allGameQuests;
    private ArrayList<String> RPSCommands;
    private Player player;
    private TileMap tileMap;
    private ArrayList<Room> rooms;
//    private HashMap<String, DrawableRoom> rooms;
    private Pane background;
    private Pane sprites;
    private AnimationTimer timer;

    public Game(Pane background, Pane sprites, Scene scene) // Constructor - ingen argumenter
    {
        this.background = background;
        this.sprites = sprites;
        addInputControls(scene);
        ConsoleInfo.setConsoleData("Test");
        player = new Player("Player-name", this.sprites, new Image("http://i.imgur.com/zLwFeje.png"), 
                background.getLayoutX() + 64.0, background.getLayoutY() + 64.0);
        player.setCanCollide(true);
        player.setDx(32);
        player.setDy(16);

        createRooms();

        makeTestGame();//Denne skal slettes - skal kun bruges til at teste spillet!!!!
        //TODO
//        initNPCs();
//        initQuests();

        gameLoop();
        play();
    }

    private void gameLoop() {

        // game loop
        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {

                updateSprites();

                checkCollisions();

                cleanupSprites();

            }

        };

        timer.start();

    }

    public void updateSprites() {
//        player.move();
        player.updateUI();
    }

    public void checkCollisions() {
        boolean result = false;
        String currentRoomID = this.player.getCurrentRoom().getID();

        Room currentRoom = this.getRoom(currentRoomID);

        for (Tile tile : currentRoom.getTileMap().getTileTerrain()) {

            if (player.collidesWith(tile)) {
                result = true;
            }
        }
        if (result) {
            //player.stopMovement();
        }
//       boolean result = false;
//        for (Tile tile : tileMap.getTileTerrain()) {
//            if (player.getSprite().collidesWith(tile)) {
//                result = true;
//            }
//        }
    }

    public void cleanupSprites() {

    }

    private void addInputControls(Scene scene) {

        // keyboard handler: key pressed
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if (key.getCode() == KeyCode.RIGHT) {
                player.move(SpriteBase.spriteActions.RIGHT);

            }
            if (key.getCode() == KeyCode.LEFT) {
                player.move(SpriteBase.spriteActions.LEFT);

            }
            if (key.getCode() == KeyCode.UP) {
                player.move(SpriteBase.spriteActions.UP);

            }
            if (key.getCode() == KeyCode.DOWN) {
                player.move(SpriteBase.spriteActions.DOWN);

            }

        });
    }

    public HashMap<String, Quest> getAllGameQuests() {
        return allGameQuests;
    }

    private void initQuests() {
        // Initialize Quests and Quest handling
        this.allGameQuests = new HashMap<>();
        QuestFactory qFactory = new QuestFactory(this.player);
        questHandler = new QuestHandler(this.player);

        // Create quests
        Quest goToCampusQ = qFactory.roomQuest("Campus", "Go to Campus", null);
        goToCampusQ.setPostAction(() -> {
            String postCompleteMessage = "The final exam for the semester awaits, but you're not prepared. "
                    + "\nYou have spent your time on anything else than studying."
                    + "\n\nFind your way to the exam room by using the map. "
                    + "Remember you can navigate using the go-command.";
            ConsoleInfo.setConsoleData(postCompleteMessage);
        });

        Quest goToExamnRoomQ = qFactory.roomQuest("Exam", "Go to the examn room.", null);
        getRoom("Exam").setLocked(false); // Temporarily unlock so the player can navigate here
        goToExamnRoomQ.setPostAction(() -> {

            String postCompleteMessage = "To prepare for the final exam you'll have to explore the university "
                    + "and collect both ECTS-points "
                    + "and important notes that you were too lazy to take during the semester."
                    + "\n\nThe exam room will unlock when you have collected enough ECTS-points. "
                    + "\n\nYou have been given a new Quest. "
                    + "To view its content type ‘quest’ in the console. You can type this command if you are lost.";
            ConsoleInfo.setConsoleData(postCompleteMessage);
            // Navigate the player out of the exam room
            Room campus = getRoom("Campus");
            this.player.navigateSilentlyTo(campus);
            getRoom("Exam").setLocked(true); // Lock the exam room again

            // unlock rooms so the player can move further
            getRoom("Gydehutten").setLocked(false);
            getRoom("Bookstore").setLocked(false);
        });

        Quest goToU163RoomQ = qFactory.roomQuest("U163", "Go to teaching room U163.", null);
        goToU163RoomQ.setPostAction(() -> {

            String postCompleteMessage = "This is the teaching room for Object Oriented Programming."
                    + "\n\nDaniel are excited to prepare you for the upcoming exam, "
                    + "but Anders is not very impressed with this being the first time you show up for OOP lessons "
                    + "in this semester. "
                    + "\n\nAs a penalty, Anders wants you to get him a cup of coffee in the canteen. "
                    + "\n\nTo buy Coffee, Anders has given you Coffee Vouchers. "
                    + "You can exchange Coffee Vouchers for coffee in the canteen. "
                    + "\n\nHurry up! The lesson is about to begin!";
            ConsoleInfo.setConsoleData(postCompleteMessage);
            this.player.getInventory().addItem(new CoffeeVoucher("Coffee Voucher", 0, 10));
        });

        Quest goToCanteenQ = qFactory.roomQuest("Canteen", "Go to the Canteen.", null);
        goToCanteenQ.setPostAction(() -> {
            String postCompleteMessage = "This is the canteen where you can exchange Coffee Vouchers for coffee"
                    + "\n\nUse the Coffee Voucher by typing 'use' followed by 'coffee voucher' in the console.";
            ConsoleInfo.setConsoleData(postCompleteMessage);
        });

        Quest coffeeQ = qFactory.pickupItemQuest(Coffee.class, "Buy a coffee.", null);
        coffeeQ.setPostAction(() -> {
            String postCompleteMessage = "Quickly! Bring the coffee to Anders in U163.";

            ConsoleInfo.setConsoleData(postCompleteMessage);
        });

        NPC anders = getRoom("U163").getNPC("Anders");
        Quest deliverCoffeeQ = qFactory.deliveryQuest(Coffee.class, anders, "Deliver the coffee to Anders.", null);
        deliverCoffeeQ.setPostAction(() -> {
            String postCompleteMessage = "Anders:"
                    + "\n\"Coffee is the source for maintaining your energy-level. "
                    + "Throughout the game you have to maintain your energy-level by drinking coffee "
                    + "but the amount of Coffee Vouchers is limited so use them wisely. "
                    + "If you run out of energy, you will black out and wake up at a random place in the university. "
                    + "\n\nBe careful. You can only black out a few times before you remain unconscious and the game is lost.\""
                    + "\n\nTo drink a Coffee type ‘use coffee’ followed by ‘drink’ in the console.";

            ConsoleInfo.setConsoleData(postCompleteMessage);
        });

        Quest leaveU163Q = qFactory.roomQuest("Knoldene", "Leave U163", null);
        leaveU163Q.setPostAction(() -> {
            String postCompleteMessage = "Daniel:"
                    + "\n\"The OOP lesson has not finished yet. To attend to the course, you need your OOP book. "
                    + "You can collect the book in the Book store.\""
                    + "\n\nFind your way to the Book store and collect the book. "
                    + "Remember that you can use the map if you’re lost.";
            ConsoleInfo.setConsoleData(postCompleteMessage);
        });

        Quest goToBookStoreQ = qFactory.roomQuest("Bookstore", "Go to the Bookstore", null);
        goToBookStoreQ.setPostAction(() -> {
            String postCompleteMessage = "This is the Book store. "
                    + "\nIn here you can collect books that are needed to attend to different courses at the university. "
                    + "\n\nTo search a room for items type ‘search’ in the console. "
                    + "To collect an item type ‘take’ followed by the name of the item you want to collect. "
                    + "e.g. ‘take oopbook’ in the console.";
            ConsoleInfo.setConsoleData(postCompleteMessage);

            getRoom("Bookstore").getRoomInventory().addItem(new Book("OOP-Book", 400));
        });

        Quest returnToU163 = qFactory.roomQuest("U163", "Return to U163", null);
        returnToU163.setPostAction(() -> {
            String postCompleteMessage = "Daniel:"
                    + "\n\"Now that you have the book, the course can begin.\"";

            ConsoleInfo.setConsoleData(postCompleteMessage);
            initPartyGuy();
        });

        Quest u170Lecture = qFactory.roomQuest("U170", "Participate in lecture", null);
        u170Lecture.setPostAction(() -> {
            String postCompleteMessage = "Lone:"
                    + "\n\"Welcome to the ISE-lecture."
                    + "\nBefore we can start the lecture you need your ISE Book."
                    + "\nGet your book in the Bookstore.";

            ConsoleInfo.setConsoleData(postCompleteMessage);

            getRoom("Bookstore").getRoomInventory().addItem(new Book("ISE-Book", 400));
        });

        Quest bookstoreIse = qFactory.roomQuest("Bookstore", "Go to Bookstore", null);
        bookstoreIse.setPostAction(() -> {
            String postCompleteMessage = "Bookstore"
                    + "\nSearch for the ISE-Book";

            ConsoleInfo.setConsoleData(postCompleteMessage);
        });

        Quest returnToU170 = qFactory.roomQuest("U170", "Return to U170", null);
        returnToU170.setPostAction(() -> {
            String postCompleteMessage = "Lone:"
                    + "\n\"Great! You've collected the book."
                    + "\nThe lecture can now begin\"";
            ConsoleInfo.setConsoleData(postCompleteMessage);
        });

        Quest u180Lecture = qFactory.roomQuest("U180", "Participate in lecture", null);
        u180Lecture.setPostAction(() -> {
            String postCompleteMessage = "Erik:"
                    + "\n\"Welcome to the COS-lecture."
                    + "\nBefore we can start the lecture you need your BOS Book."
                    + "\nGet your book in the Bookstore.";

            ConsoleInfo.setConsoleData(postCompleteMessage);

            getRoom("Bookstore").getRoomInventory().addItem(new Book("COS-Book", 400));
        });

        Quest bookstoreCos = qFactory.roomQuest("Bookstore", "Go to Bookstore", null);
        bookstoreCos.setPostAction(() -> {
            String postCompleteMessage = "Search for the COS Book";

            ConsoleInfo.setConsoleData(postCompleteMessage);

        });

        Quest returnToU180 = qFactory.roomQuest("u180", "Return to U180", null);
        returnToU180.setPostAction(() -> {
            String postCompleteMessage = "Erik:"
                    + "\n\"Great! You've collected the book."
                    + "\nThe lecture can now begin\"";

            ConsoleInfo.setConsoleData(postCompleteMessage);

        });

        // Give the player an initial quest
        this.player.setActiveQuest(goToCampusQ, true);

        // Chain quests together in a "tutorial"
        goToCampusQ.setChainQuest(goToExamnRoomQ);
        goToExamnRoomQ.setChainQuest(goToU163RoomQ);
        goToU163RoomQ.setChainQuest(goToCanteenQ);
        goToCanteenQ.setChainQuest(coffeeQ);
        coffeeQ.setChainQuest(deliverCoffeeQ);
        deliverCoffeeQ.setChainQuest(leaveU163Q);
        leaveU163Q.setChainQuest(goToBookStoreQ);
        goToBookStoreQ.setChainQuest(returnToU163);

        // Chain the rest of the quests together in the game
        u170Lecture.setChainQuest(bookstoreIse);
        bookstoreIse.setChainQuest(returnToU170);

        u180Lecture.setChainQuest(bookstoreCos);
        bookstoreCos.setChainQuest(returnToU180);

        // Save a reference to the rest of the quests
        this.allGameQuests.put("goToCampusQ", goToCampusQ);
        this.allGameQuests.put("goToExamRoomQ", goToExamnRoomQ);
        this.allGameQuests.put("goToU163RoomQ", goToU163RoomQ);
        this.allGameQuests.put("goToCanteenQ", goToCanteenQ);
        this.allGameQuests.put("coffeeQ", coffeeQ);
        this.allGameQuests.put("deliverCoffeeQ", deliverCoffeeQ);
        this.allGameQuests.put("leaveU163Q", leaveU163Q);
        this.allGameQuests.put("goToBookStoreQ", goToBookStoreQ);
        this.allGameQuests.put("returnToU163", returnToU163);
        this.allGameQuests.put("u170Lecuture", u170Lecture);
        this.allGameQuests.put("bookstoreIse", bookstoreIse);
        this.allGameQuests.put("returnToU170", returnToU170);
        this.allGameQuests.put("u180Lecture", u180Lecture);
        this.allGameQuests.put("bookstoreCos", bookstoreCos);
        this.allGameQuests.put("returnToU180", returnToU180);
    }

    private void initPartyGuy() {
        partyguy = new PartyGuy("PartyGuy", "Den festlige ven");
        partyguy.spawn(getRooms(false), getAllGameQuests());
    }

    public Room getRoom(String ID) {

        for (Room r : this.rooms) {
            if (r.getID().equalsIgnoreCase(ID)) {
                return r;
            }
        }

        return null;
    }

    public ArrayList<Room> getRooms(Boolean lockedRooms) {
        ArrayList<Room> roomsToReturn = new ArrayList<>();
        //TODO Kan checkes
        for (Room room : this.rooms) {

            if (room.isLocked() == lockedRooms) {
                roomsToReturn.add(room);
            }
        }
        return roomsToReturn;
    }

    /**
     * Returns an ArrayList with the names of NPC's in the specified room.
     *
     * @param room The room to look for NPC's in.
     * @return An ArrayList<String> with NPC names.
     */
    public ArrayList<String> getNPCNameList(Room room) {
        ArrayList<String> list = new ArrayList<>();

        for (NPC person : room.getNPCList()) {
            list.add(person.getName());
        }

        return list;
    }

    private void createRooms() {
        Room outside, exam, campus, downunder, bookstore, hutten, canteen, knoldene, u163, u170, u180; // Varibler af typen Room

        rooms = new ArrayList<>();
        TileLoader tLoader = new TileLoader(new Image("http://i.imgur.com/E04tZvB.png"), 32, 32);
        HashMap<Integer, Tile> tiles = tLoader.getTiles();

        outside = new Room("outside", "outside", background, tiles, new int[][]{{0, 0, 0, 0, 0, 0, 0, 116, 0, 0},
        {0, 5, 13, 49, 57, 13, 13, 117, 21, 0},
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

        exam = new Room("exam", "exam", background, tiles, new int[][]{{0, 0, 0, 0, 0, 0, 0, 116, 0, 0},
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

        campus = new Room("campus", "campus", background, tiles, new int[][]{{0, 0, 0, 0, 0, 0, 0, 116, 0, 0},
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
        downunder = new Room("downunder", "downunder", background, tiles, new int[][]{{0, 0, 0, 0, 0, 0, 0, 116, 0, 0},
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
        bookstore = new Room("bookstore", "bookstore", background, tiles, new int[][]{{0, 0, 0, 0, 0, 0, 0, 116, 0, 0},
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
        hutten = new Room("hutten", "hutten", background, tiles, new int[][]{{0, 0, 0, 0, 0, 0, 0, 116, 0, 0},
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
        canteen = new Room("Canteen", "Canteen", background, tiles, new int[][]{{0, 0, 0, 0, 0, 0, 0, 116, 0, 0},
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
        knoldene = new Room("knoldene", "knoldene", background, tiles, new int[][]{{0, 0, 0, 0, 0, 0, 0, 116, 0, 0},
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
        u163 = new Room("u163", "u163", background, tiles, new int[][]{{0, 0, 0, 0, 0, 0, 0, 116, 0, 0},
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
        u170 = new Room("u170", "u170", background, tiles, new int[][]{{0, 0, 0, 0, 0, 0, 0, 116, 0, 0},
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
        u180 = new Room("u180", "u180", background, tiles, new int[][]{{0, 0, 0, 0, 0, 0, 0, 116, 0, 0},
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
//        rooms = new ArrayList<Room>();
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
//
//        // CurrentRoom tildeles referencen til Outside.
        getPlayer().navigateTo(outside);
    }

    public void play() {
        printWelcome(); //En velkomst 

        // Nedensåtende håndter, at programmet hele tiden spørger efter user-input
        // processCommand returner en Boolean - skal programmet forsætte eller ej.
//        while (!finished) {
//            Command command = parser.getCommand(); // Spørger efter user-input
//            //TODO Der skal implementeres en ny måde at håndtere gameplayet på!
//            // Starter.gui.clearTxt();
//            finished = processCommand(command);
//        }
        ConsoleInfo.setConsoleData("Thank you for playing.  Good bye.");
    }

    private void printWelcome() {
        // Velkomst

        ConsoleInfo.setConsoleData("Welcome " + getPlayer().getName() + ", to the World of Zuul!");
        ConsoleInfo.setConsoleData("World of Zuul is a new, incredibly boring adventure game.");
        ConsoleInfo.setConsoleData("Type '" + CommandWord.HELP + "' if you need help.");
        ConsoleInfo.setConsoleData("");
        showInfo();
    }

    private boolean processCommand(Command command) {
        // Starter med at sætte variablen WantToQuit lig False. 
        // På denne måde sikre vi os, at vi vil få en værdien False retur, hvis ingen af if-sætningerne er sande.
        boolean wantToQuit = false;

        // Dette er en midlertidig variable, da den kun bruges i IF-sætningerne. Dette gør, at det ikke er nødvendigt at benytte getCommandWord().
        CommandWord commandWord = command.getCommandWord();
        // Tjek om det er en ukendt command
        if (null != commandWord) {
            switch (commandWord) {
                case UNKNOWN: {
                    ConsoleInfo.setConsoleData("I don't know what you mean...");
                    break;
                }
                case HELP: {
                    printHelp();
                    break;
                }
                case GO: {
                    ConsoleInfo.clearData();
                    goRoom(command);
                    break;
                }
                case USE: {
                    use(command);
                    break;
                }
                case SEARCH: {
                    search(command);
                    break;
                }
                case TAKE: {
                    take(command);
                    break;
                }
                case QUEST: {
                    Quest activeQuest = this.player.getActiveQuest();
                    if (activeQuest != null) {
                        ConsoleInfo.setConsoleData(activeQuest.toString());
                    } else {
                        ConsoleInfo.setConsoleData("You have no quests!");
                    }
                    break;
                }
                case INFO: {
                    ConsoleInfo.setConsoleData(getPlayer().getCurrentRoom().getLongDescription());
                    break;
                }
                case QUIT: {
                    wantToQuit = quit(command);
                    break;
                }
                case CHALLENGE: {
                    challenge(command);
                }
            }
        }
        return wantToQuit;
    }

    private void printHelp() {
        ConsoleInfo.setConsoleData("You are lost. You are alone. You wander");
        ConsoleInfo.setConsoleData("around at the university.");
        ConsoleInfo.setConsoleData("");
        ConsoleInfo.setConsoleData("Your command words are:");
    }

    /**
     * The command to start the minigame if the player is within downunder
     *
     * @param command
     */
    private void challenge(Command command) {
        RockPaperScissors rpsMiniGame = new RockPaperScissors();

        if (getPlayer().getCurrentRoom() == getRoom("Fredagsbar")) {
            ConsoleInfo.setConsoleData("I hereby challenge thee to an epic battle of 'ROCK PAPER AND SCISSOR' *epic drumroll*");
            ConsoleInfo.setConsoleData("Sound trumpets! let our bloody colours wave! And either victory, or else a grave. ");
            ConsoleInfo.setConsoleData("Just kidding. If you win I will give you coffee, if you lose then your energy will drain");
            ConsoleInfo.setConsoleData("");

            if (!command.hasSecondWord()) {

                ConsoleInfo.setConsoleData("Use one of the following commands:");
                ConsoleInfo.setConsoleData(">");
                rpsMiniGame.play();
                if (rpsMiniGame.getMoveComparison() == 1) {
                    getPlayer().pickupItem(new Drink("Øl", 1, 1, true));
                }
                if (rpsMiniGame.getMoveComparison() == -1) {
                    getPlayer().increaseEnergy(-5);
                }
                return;
            }
            ConsoleInfo.setConsoleData("Just type 'use' - no need for a second word");

        }
        if (getPlayer().getCurrentRoom() != getRoom("Fredagsbar")) {
            ConsoleInfo.setConsoleData("There is a time and place for everything, and now is not time for a challenge.");
        }
    }

    private void goRoom(Command command) {
        // Tjekker om der er to ord - f.eks. "go east"
        if (!command.hasSecondWord()) {
            ConsoleInfo.setConsoleData("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        Room nextRoom = getPlayer().getCurrentRoom().getExit(direction);

        if (nextRoom == null) {
            // Hvis der er en ingen dør
            ConsoleInfo.setConsoleData("There is no door!");
        } else if (!nextRoom.isLocked()) {
            // Hvis der er en dør, så
            getPlayer().navigateTo(nextRoom);
            showInfo();
            getPlayer().barValueChanged(getPlayer().getEnergyBar());
            if (getAllGameQuests().get("returnToU163").isCompleted()) {
                if (getPlayer().getCurrentRoom() == getPartyGuy().getCurrentRoom()) {
                    ConsoleInfo.clearData();
                    ConsoleInfo.setConsoleData("You met your good ol' buddy ol' pal the Partyguy who took you to 'FredagsBaren' but where did he go?");
                    getPartyGuy().partyTime(getPlayer(), getRoom("FredagsBar"), getRooms(false), getAllGameQuests());
                    showInfo();
                }
            }
        } else {
            showInfo();
            ConsoleInfo.setConsoleData("The door to this room is locked. Try another door.");
        }
    }

    public boolean quit(Command command) {
        // Dette tjekker om brugeren skriver to ord - f.eks. "quit game". Dette vil resultere i "Quit what?"
        // Hvis nej, så sendes en True retur.
        if (command.hasSecondWord()) {
            ConsoleInfo.setConsoleData("Quit what?");
            return false;
        } else {
            return true;
        }
    }

    public void makeTestGame() {

        // Dette skal slettes!!!!! Benyttes til at teste spillet!
        getPlayer().pickupItem(new Drink("Vin", 1, 1, true));
        getPlayer().pickupItem(new Note("MyNotes", 1, "Dette er en test"));
        getPlayer().pickupItem(new Drink("Cider", 1, 1, true));
        getPlayer().pickupItem(new Note("MyNotes2", 1, "Dette er en test"));
        getPlayer().pickupItem(new Drink("Øl", 1, 1, true));
        getPlayer().pickupItem(new Note("MyNotes3", 1, "Dette er en test"));
        getPlayer().getCurrentRoom().getRoomInventory().addItem(new Drink("Beer", 10, 10, true));
    }

    private void use(Command command) {
        String items;
        String input;

        if (!command.hasSecondWord()) {

            ConsoleInfo.setConsoleData("Use one of the following items:");
            items = Util.arrayToString(getPlayer().getInventory().getStringList());
            ConsoleInfo.setConsoleData(items);
            ConsoleInfo.setConsoleData(">");
            input = "Get input";
            //The For-each loop iterates through the items (e.g Drink   Øl   Coffee)
            for (Item item : getPlayer().getInventory().getItemList()) {
                // Then checks if the item's description equals the userInput.
                if (item.getDescription().equalsIgnoreCase(input)) {
                    getPlayer().useItem(item);
                    return;
                }
            }
        } else {
            ConsoleInfo.setConsoleData("Just type 'use' - no need for a second word");
        }

    }

    public void search(Command command) {
        String itemString;
        itemString = "";
        ArrayList<Item> itemList;
        itemList = getPlayer().getCurrentRoom().getRoomInventory().getItemList();
        if (itemList.isEmpty()) {
            ConsoleInfo.setConsoleData("   You found nothing in this room");
            return;
        }

        try {
            for (Item item : itemList) {
                itemString += "  " + item.getDescription();
            }
            ConsoleInfo.setConsoleData(itemString);
        } catch (NullPointerException e) {

        }
    }

    public void take(Command command) {
        Item item;
        if (!command.hasSecondWord()) {
            ConsoleInfo.setConsoleData("Take what?");
            return;
        }
        try {
            item = getPlayer().getCurrentRoom().getRoomInventory().find(command.getSecondWord());
            if (item != null) {
                getPlayer().pickupItem(item);
                getPlayer().getCurrentRoom().getRoomInventory().removeItem(item);
            } else {
                ConsoleInfo.setConsoleData("");
            }
        } catch (NullPointerException e) {
        }
    }

    public void showInfo() {
        // TODO skal muligvis slettes
        ConsoleInfo.setConsoleData(getPlayer().getCurrentRoom().getLongDescription()
                + "\nPersons: " + getPlayer().getCurrentRoom().getPersonsString());
        ConsoleInfo.setConsoleData("-----------------------------"
                + "-----------------------------");
    }

    /**
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }

    public PartyGuy getPartyGuy() {
        return partyguy;
    }

    public void setFinished() {
        finished = true;
    }

    private void initNPCs() {
        Room u163 = getRoom("U163");
        NPC anders = new Lector("Anders", "Anders");
        NPC daniel = new Lector("Daniel", "Daniel");
        u163.addNPC(daniel);
        u163.addNPC(anders);
    }
}
