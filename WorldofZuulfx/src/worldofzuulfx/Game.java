package worldofzuulfx;

import java.util.ArrayList;
import java.util.HashMap;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

import javafx.scene.shape.Rectangle;
import worldofzuulfx.Events.NavigateEvent;
import worldofzuulfx.Interfaces.NavigateListener;
import worldofzuulfx.Items.Drink;

import worldofzuulfx.Items.Item;
import worldofzuulfx.Items.ItemFactory;

import worldofzuulfx.NPC.*;
import worldofzuulfx.Quest.Quest;
import worldofzuulfx.Quest.QuestFactory;
import worldofzuulfx.Quest.QuestHandler;

import worldofzuulfx.Minigame.RockPaperScissors;
import worldofzuulfx.Quest.QuestInventory;

import worldofzuulfx.tiles.Tile;
import worldofzuulfx.tiles.TileLoader;
import worldofzuulfx.tiles.TileMap;

public class Game implements NavigateListener {

    private boolean finished;
//    private QuestHandler questHandler;
    private PartyGuy partyguy;
//    private HashMap<String, Quest> allGameQuests;
    private ArrayList<String> RPSCommands;
    private Player player;
    private TileMap tileMap;
    private RoomHandler roomHandler;
    private QuestInventory questInventory;

    public static Pane backgroundLayer;
    public static Pane spritesLayer;
    public static Pane objectsLayer;
    public static Pane inventorysLayer;
    
    private AnimationTimer timer;
    public static HashMap<Integer, Tile> tiles;

    private double nextPosX;
    private double nextPosY;

    public Game(Pane background, Pane sprites, Pane objects, Pane Inventory, Scene scene) // Constructor - ingen argumenter
    {
        this.backgroundLayer = background;
        this.spritesLayer = sprites;
        this.objectsLayer = objects;
        this.inventorysLayer = Inventory;
        addInputControls(scene);
        
        TileLoader tLoader = new TileLoader(new Image("http://i.imgur.com/OaHgZsd.png"), 32, 32);
        tiles = tLoader.getTiles();

        roomHandler = new RoomHandler();
        roomHandler.setRooms(RoomFactory.createRooms(tiles));
        
        questInventory = new QuestInventory();
        
        player = new Player("Player-name", sprites, new Image("http://i.imgur.com/zLwFeje.png"),
                background.getLayoutX() + 65.0, background.getLayoutY() + 65.0);
        player.setCanCollide(true);
        player.setDx(32);
        player.setDy(16);
        player.getBounds().setHeight(14);
        player.getBounds().setWidth(30);
        player.addNavigateListener(this);
        nextPosX = player.getBounds().getX();
        nextPosY = player.getBounds().getY();
        
        //TODO
        initNPCs();
        questInventory.initQuests(roomHandler, player);

        gameLoop();

        player.navigateTo(roomHandler.getRoom("outside"));
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
        player.updateUI();
    }

    public void checkCollisions() {
        String currentRoomID = this.player.getCurrentRoom().getID();

        Room currentRoom = this.getRoomHandler().getRoom(currentRoomID);

        for (Tile tile : currentRoom.getTileMap().getTileTerrain()) {

            if (tile.getCanCollide()) {
                if (tile.getBounds().getBoundsInLocal().intersects(nextPosX, nextPosY, player.getBounds().getWidth(), player.getBounds().getHeight())) {
                    // Reset the nextPos since a collision was detected
                    nextPosX = player.getX();
                    nextPosY = player.getY();
                    return;
                }
            }
        }
        for (Item item : currentRoom.getRoomInventory().getItemList()) {
            if (item.getCanCollide()) {
                if (item.getBounds().getBoundsInLocal().intersects(nextPosX, nextPosY, player.getBounds().getWidth(), player.getBounds().getHeight())) {
                    // Reset the nextPos since a collision was detected
                    nextPosX = player.getX();
                    nextPosY = player.getY();
                    return;
                }
            }
        }

        player.move(nextPosX, nextPosY);
    }

    public void cleanupSprites() {

    }

    private void addInputControls(Scene scene) {

        // keyboard handler: key pressed
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if (key.getCode() == KeyCode.RIGHT) {
                nextPosX = player.getBounds().getX() + player.getDx();
            }
            if (key.getCode() == KeyCode.LEFT) {
                nextPosX = player.getBounds().getX() - player.getDx();
            }
            if (key.getCode() == KeyCode.UP) {
                nextPosY = player.getBounds().getY() - player.getDy();
            }
            if (key.getCode() == KeyCode.DOWN) {
                nextPosY = player.getBounds().getY() + player.getDy();
            }

            if (key.getCode() == KeyCode.A) {
                player.navigateTo(getRoomHandler().getRoom("Campus"));
            }

        });
    }

    

    private void initPartyGuy() {
        partyguy = new PartyGuy("PartyGuy", "Den festlige ven");
        partyguy.spawn(getRoomHandler().getRooms(false), questInventory.getAllGameQuests());
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

        if (getPlayer().getCurrentRoom() == getRoomHandler().getRoom("Fredagsbar")) {
            ConsoleInfo.setConsoleData("I hereby challenge thee to an epic battle of 'ROCK PAPER AND SCISSOR' *epic drumroll*");
            ConsoleInfo.setConsoleData("Sound trumpets! let our bloody colours wave! And either victory, or else a grave. ");
            ConsoleInfo.setConsoleData("Just kidding. If you win I will give you coffee, if you lose then your energy will drain");
            ConsoleInfo.setConsoleData("");

            if (!command.hasSecondWord()) {

                ConsoleInfo.setConsoleData("Use one of the following commands:");
                ConsoleInfo.setConsoleData(">");
                rpsMiniGame.play();
                if (rpsMiniGame.getMoveComparison() == 1) {
                    getPlayer().pickupItem(ItemFactory.makeBeer());
                }
                if (rpsMiniGame.getMoveComparison() == -1) {
                    getPlayer().increaseEnergy(-5);
                }
                return;
            }
            ConsoleInfo.setConsoleData("Just type 'use' - no need for a second word");

        }
        if (getPlayer().getCurrentRoom() != getRoomHandler().getRoom("Fredagsbar")) {
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
            if (questInventory.getAllGameQuests().get("returnToU163").isCompleted()) {
                if (getPlayer().getCurrentRoom() == getPartyGuy().getCurrentRoom()) {
                    ConsoleInfo.clearData();
                    ConsoleInfo.setConsoleData("You met your good ol' buddy ol' pal the Partyguy who took you to 'FredagsBaren' but where did he go?");
                    getPartyGuy().partyTime(getPlayer(), getRoomHandler().getRoom("FredagsBar"), getRoomHandler().getRooms(false), questInventory.getAllGameQuests());
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
        Room u163 = getRoomHandler().getRoom("U163");
        NPC anders = new Lector("Anders", "Anders");
        NPC daniel = new Lector("Daniel", "Daniel");
        u163.addNPC(daniel);
        u163.addNPC(anders);
    }

    @Override
    public void navigated(NavigateEvent event) {
        event.getNewRoom().draw();
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
}
