package worldofzuulfx;

import worldofzuulfx.Room.RoomFactory;
import worldofzuulfx.Room.Room;
import worldofzuulfx.Room.RoomList;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import worldofzuulfx.Events.*;
import worldofzuulfx.Interfaces.*;
import worldofzuulfx.Items.*;
import worldofzuulfx.NPC.*;
import worldofzuulfx.Minigame.RockPaperScissors;
import worldofzuulfx.Quest.Quest;
import worldofzuulfx.Quest.QuestInventory;
import worldofzuulfx.sprites.SpriteBase;

import worldofzuulfx.tiles.*;

public class Game implements NavigateListener, ItemPickupListener {

    private boolean finished;
    private boolean gameOver;
    private int timeLeft;
    private PartyGuy partyguy;
    private Player player;
    private ECTSHandler ectsHandler;
    private RoomList roomHandler;
    private RoomFactory roomFactory;
    private QuestInventory questInventory;
    private RockPaperScissors RPS;

    private AnimationTimer timer;
    public static HashMap<Integer, Tile> tiles;
    private Layers layers;

    public Game(Layers layers, String playerName, int gameMode) {
        // Loads a specific image containing all tiles to used throughout the game

        initTiles(gameMode);
        this.layers = layers;

        initRooms(gameMode);
        initPlayer(playerName);
        initECTSHandler();
        initNPCs();
        initPartyGuy();
        initQuests();

        gameLoop();
        // Navigates Player to Outside
        player.navigateTo(roomHandler.getRoom("outside"));
        printWelcome();
    }

    private void gameLoop() {

        // Creates a AnimationTimer which updates the players position
        // and checks for collision between tiles, items and NPC's.
        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {

                updateSprites();

                checkCollisions();

                if (isFinished()) {
                    timer.stop();
                }
            }
        };

        timer.start();
    }

    /**
     * Updates the players UI - i.e. the players position and the player's
     * inventory (Which item to be selected).
     *
     */
    private void updateSprites() {
        player.updateUI();
        player.getInventory().selectItem(player.getInventory().getSelectedItem());
    }

    /**
     * Checks for collision between player and Tiles, Collision between player
     * and Items, and collision between player and NPCs.
     */
    public void checkCollisions() {
        Room currentRoom = this.player.getCurrentRoom();
        double playerWidth = player.getBounds().getWidth();
        double playerHeight = player.getBounds().getHeight();
        double playersNextPosY = player.getNextPosY();
        double playersNextPosX = player.getNextPosX();
        Bounds boundsToBeChecked = null;
        
        for (SpriteBase sprite : currentRoom.getAllSpriteBases()) {
            if (sprite.getCanCollide()) {
                boundsToBeChecked = sprite.getBounds().getBoundsInLocal();
                if (boundsToBeChecked.intersects(playersNextPosX, playersNextPosY, playerWidth, playerHeight)) {
                    sprite.collides(player);
                    return;
                }
            }
        }
            
        // Moves the player if it did not collide with any objects - e.g. Items, tiles and NPCs.
        player.move(player.getNextPosX(), player.getNextPosY());
    }

    /**
     * Initiates all quests used throughout this game.
     */
    private void initQuests() {
        questInventory = new QuestInventory();
        questInventory.initQuests(roomHandler, player);
    }

    private void initPartyGuy() {
        partyguy = new PartyGuy("PartyGuy", "Party Guy", Game.tiles.get(123).getImageView().getImage());
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

    /**
     * Initiate all rooms based on a given GameLevel.
     *
     * @param gameMode
     */
    private void initRooms(int gameMode) {
        roomFactory = new RoomFactory(gameMode);
        roomHandler = new RoomList(roomFactory.createRooms(tiles, layers.getBackgoundLayer(), layers.getObjectsLayer()));
        roomHandler.getRoom("Gydehutten").setLocked(true);
        roomHandler.getRoom("Bookstore").setLocked(true);
    }

    /**
     * Initiate Player - which involves Player name, layers, Player-image and
     * the position of the player.
     */
    private void initPlayer(String playerName) {
        player = new Player(playerName, layers.getPlayerLayer(), Game.tiles.get(120).getImageView().getImage(),
                layers.getBackgoundLayer().getLayoutX() + 129.0, layers.getBackgoundLayer().getLayoutY() + 369.0);

        player.setCanCollide(true);
        player.setDx(32);
        player.setDy(16);
        player.getBounds().setHeight(14);
        player.getBounds().setWidth(30);
        player.addNavigateListener(this);
        player.addItemPickupListener(this);

        player.getInventory().setLayer(layers.getInventoryLayer());
    }

    /**
     * Prints out a Welcome message.
     */
    private void printWelcome() {
        String playerName = getPlayer().getName();
        
        String welcome = "Hello " + playerName + ". " +
                "You are a software student at the University of Southern Denmark \n" +
                "The final exam for the semester awaits, but you're not prepared. \n" +
                "You have spent your time on anything else than studying. \n\n" +
                "Find your way to Campus by using the arrow-keys on the keyboard.";
        ConsoleInfo.setConsoleData(welcome);
    }

    /**
     * The command to start the minigame if the player is within downunder
     *
     * @param command
     */
    private void challenge() {
        Thread rpsThread;
        player.setCanMove(false);
        rpsThread = new Thread() {
            // runnable for that thread
            public void run() {

                RPS = new RockPaperScissors();
                String txt;

                txt = "I hereby challenge thee to an epic battle of 'ROCK PAPER AND SCISSOR' *epic drumroll*"
                        + "\n Sound trumpets! let our bloody colours wave! And either victory, or else a grave. "
                        + "\n Just kidding. If you win I will give you coffee, if you lose then your energy will drai";
                // .play() waits for user-input.
                RPS.play();

                Platform.runLater(new Runnable() {

                    public void run() {
                        if (RPS.getMoveComparison() == 1) {
                            getPlayer().getInventory().addItem(ItemFactory.makeBeer());
                        }
                        if (RPS.getMoveComparison() == -1) {
                            getPlayer().increaseEnergy(-30);
                        }
                        player.setCanMove(true);
                    }

                });
                this.interrupt();
            }
        };
        rpsThread.start();
    }

    /**
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     *
     * @return the PartyGuy
     */
    public PartyGuy getPartyGuy() {
        return partyguy;
    }

    /**
     * Sets a flag which indicates game over
     */
    public void setFinished() {
        finished = true;
    }

    /**
     * Initiates all NPCs and places them in their respective room.
     */
    private void initNPCs() {
        // U163 NPCS
        Room u163 = getRoomHandler().getRoom("U163");
        NPC anders = new NPC("Anders", "Anders", Game.tiles.get(124).getImageView().getImage());
        anders.move(96, 64);
        NPC daniel = new NPC("Daniel", "Daniel", Game.tiles.get(125).getImageView().getImage());
        daniel.move(256, 64);
        u163.addNPC(daniel);
        u163.addNPC(anders);

        // U170 NPCS
        Room u170 = getRoomHandler().getRoom("U170");
        NPC lone = new NPC("Lone", "Lone", Game.tiles.get(121).getImageView().getImage());
        lone.move(96, 64);
        u170.addNPC(lone);

        // U180 NPCS
        Room u180 = getRoomHandler().getRoom("U180");
        NPC erik = new NPC("Erik", "Erik", Game.tiles.get(122).getImageView().getImage());
        erik.move(128, 64);
        u180.addNPC(erik);

    }

    /**
     * When the Player navigates to a new room, this methods is executed.
     *
     * @param event An event containing information about the Old Room and the
     * New Room
     */
    @Override
    public void navigated(NavigateEvent event) {
        Quest quest = player.getInactiveQuests().get("goToCampusQ");
        // Checks if the Quest - "goToCampusQ" is completed.
        if (quest != null && quest.isCompleted()) {
            // If true (statement above) - then checks if Player is not in the room "downunder".
            if (!player.getCurrentRoom().getID().equals("downunder")) {
                // Spawns PartyGuy in a random location, and always places him in the corner of the room
                partyguy.move(256, 256);
                ArrayList<Room> rooms = getRoomHandler().getRooms(false);
                rooms.remove(getRoomHandler().getRoom("outside"));
                partyguy.spawn(rooms);
            } else {
                // Spawns PartyGuy in Downunder, if the player is in Downunder.
                partyguy.navigateTo(getRoomHandler().getRoom("downunder"));
            }
        }
        //The new room is drawn.
        event.getNewRoom().draw();
    }

    /**
     * When Player picks up an item, this method is executed.
     *
     * @param event Contains information about the item and the player, who
     * picked up the item.
     */
    @Override
    public void itemPickedUp(ItemPickupEvent event) {
        Item item = event.getItem();
        // Checks if the item is not null.
        if (item != null) {
            // Removes the image from the layer.
            item.removeFromLayer();
        }
    }

    /**
     * @return the roomHandler
     */
    public RoomList getRoomHandler() {
        return roomHandler;
    }

    /**
     * @return the questInventory
     */
    public QuestInventory getQuestInventory() {
        return questInventory;
    }

    /**
     * @return the flag which indicates if the game is over.
     */
    public boolean isFinished() {
        return finished;
    }

    /**
     * Initiates the ECTSHandler and assigns Player and a given Room.
     */
    private void initECTSHandler() {
        Room examRoom = getRoomHandler().getRoom("exam");
        this.ectsHandler = new ECTSHandler(player, examRoom);
        player.getECTSBar().setValue(0);
    }

    /**
     * Initiates TileLoader and loads a tileset based on the gameMode. If no
     * gameMode is provided, a default tileset is loaded.
     *
     * @param gameMode 0 = normal mode or 1 = abnormal mode
     */
    private void initTiles(int gameMode) {
        TileLoader tLoader;

        switch (gameMode) {
            case 0: {
                tLoader = new TileLoader(new Image("resources/tiles1.png"), 32, 32);
                break;
            }
            case 1: {
                tLoader = new TileLoader(new Image("resources/tiles2.png"), 32, 32);
                break;
            }
            default: {
                tLoader = new TileLoader(new Image("resources/tiles1.png"), 32, 32);
                break;
            }
        }
        tiles = tLoader.getTiles();
    }

    /**
     * @return the RPS
     */
    public RockPaperScissors getRPS() {
        return partyguy.getRPS();
    }

    /**
     * @return the lostGame
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * @param lostGame the lostGame to sets
     */
    public void setGameOver() {
        this.gameOver = true;
    }
}
