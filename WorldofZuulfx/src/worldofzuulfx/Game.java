package worldofzuulfx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.image.Image;
import worldofzuulfx.Events.*;
import worldofzuulfx.Interfaces.*;
import worldofzuulfx.Items.*;
import worldofzuulfx.NPC.*;
import worldofzuulfx.Minigame.RockPaperScissors;
import worldofzuulfx.Quest.Quest;
import worldofzuulfx.Quest.QuestInventory;

import worldofzuulfx.tiles.*;

public class Game implements NavigateListener, ItemPickupListener {

    private boolean finished;
    private PartyGuy partyguy;
    private Player player;
    private ECTSHandler ectsHandler;
    private RoomHandler roomHandler;
    private RoomFactory roomFactory;
    private QuestInventory questInventory;
    private RockPaperScissors RPS;

    private AnimationTimer timer;
    public static HashMap<Integer, Tile> tiles;
    private Layers layers;

    public Game(Layers layers) {
        TileLoader tLoader = new TileLoader(new Image("http://i.imgur.com/KrRh335.png"), 32, 32);
        tiles = tLoader.getTiles();
        this.layers = layers;
        
       
        roomHandler = new RoomHandler();
         roomFactory = new RoomFactory();
        roomHandler.setRooms(roomFactory.createRooms(tiles, layers.getBackgoundLayer(), layers.getObjectsLayer()));

        questInventory = new QuestInventory();

        initPlayer();
        initECTSHandler();

        initNPCs();
        initPartyGuy();
        questInventory.initQuests(roomHandler, player);

        gameLoop();

        player.navigateTo(roomHandler.getRoom("outside"));
        printWelcome();
    }

    private void gameLoop() {

        // game loop
        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {

                updateSprites();

                checkCollisions();

                cleanupSprites();

                if (isFinished()) {
                    timer.stop();
                }

            }

        };

        timer.start();

    }

    public void updateSprites() {
        player.updateUI();
        player.getInventory().selectItem(player.getInventory().getSelectedItem());
    }

    public void checkCollisions() {
        Tile nextTile;
        Room currentRoom = this.player.getCurrentRoom();

        // Check tile collision
        for (Tile tile : currentRoom.getTileMap().getTileTerrain()) {

            if (tile.getCanCollide()) {
                if (tile.getBounds().getBoundsInLocal().intersects(player.getNextPosX(), player.getNextPosY(), player.getBounds().getWidth(), player.getBounds().getHeight())) {
                    
                    if (tile.canTeleport() && player.navigateTo(tile.getNextRoom())) {

                        // The Player needs to moved with the offset 1.
                        nextTile = tile.getNextRoom().getTileMap().getTile(tile.getNextPos());
                        player.move(nextTile.getX() + 1, nextTile.getY() + 1);
                    }
                    
                    // Reset the nextPos since a collision was detected
                    player.setNextPosX(player.getX());
                    player.setNextPosY(player.getY());
                    return;
                }
            }
        }

        // Check item collision
        for (Item item : currentRoom.getRoomInventory().getItemList()) {
            if (item.getCanCollide()) {
                if (item.getBounds().getBoundsInLocal().intersects(player.getNextPosX(), player.getNextPosY(), player.getBounds().getWidth(), player.getBounds().getHeight())) {
                    
                    if (item.canTeleport()) {
                        player.navigateTo(item.getNextRoom());
                    } else {
                        // Pick up the item
                        player.pickupItem(item);
                        
                        // Reset the nextPos since a collision was detected
                        player.setNextPosX(player.getX());
                        player.setNextPosY(player.getY());
                    }
                    return;
                }
            }
        }

        // Check NPC collision
        for (NPC npc : currentRoom.getNPCList()) {
            if (npc.getCanCollide()) {
                if (npc.getBounds().getBoundsInLocal().intersects(player.getNextPosX(), player.getNextPosY(), player.getBounds().getWidth(), player.getBounds().getHeight())) {
                    
                    if (npc.canTeleport()) {
                        player.navigateTo(npc.getNextRoom());
                    } else {
                        // Reset the nextPos since a collision was detected
                        player.setNextPosX(player.getX());
                        player.setNextPosY(player.getY());
                        player.setNearNPC(npc);

                        if (npc instanceof PartyGuy) {
                            if (player.getCurrentRoom() == getRoomHandler().getRoom("downunder")) {
                                challenge();
                            } else {
                                player.getInventory().addItem(((PartyGuy) npc).giveItem());
                            }

                        }
                    }
                    return;
                }
            }
        }

        player.move(player.getNextPosX(), player.getNextPosY());
    }

    public void cleanupSprites() {

    }

    private void initPartyGuy() {
        partyguy = new PartyGuy("PartyGuy", "Den festlige ven", Game.tiles.get(123).getImageView().getImage());
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

    public void initPlayer() {
        player = new Player("Player-name", layers.getPlayerLayer(), new Image("http://i.imgur.com/zLwFeje.png"),
                layers.getBackgoundLayer().getLayoutX() + 65.0, layers.getBackgoundLayer().getLayoutY() + 65.0);

        player.setCanCollide(true);
        player.setDx(32);
        player.setDy(16);
        player.getBounds().setHeight(14);
        player.getBounds().setWidth(30);
        player.addNavigateListener(this);
        player.addItemPickupListener(this);

        player.getInventory().setLayer(layers.getInventoryLayer());
    }

    private void printWelcome() {
        // Velkomst
        String welcome = "Welcome " + getPlayer().getName() + ", to the World of Zuul!"
                + "\n World of Zuul is a new, incredibly boring adventure game.";
        ConsoleInfo.setConsoleData(welcome);
        showInfo();
    }

    /**
     * The command to start the minigame if the player is within downunder
     *
     * @param command
     */
    private void challenge() {
        Thread rpsThread;
        new Thread() {

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
                    }

                });
            }
        }.start();

        return;
    }

    public void showInfo() {
        // TODO skal muligvis slettes
        ConsoleInfo.setConsoleData(getPlayer().getCurrentRoom().getLongDescription());
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
        Room u163 = getRoomHandler().getRoom("U163");
        NPC anders = new NPC("Anders", "Anders", Game.tiles.get(124).getImageView().getImage());
        anders.move(96, 64);
        NPC daniel = new NPC("Daniel", "Daniel", Game.tiles.get(125).getImageView().getImage());
        daniel.move(256, 64);
        u163.addNPC(daniel);
        u163.addNPC(anders);
    }

    @Override
    public void navigated(NavigateEvent event) {
        Quest quest = player.getInactiveQuests().get("goToCampusQ");

        if (quest != null && quest.isCompleted()) {

            if (!player.getCurrentRoom().getID().equals("downunder")) {
                partyguy.move(256, 256);
                partyguy.spawn(getRoomHandler().getRooms(false));
            } else {
                partyguy.navigateTo(getRoomHandler().getRoom("downunder"));
            }
        }
        event.getNewRoom().draw();
    }

    @Override
    public void itemPickedUp(ItemPickupEvent event) {
        Item item = event.getItem();
        if (item != null) {
            item.removeFromLayer();
        }
    }

    /**
     * @return the roomHandler
     */
    public RoomHandler getRoomHandler() {
        return roomHandler;
    }

    /**
     * @return the questInventory
     */
    public QuestInventory getQuestInventory() {
        return questInventory;
    }

    /**
     * @return the finished
     */
    public boolean isFinished() {
        return finished;
    }
    
    private void initECTSHandler() {
        Room examRoom = getRoomHandler().getRoom("exam");
        this.ectsHandler = new ECTSHandler(player, examRoom);
        player.getECTSBar().setValue(0);
    }

    /**
     * @return the RPS
     */
    public RockPaperScissors getRPS() {
        return RPS;
    }
}
